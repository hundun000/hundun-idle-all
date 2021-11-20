package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.AchievementPrototype;
import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.ui.other.GameAreaChangeButton;
import hundun.gdxgame.bugindustry.ui.other.GameAreaControlBoard;
import hundun.gdxgame.bugindustry.ui.other.GameImageDrawHelper;
import hundun.gdxgame.bugindustry.ui.other.PopupInfoBoard;
import hundun.gdxgame.bugindustry.ui.other.PopupInfoBoard;
import hundun.gdxgame.bugindustry.ui.other.StorageInfoBoard;
import hundun.gdxgame.bugindustry.ui.other.AchievementMaskBoard;
import hundun.gdxgame.bugindustry.ui.other.ConstructionControlBoard;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class GameScreen extends BaseScreen {
    private static final float LOGIC_FRAME_LENGTH = 1 / 30f; 
    public Pixmap tableBackgroundPixmap;
    public TextureRegionDrawable tableBackgroundDrawable;
    public Pixmap tableBackgroundPixmap2;
    public TextureRegionDrawable tableBackgroundDrawable2;
    public Pixmap maskBackgroundPixmap;
    public TextureRegionDrawable maskBackgroundDrawable;
    
    StorageInfoBoard storageInfoTable;
    ConstructionControlBoard constructionControlBoard;
    //Table backTable;
    Label clockLabel;
    PopupInfoBoard popUpInfoBoard;
//    GameAreaChangeButton areaChangeButtonL;
//    GameAreaChangeButton areaChangeButtonR;
    GameAreaControlBoard gameAreaControlBoard;
    AchievementMaskBoard achievementMaskBoard;
    GameImageDrawHelper gameImageDrawHelper;
    int clockCount = 0;
    private float logicFramAccumulator;
    boolean logicFramePause;

    @Getter
    GameArea area;
    
    public GameScreen(BugIndustryGame game) {
        super(game);
        
    }
    
    public void setAreaAndNotifyChildren(GameArea current) {
        GameArea last = this.area;
        this.area = current;
        
        
        constructionControlBoard.onGameAreaChange(last, current);
        gameAreaControlBoard.onGameAreaChange(last, current);
    }
    

    
    private void initChildren() {
        
        tableBackgroundPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        tableBackgroundPixmap.setColor(0.8f, 0.8f, 0.8f, 1.0f);
        tableBackgroundPixmap.fill();
        tableBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(tableBackgroundPixmap)));
        tableBackgroundPixmap2 = new Pixmap(1,1, Pixmap.Format.RGB565);
        tableBackgroundPixmap2.setColor(0.75f, 0.75f, 0.75f, 1.0f);
        tableBackgroundPixmap2.fill();
        tableBackgroundDrawable2 = new TextureRegionDrawable(new TextureRegion(new Texture(tableBackgroundPixmap2)));
        maskBackgroundPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        maskBackgroundPixmap.setColor(1f, 1f, 1f, 0.3f);
        maskBackgroundPixmap.fill();
        maskBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(maskBackgroundPixmap)));
        
        //backTable = new Table();
        //backTable.setBackground(tableBackgroundDrawable2);
        //backTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        areaChangeButtonL = GameAreaChangeButton.create(this, "GameAreaChangeButton_L.png", false);
//        stage.addActor(areaChangeButtonL);
//        areaChangeButtonR = GameAreaChangeButton.create(this, "GameAreaChangeButton_R.png", true);
//        stage.addActor(areaChangeButtonR);
        
        gameAreaControlBoard = new GameAreaControlBoard(this);
        stage.addActor(gameAreaControlBoard);
        
        clockLabel = new Label("", game.getButtonSkin());
        clockLabel.setBounds(0, Gdx.graphics.getHeight() - 10 - 50, 200, 50);
        stage.addActor(clockLabel);
        //backTable.debugAll();
        //stage.addActor(backTable);
        
        popUpInfoBoard = new PopupInfoBoard(this);
        stage.addActor(popUpInfoBoard);
        
        storageInfoTable = new StorageInfoBoard(this);
        stage.addActor(storageInfoTable);
        
        constructionControlBoard = new ConstructionControlBoard(this);
        stage.addActor(constructionControlBoard);
        
        achievementMaskBoard = new AchievementMaskBoard(this);
        stage.addActor(achievementMaskBoard);
        
        
        gameImageDrawHelper = new GameImageDrawHelper(this, stage.getCamera());
        gameImageDrawHelper.addBeeEntity();
//        gameImageDrawHelper.addBeeEntity();
//        gameImageDrawHelper.addBeeEntity();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        initChildren();
        
        setAreaAndNotifyChildren(GameArea.BEE_FARM);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logicFramAccumulator += delta;
        if (logicFramAccumulator >= LOGIC_FRAME_LENGTH) {
            logicFramAccumulator -= LOGIC_FRAME_LENGTH;
            if (!logicFramePause) {
                onLogicFrame();
            }
        }
        
        stage.act();
        
        if (game.drawGameImageAndPlayAudio) {
            gameImageDrawHelper.drawAll();
        }
        
        stage.draw();
    }

    private void onLogicFrame() {
        clockCount++;
        clockLabel.setText("LogicFrame: " + clockCount);
        
        constructionControlBoard.onLogicFrame();
    }

    public void showAndUpdateGuideInfo(String text) {
        popUpInfoBoard.setVisible(true);
        popUpInfoBoard.setText(text);
    }
    
    public void hideAndCleanGuideInfo() {
        popUpInfoBoard.setVisible(false);
        popUpInfoBoard.setText("GUIDE_TEXT");
    }

    @Override
    public void dispose() {
        tableBackgroundPixmap.dispose();
        
        tableBackgroundPixmap2.dispose();

    }

    public void hideAchievementMaskBoard() {
        achievementMaskBoard.setVisible(false);
        logicFramePause = false;
    }

    public void onAchievementUnlock(AchievementPrototype prototype) {
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        logicFramePause = true;
    }

}
