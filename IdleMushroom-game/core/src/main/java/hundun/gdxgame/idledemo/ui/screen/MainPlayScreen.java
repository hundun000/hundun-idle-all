package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.main.MainScreenConstructionControlBoard;
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
public class MainPlayScreen extends BaseDemoPlayScreen
        implements ISecondaryInfoBoardCallback<BaseConstruction> {
    protected MainScreenPopupInfoBoard secondaryInfoBoard;
    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<IdleMushroomGame, RootSaveData> gameImageDrawer;
    protected MainClickerAnimationVM<IdleMushroomGame, RootSaveData> mainClickerAnimationVM;
    protected MainScreenConstructionControlBoard constructionControlBoard;
    public MainPlayScreen(IdleMushroomGame game) {
        super(game, DemoScreenId.SCREEN_MAIN);
    }

    public static class MainScreenPopupInfoBoard extends Table {

        MainPlayScreen parent;

        public MainScreenPopupInfoBoard(MainPlayScreen parent) {
            //super("GUIDE_TEXT", parent.game.getButtonSkin());
            this.parent = parent;
            //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
            this.setTouchable(Touchable.disabled);
            this.setBackground(parent.getLayoutConst().simpleBoardBackground);
            this.setVisible(false);
        }


        private void rebuildCells(BaseConstruction model) {
            this.clearChildren();

            add(new Label(model.getDetailDescroptionConstPart(), parent.getGame().getMainSkin()))
                    .colspan(3)
                    .left()
                    .row();

        }

        public void update(BaseConstruction model) {
            rebuildCells(model);
        }


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
        popupRootTable.add(secondaryInfoBoard).bottom().expand().row();
        popupRootTable.add(new Image())
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT);

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
