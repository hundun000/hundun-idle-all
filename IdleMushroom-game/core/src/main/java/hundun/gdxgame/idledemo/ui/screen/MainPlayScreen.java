package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.main.MainScreenConstructionControlBoard;
import hundun.gdxgame.idledemo.ui.main.MainScreenPopupInfoBoard;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.main.GameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.idleshare.core.starter.ui.component.animation.MainClickerAnimationVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class MainPlayScreen extends BaseIdleMushroomPlayScreen
        implements ISecondaryInfoBoardCallback<BaseConstruction> {
    protected MainScreenPopupInfoBoard secondaryInfoBoard;
    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<IdleMushroomGame, RootSaveData> gameImageDrawer;
    protected MainClickerAnimationVM<IdleMushroomGame, RootSaveData> mainClickerAnimationVM;
    protected MainScreenConstructionControlBoard constructionControlBoard;
    public MainPlayScreen(IdleMushroomGame game) {
        super(game, DemoScreenId.SCREEN_MAIN);
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        mainClickerAnimationVM = new MainClickerAnimationVM<>(
                this,
                game.getTextureManager().getMainClickAnimationTextureAtlas()
        );
        middleGroup.add(mainClickerAnimationVM);

        constructionControlBoard = new MainScreenConstructionControlBoard(this, this);
        uiRootTable.add(constructionControlBoard)
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
        ;
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        this.gameImageDrawer = new GameImageDrawer<>(this);
        this.gameEntityManager = new GameEntityManager(game);
        gameEntityManager.lazyInit(
                game.getChildGameConfig().getAreaEntityEffectConfigMap()
        );
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer.lazyInit(gameEntityFactory, gameEntityManager);

        logicFrameListeners.add(constructionControlBoard);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);

        gameAreaChangeListeners.add(constructionControlBoard);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(constructionControlBoard);
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
    public void onCellClicked(HexCellVM vm) {
        throw new UnsupportedOperationException();
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

        this.secondaryInfoBoard = new MainScreenPopupInfoBoard(this);
        popupRootTable.add(secondaryInfoBoard).center().expand();

    }

    @Override
    protected void updateUIForShow() {

    }

    @Override
    protected void renderPopupAnimations(float delta, SpriteBatch spriteBatch) {
        super.renderPopupAnimations(delta, spriteBatch);

        mainClickerAnimationVM.updateFrame(delta, spriteBatch);
    }
}
