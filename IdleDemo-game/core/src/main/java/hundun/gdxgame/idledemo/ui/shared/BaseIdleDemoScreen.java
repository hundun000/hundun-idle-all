package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.StarterStorageInfoBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;

public abstract class BaseIdleDemoScreen extends BaseIdleScreen<DemoIdleGame, RootSaveData> {

    protected StarterStorageInfoBoard<DemoIdleGame, RootSaveData> storageInfoTable;

    protected BackgroundImageBox<DemoIdleGame, RootSaveData> backgroundImageBox;
    protected GameAreaControlBoard<DemoIdleGame, RootSaveData> gameAreaControlBoard;

    protected Table leftSideGroup;
    protected Table middleGroup;

    protected static int UI_ROOT_TABLE_COLSPAN_SIZE = 3;
    
    public BaseIdleDemoScreen(DemoIdleGame game, String screenId, PlayScreenLayoutConst layoutConst) {
        super(game, screenId, layoutConst);
    }

    @Override
    protected void lazyInitLogicContext() {
        logicFrameListeners.add(game.getIdleGameplayExport());

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(gameAreaControlBoard);

        gameAreaControlBoard.lazyInit(game.getControlBoardScreenIds());

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(storageInfoTable);
    }

    @Override
    protected void lazyInitUiRootContext() {

        storageInfoTable = new StarterStorageInfoBoard<>(this);
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

        gameAreaControlBoard = new GameAreaControlBoard<>(this);
        gameAreaControlBoard.top();
        uiRootTable.add(gameAreaControlBoard)
                .growY()
                .row();

    }

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {

        this.backgroundImageBox = new BackgroundImageBox<>(this);
        backUiStage.addActor(backgroundImageBox);

    }
}
