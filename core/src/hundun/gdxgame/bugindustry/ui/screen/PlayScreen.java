package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.bugindustry.ui.image.GameEntityFactory;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.GameImageDrawHelper;
import hundun.gdxgame.idlestarter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.idlestarter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idlestarter.ui.component.ConstructionControlBoard;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.PopupInfoBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class PlayScreen extends BasePlayScreen<BugIndustryGame> {
    
    private StorageInfoBoard<BugIndustryGame> storageInfoTable;
    private ConstructionControlBoard<BugIndustryGame> constructionControlBoard;
    private BackgroundImageBox<BugIndustryGame> backgroundImageBox;

    private GameAreaControlBoard<BugIndustryGame> gameAreaControlBoard;
    private GameImageDrawHelper<BugIndustryGame> gameImageDrawHelper;

    Table uiRootTable;
    Table popupRootTable;
    
    
    public PlayScreen(BugIndustryGame game) {
        super(game);
        popupUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));
        backUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));
    

    }
    
    private void initLogicChildren() {
        
        gameImageDrawHelper = new GameImageDrawHelper<>(this, uiStage.getCamera(), new GameEntityFactory(game));
        
        
        logicFrameListeners.add(constructionControlBoard);
        
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }

    private void initUiRoot() {
        uiRootTable = new Table();
        uiRootTable.setFillParent(true);
        uiStage.addActor(uiRootTable);
        
        
        storageInfoTable = new StorageInfoBoard<BugIndustryGame>(this);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        uiRootTable.add(storageInfoTable).height(storageInfoTable.getHeight()).row();
        
        gameAreaControlBoard = new GameAreaControlBoard<BugIndustryGame>(this, GameArea.values);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();

        constructionControlBoard = new ConstructionControlBoard<BugIndustryGame>(this);
        uiRootTable.add(constructionControlBoard).height(constructionControlBoard.BOARD_BORDER_HEIGHT);
        
        uiRootTable.debugCell();
    }
    
    private void initBackUiAndPopupUi() {
        
        this.backgroundImageBox = new BackgroundImageBox<BugIndustryGame>(this);
        backUiStage.addActor(backgroundImageBox);
        
        popupRootTable = new Table();
        popupRootTable.setFillParent(true);
        //popupRootTable.debug();
        popupUiStage.addActor(popupRootTable);
        
        popUpInfoBoard = new PopupInfoBoard<BugIndustryGame>(this);
        popupRootTable.add(popUpInfoBoard).bottom().expand().row();
        // empty image for hold the space
        popupRootTable.add(new Image()).height(game.LOGIC_HEIGHT / 4);
        
        achievementMaskBoard = new AchievementMaskBoard<BugIndustryGame>(this);
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
        
        // ====== be careful of draw order ======
        backUiStage.draw();
        if (game.drawGameImageAndPlayAudio) {
            gameImageDrawHelper.drawAll();
        }
        uiStage.draw();
        popupUiStage.draw();
    }


    @Override
    public void dispose() {


    }


}
