package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseIdleDemoScreen extends BaseHundunScreen<IdleDemoGame, RootSaveData> {

    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final PlayScreenLayoutConst layoutConst;

    protected boolean hidden;
    // ====== need child lazy-init start ======


    // ----- not ui ------
    protected List<IGameAreaChangeListener> gameAreaChangeListeners;

    @Getter
    protected final String screenId;
    protected StarterStorageInfoBoard storageInfoTable;

    protected BackgroundImageBox backgroundImageBox;
    protected GameAreaControlBoard gameAreaControlBoard;

    protected Table leftSideGroup;
    protected Table middleGroup;

    protected static int UI_ROOT_TABLE_COLSPAN_SIZE = 3;
    
    public BaseIdleDemoScreen(IdleDemoGame game, String screenId, PlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.screenId = screenId;
        this.layoutConst = layoutConst;
        this.gameAreaChangeListeners = new ArrayList<>();
    }

    protected void lazyInitLogicContext() {

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(gameAreaControlBoard);

        gameAreaControlBoard.lazyInit(game.getControlBoardScreenIds());
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(storageInfoTable);
    }


    protected void lazyInitUiRootContext() {

        storageInfoTable = new StarterStorageInfoBoard(this);
        uiRootTable.add(storageInfoTable)
                .height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
                .row();

        leftSideGroup = new Table();
        uiRootTable.add(leftSideGroup)
                .growY();

        middleGroup = new Table();
        uiRootTable.add(middleGroup).grow();

        gameAreaControlBoard = new GameAreaControlBoard(this);
        gameAreaControlBoard.top();
        uiRootTable.add(gameAreaControlBoard)
                .growY()
                .row();

    }

    protected void lazyInitBackUiAndPopupUiContent() {

        this.backgroundImageBox = new BackgroundImageBox(this);
        backUiStage.addActor(backgroundImageBox);

    }

    @Override
    protected void create() {

        lazyInitBackUiAndPopupUiContent();

        lazyInitUiRootContext();

        lazyInitLogicContext();

        if (game.debugMode) {
            uiRootTable.debugAll();
            popupRootTable.debugCell();
        }
    }

    @Override
    public void dispose() {
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
