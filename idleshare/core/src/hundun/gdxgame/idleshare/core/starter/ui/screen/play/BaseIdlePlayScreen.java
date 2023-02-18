package hundun.gdxgame.idleshare.core.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import hundun.gdxgame.corelib.starter.StarterPlayScreen;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.idleshare.core.starter.ui.component.PopupInfoBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.fixed.FixedConstructionControlBoard;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementUnlockCallback;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;


/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BaseIdlePlayScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE>
        extends StarterPlayScreen<T_GAME, T_SAVE>
        implements IAchievementUnlockCallback, ISecondaryInfoBoardCallback<BaseConstruction> {

    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final PlayScreenLayoutConst layoutConst;


    // ====== need child lazy-init start ======
    protected AchievementMaskBoard<T_GAME, T_SAVE> achievementMaskBoard;
    protected PopupInfoBoard<T_GAME, T_SAVE> secondaryInfoBoard;
    protected GameImageDrawer<T_GAME, T_SAVE> gameImageDrawer;
    protected StorageInfoBoard<T_GAME, T_SAVE> storageInfoTable;
    protected AbstractConstructionControlBoard<T_GAME, T_SAVE> constructionControlBoard;
    protected BackgroundImageBox<T_GAME, T_SAVE> backgroundImageBox;
    protected GameAreaControlBoard<T_GAME, T_SAVE> gameAreaControlBoard;

    public BaseIdlePlayScreen(T_GAME game, String startArea, PlayScreenLayoutConst layoutConst) {
        super(game, startArea, game.getSharedViewport(), LOGIC_FRAME_PER_SECOND);
        this.layoutConst = layoutConst;
        game.getGamePlayContext().getEventManager().registerListener(this);
    }
    
    @Override
    protected void create() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hideAchievementMaskBoard() {
        Gdx.app.log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(uiStage);
        logicFrameHelper.setLogicFramePause(false);
    }

    @Override
    public void onAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        logicFrameHelper.setLogicFramePause(true);
    }

    @Override
    public void showAndUpdateGuideInfo(BaseConstruction model) {
        secondaryInfoBoard.setVisible(true);
        secondaryInfoBoard.update(model);
    }

    @Override
    public void hideAndCleanGuideInfo() {
        secondaryInfoBoard.setVisible(false);
        //popUpInfoBoard.setText("GUIDE_TEXT");
    }

    protected void lazyInitLogicContext() {
        gameImageDrawer = new GameImageDrawer<>(this);
        
        logicFrameListeners.add(constructionControlBoard);
        logicFrameListeners.add(game.getGamePlayContext().getStorageManager());

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }
    


    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<>(this);
        uiRootTable.add(storageInfoTable).height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT).fill().row();
        
        gameAreaControlBoard = new GameAreaControlBoard<>(this);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();
        // impl switchable
        constructionControlBoard = new FixedConstructionControlBoard<>(this);
        uiRootTable.add(constructionControlBoard).height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT).fill();
        
        if (game.debugMode) {
            uiRootTable.debugCell();
        }
    }

    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<>(this);
        backUiStage.addActor(backgroundImageBox);
        
        this.secondaryInfoBoard = new PopupInfoBoard<>(this);
        popupRootTable.add(secondaryInfoBoard).bottom().expand().row();
        popupRootTable.add(new Image())
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT);
        
        
        
        achievementMaskBoard = new AchievementMaskBoard<>(this);
        popupUiStage.addActor(achievementMaskBoard);
        
        if (game.debugMode) {
            popupRootTable.debugCell();
        }
        
    }

    protected void gameObjectDraw(float delta) {
        gameImageDrawer.allEntitiesMoveForFrameAndDraw();
    }
    
    
    
}
