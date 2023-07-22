package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.entity.ChessVM;
import hundun.gdxgame.idledemo.ui.entity.GameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer.GameImageDrawerOwner;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.fixed.FixedConstructionControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class CookiePlayScreen extends BaseDemoPlayScreen implements GameImageDrawerOwner {

    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<DemoIdleGame, RootSaveData> gameImageDrawer;
    protected AbstractConstructionControlBoard<DemoIdleGame, RootSaveData> constructionControlBoard;

    public CookiePlayScreen(DemoIdleGame game) {
        super(game, GameArea.AREA_COOKIE);
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        // impl switchable
        constructionControlBoard = new FixedConstructionControlBoard<>(this);
        uiRootTable.add(constructionControlBoard).height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT).fill();
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
        gameAreaControlBoard.lazyInit(GameArea.values);
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
    public void onDeskClicked(ChessVM vm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameEntityManager getGameEntityManager() {
        return gameEntityManager;
    }
}
