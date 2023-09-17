package hundun.gdxgame.idleshare.core.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.component.*;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementUnlockCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BaseIdlePlayScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE>
        extends BaseHundunScreen<T_GAME, T_SAVE>
        implements IAchievementUnlockCallback {

    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final PlayScreenLayoutConst layoutConst;

    protected boolean hidden;
    // ====== need child lazy-init start ======
    protected AchievementMaskBoard<T_GAME, T_SAVE> achievementMaskBoard;

    protected StorageInfoBoard<T_GAME, T_SAVE> storageInfoTable;

    protected BackgroundImageBox<T_GAME, T_SAVE> backgroundImageBox;
    protected GameAreaControlBoard<T_GAME, T_SAVE> gameAreaControlBoard;
    // ----- not ui ------

    @Getter
    protected String area;

    protected List<ILogicFrameListener> logicFrameListeners;
    protected List<IGameAreaChangeListener> gameAreaChangeListeners;

    public BaseIdlePlayScreen(T_GAME game, PlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.layoutConst = layoutConst;
        this.logicFrameHelper = new LogicFrameHelper(LOGIC_FRAME_PER_SECOND);
        this.logicFrameListeners = new ArrayList<>();
        this.gameAreaChangeListeners = new ArrayList<>();
    }
    
    @Override
    protected void create() {

        lazyInitBackUiAndPopupUiContent();

        lazyInitUiRootContext();

        lazyInitLogicContext();

    }

    @Override
    public void dispose() {
    }

    @Override
    public void hideAchievementMaskBoard() {
        game.getFrontend().log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(provideDefaultInputProcessor());
        logicFrameHelper.setLogicFramePause(false);
    }

    @Override
    public void showAchievementMaskBoard(AbstractAchievement prototype) {
        if (this.hidden) {
            return;
        }
        game.getFrontend().log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        logicFrameHelper.setLogicFramePause(true);
    }



    protected void lazyInitLogicContext() {


        

        logicFrameListeners.add(game.getIdleGameplayExport());

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(gameAreaControlBoard);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(storageInfoTable);
    }
    


    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<>(this);
        uiRootTable.add(storageInfoTable).height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT).fill().row();


        if (game.debugMode) {
            uiRootTable.debugCell();
        }
    }

    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<>(this);
        backUiStage.addActor(backgroundImageBox);

        
        
        
        achievementMaskBoard = new AchievementMaskBoard<>(this);
        popupUiStage.addActor(achievementMaskBoard);
        
        if (game.debugMode) {
            popupRootTable.debugCell();
        }
        
    }



    @Override
    protected void onLogicFrame() {
        super.onLogicFrame();

        for (ILogicFrameListener logicFrameListener : logicFrameListeners) {
            logicFrameListener.onLogicFrame();
        }
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(provideDefaultInputProcessor());

        updateUIForShow();

        this.hidden = false;
        Gdx.app.log(this.getClass().getSimpleName(), "show done");
    }

    @Override
    public void hide() {
        super.hide();

        this.hidden = true;
    }

    protected abstract void updateUIForShow();

    protected abstract InputProcessor provideDefaultInputProcessor();

    public void setAreaAndNotifyChildren(String current) {
        String last = this.area;
        this.area = current;

        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(last, current);
        }

    }
}
