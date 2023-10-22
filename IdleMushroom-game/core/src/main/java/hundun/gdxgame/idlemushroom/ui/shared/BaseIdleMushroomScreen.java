package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;

public abstract class BaseIdleMushroomScreen extends BaseIdleScreen<IdleMushroomGame, RootSaveData> {

    protected StorageInfoBoard storageInfoTable;

    protected BackgroundImageBox<IdleMushroomGame, RootSaveData> backgroundImageBox;
    protected IdleMushroomGameAreaControlBoard gameAreaControlBoard;

    protected Table leftSideGroup;
    protected Table middleGroup;

    protected static int UI_ROOT_TABLE_COLSPAN_SIZE = 3;
    
    public BaseIdleMushroomScreen(IdleMushroomGame game, String screenId, PlayScreenLayoutConst layoutConst) {
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

        storageInfoTable = new StorageInfoBoard(this);
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

        gameAreaControlBoard = new IdleMushroomGameAreaControlBoard(this);
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
