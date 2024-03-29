package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.main.MainScreenConstructionControlBoard;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.main.GameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.framework.GameImageDrawer;
import hundun.gdxgame.idleshare.core.framework.GameImageDrawer.IGameImageDrawerHolder;
import hundun.gdxgame.idledemo.ui.shared.PopupInfoBoard;
import hundun.gdxgame.idledemo.ui.main.MainClickerAnimationVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class MainPlayScreen extends BaseDemoPlayScreen
        implements ISecondaryInfoBoardCallback<BaseConstruction>, IGameImageDrawerHolder {
    protected PopupInfoBoard secondaryInfoBoard;
    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<IdleDemoGame, RootSaveData> gameImageDrawer;
    protected MainClickerAnimationVM mainClickerAnimationVM;
    protected MainScreenConstructionControlBoard constructionControlBoard;
    public MainPlayScreen(IdleDemoGame game) {
        super(game, DemoScreenId.SCREEN_COOKIE);
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        mainClickerAnimationVM = new MainClickerAnimationVM(
                this,
                game.getTextureManager().getMainClickAnimationTextureAtlas()
        );
        middleGroup.add(mainClickerAnimationVM);

        constructionControlBoard = new MainScreenConstructionControlBoard(this, this);
        uiRootTable.add(constructionControlBoard)
                .height(layoutConst.CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
        ;
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        this.gameImageDrawer = new GameImageDrawer<>(this, this);
        this.gameEntityManager = new GameEntityManager(this);
        gameEntityManager.lazyInit(
                game.getChildGameConfig().getAreaEntityEffectConfigMap()
        );
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst.gameEntityFactoryLayoutConst, this);
        gameImageDrawer.lazyInit(gameEntityFactory, gameEntityManager);

        gameAreaChangeListeners.add(constructionControlBoard);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(gameImageDrawer);
    }

    @Override
    public void onLogicFrame() {
        super.onLogicFrame();

        constructionControlBoard.onLogicFrame();
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

        this.secondaryInfoBoard = new PopupInfoBoard(this);
        popupRootTable.add(secondaryInfoBoard).bottom().expand().row();
        popupRootTable.add(new Image())
                .height(layoutConst.CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT);

    }

    @Override
    protected void updateUIForShow() {

    }

    @Override
    protected void renderPopupAnimations(float delta, SpriteBatch spriteBatch) {
        super.renderPopupAnimations(delta, spriteBatch);

        mainClickerAnimationVM.updateFrame(delta, spriteBatch);
    }


    @Override
    public long getResourceNumOrZero(String resourceId) {
        return game.getIdleGameplayExport().getGameplayContext().getStorageManager().getResourceNumOrZero(resourceId);
    }

    @Override
    public int getConstructionWorkingLevelNumOrZero(String prototypeId) {
        List<BaseConstruction> constructions = game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getConstructionsOfPrototype(prototypeId);
        return constructions.stream().mapToInt(it -> it.getSaveData().getWorkingLevel()).sum();
    }

    @Override
    public Sprite getResourceEntity(String resourceId) {
        return new Sprite(game.getTextureManager().getResourceEntity(resourceId));
    }

    @Override
    public Sprite getConstructionEntity(String constructionId) {
        return new Sprite(game.getTextureManager().getConstructionEntity(constructionId));
    }
}
