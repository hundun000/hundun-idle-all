package hundun.gdxgame.idleshare.core.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.StorageInfoBoard;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BaseIdleScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE>
        extends BaseHundunScreen<T_GAME, T_SAVE> {

    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final PlayScreenLayoutConst layoutConst;

    protected boolean hidden;
    // ====== need child lazy-init start ======

    protected StorageInfoBoard<T_GAME, T_SAVE> storageInfoTable;

    protected BackgroundImageBox<T_GAME, T_SAVE> backgroundImageBox;
    protected GameAreaControlBoard<T_GAME, T_SAVE> gameAreaControlBoard;

    protected Table leftSideGroup;
    // ----- not ui ------


    protected List<ILogicFrameListener> logicFrameListeners;
    protected List<IGameAreaChangeListener> gameAreaChangeListeners;

    @Getter
    protected final String screenId;

    public BaseIdleScreen(T_GAME game, String screenId, PlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.screenId = screenId;
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

        if (game.debugMode) {
            uiRootTable.debugCell();
            popupRootTable.debugCell();
            popupRootTable.debugCell();
        }
    }

    @Override
    public void dispose() {
    }




    protected void lazyInitLogicContext() {
        logicFrameListeners.add(game.getIdleGameplayExport());

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(gameAreaControlBoard);

        gameAreaControlBoard.lazyInit(game.getControlBoardScreenIds());

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(storageInfoTable);
    }
    


    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<>(this);
        uiRootTable.add(storageInfoTable)
                .height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT)
                .fill()
                .colspan(2)
                .row();

        leftSideGroup = new Table();
        uiRootTable.add(leftSideGroup).expand().left();

        gameAreaControlBoard = new GameAreaControlBoard<>(this);
        uiRootTable.add(gameAreaControlBoard).expand().right().top().row();

    }

    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<>(this);
        backUiStage.addActor(backgroundImageBox);

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

        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(game.getLastScreenId(), this.getScreenId());
        }

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


}
