package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.AchievementPrototype;
import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.ui.component.AchievementMaskBoard;
import hundun.gdxgame.bugindustry.ui.component.BackgroundImageBox;
import hundun.gdxgame.bugindustry.ui.component.ConstructionControlBoard;
import hundun.gdxgame.bugindustry.ui.component.GameAreaControlBoard;
import hundun.gdxgame.bugindustry.ui.component.GameImageDrawHelper;
import hundun.gdxgame.bugindustry.ui.component.PopupInfoBoard;
import hundun.gdxgame.bugindustry.ui.component.StorageInfoBoard;
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
    //Image backgroundImage;
    BackgroundImageBox backgroundImageBox;
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
        
        //this.backgroundImage.setDrawable(new SpriteDrawable(new Sprite(game.getTextureManager().getBackgroundTexture(current))));
        backgroundImageBox.onGameAreaChange(last, current);
        constructionControlBoard.onGameAreaChange(last, current);
        gameAreaControlBoard.onGameAreaChange(last, current);
    }
    

    
    private void initChildren() {
//        this.backgroundImage = new Image(game.getTextureManager().getBackgroundTexture(GameArea.BEE_BUFF));
//        stage.addActor(backgroundImage);
        
        this.backgroundImageBox = new BackgroundImageBox(this);
        stage.addActor(backgroundImageBox);
        
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
//        gameImageDrawHelper.addBeeEntity();
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
        stage.draw();
        
        if (game.drawGameImageAndPlayAudio) {
            gameImageDrawHelper.drawAll();
        }
        
        
    }

    private void onLogicFrame() {
        clockCount++;
        clockLabel.setText("LogicFrame: " + clockCount);
        
        constructionControlBoard.onLogicFrame();
    }

    public void showAndUpdateGuideInfo(BaseConstruction model) {
        popUpInfoBoard.setVisible(true);
        popUpInfoBoard.update(model);
    }
    
    public void hideAndCleanGuideInfo() {
        popUpInfoBoard.setVisible(false);
        //popUpInfoBoard.setText("GUIDE_TEXT");
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
