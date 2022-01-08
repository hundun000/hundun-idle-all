package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.ui.component.AchievementMaskBoard;
import hundun.gdxgame.bugindustry.ui.component.BackgroundImageBox;
import hundun.gdxgame.bugindustry.ui.component.ConstructionControlBoard;
import hundun.gdxgame.bugindustry.ui.component.GameAreaControlBoard;
import hundun.gdxgame.bugindustry.ui.component.GameImageDrawHelper;
import hundun.gdxgame.bugindustry.ui.component.PopupInfoBoard;
import hundun.gdxgame.bugindustry.ui.component.StorageInfoBoard;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class PlayScreen extends BasePlayScreen<BugIndustryGame> {
 
    public Pixmap tableBackgroundPixmap;
    public TextureRegionDrawable tableBackgroundDrawable;
    public Pixmap tableBackgroundPixmap2;
    public TextureRegionDrawable tableBackgroundDrawable2;
    public Pixmap maskBackgroundPixmap;
    public TextureRegionDrawable maskBackgroundDrawable;
    
    private StorageInfoBoard storageInfoTable;
    private ConstructionControlBoard constructionControlBoard;
    private BackgroundImageBox backgroundImageBox;

    private PopupInfoBoard popUpInfoBoard;
    private GameAreaControlBoard gameAreaControlBoard;
    private AchievementMaskBoard achievementMaskBoard;
    private GameImageDrawHelper gameImageDrawHelper;

    Table uiRootTable;
    Stage popupUiStage;
    private Stage backUiStage;
    
    public PlayScreen(BugIndustryGame game) {
        super(game);
        popupUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));
        backUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));
    
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
        
    }
    
    private void initLogicChildren() {
        
        gameImageDrawHelper = new GameImageDrawHelper(this, uiStage.getCamera());
        
        
        logicFrameListeners.add(constructionControlBoard);
        
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }

    private void initUiRoot() {
        uiRootTable = new Table();
        uiRootTable.setSize(game.LOGIC_WIDTH, game.LOGIC_HEIGHT - 10);
        uiStage.addActor(uiRootTable);
        
        
        storageInfoTable = new StorageInfoBoard(this);
        uiRootTable.add(storageInfoTable).height(storageInfoTable.getHeight()).row();
        
        gameAreaControlBoard = new GameAreaControlBoard(this, GameArea.values);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();

        constructionControlBoard = new ConstructionControlBoard(this);
        uiRootTable.add(constructionControlBoard).height(constructionControlBoard.getHeight());
        
        
    }
    
    private void initBackUiAndPopupUi() {
        
        this.backgroundImageBox = new BackgroundImageBox(this);
        backUiStage.addActor(backgroundImageBox);
        
        popUpInfoBoard = new PopupInfoBoard(this);
        popupUiStage.addActor(popUpInfoBoard);

        achievementMaskBoard = new AchievementMaskBoard(this);
        popupUiStage.addActor(achievementMaskBoard);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);
        
        initBackUiAndPopupUi();
        initUiRoot();
        initLogicChildren();
        
        // start area
        setAreaAndNotifyChildren(GameArea.BEE_FARM);
    }
    
    
    

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logicFrameCheck(delta);
        
        uiStage.act();
        
        backUiStage.draw();
        if (game.drawGameImageAndPlayAudio) {
            gameImageDrawHelper.drawAll();
        }
        uiStage.draw();
        popupUiStage.draw();
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
        setLogicFramePause(false);
    }

    public void onAchievementUnlock(AchievementPrototype prototype) {
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        setLogicFramePause(true);
    }

}
