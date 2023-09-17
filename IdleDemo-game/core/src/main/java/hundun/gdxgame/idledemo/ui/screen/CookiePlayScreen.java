package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.entity.GameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer.GameImageDrawerOwner;
import hundun.gdxgame.idleshare.core.starter.ui.component.PopupInfoBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.fixed.FixedConstructionControlBoard;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class CookiePlayScreen extends BaseDemoPlayScreen
        implements GameImageDrawerOwner, ISecondaryInfoBoardCallback<BaseConstruction> {
    protected PopupInfoBoard<DemoIdleGame, RootSaveData> secondaryInfoBoard;
    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<DemoIdleGame, RootSaveData> gameImageDrawer;
    protected AbstractConstructionControlBoard<DemoIdleGame, RootSaveData> constructionControlBoard;

    public CookiePlayScreen(DemoIdleGame game) {
        super(game, DemoScreenId.SCREEN_COOKIE);
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        // impl switchable
        constructionControlBoard = new FixedConstructionControlBoard<>(this, this);
        uiRootTable.add(constructionControlBoard)
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(2)
        ;
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        this.gameImageDrawer = new GameImageDrawer<>(this, this);
        this.gameEntityManager = new GameEntityManager(game);
        gameEntityManager.lazyInit(
                game.getChildGameConfig().getAreaShowEntityByOwnAmountConstructionPrototypeIds(),
                game.getChildGameConfig().getAreaShowEntityByOwnAmountResourceIds(),
                game.getChildGameConfig().getAreaShowEntityByChangeAmountResourceIds()
        );
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer.lazyInit(gameEntityFactory);

        logicFrameListeners.add(constructionControlBoard);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);

        gameAreaChangeListeners.add(constructionControlBoard);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(gameImageDrawer);
    }

    @Override
    protected InputProcessor provideDefaultInputProcessor() {
        return uiStage;
    }

    @Override
    protected void belowUiStageDraw(float delta) {
        gameImageDrawer.allEntitiesMoveForFrameAndDraw();
    }

    @Override
    public void onDeskClicked(HexCellVM vm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameEntityManager getGameEntityManager() {
        return gameEntityManager;
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

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();

        this.secondaryInfoBoard = new PopupInfoBoard<>(this);
        popupRootTable.add(secondaryInfoBoard).bottom().expand().row();
        popupRootTable.add(new Image())
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT);

    }

    @Override
    protected void updateUIForShow() {

    }
}
