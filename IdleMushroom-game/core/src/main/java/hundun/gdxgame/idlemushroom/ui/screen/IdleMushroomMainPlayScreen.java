package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomScreenId;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idlemushroom.ui.main.MainScreenConstructionControlBoard;
import hundun.gdxgame.idlemushroom.ui.shared.wiki.SharedWikiPopupInfoBoard;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idlemushroom.ui.world.HexCellVM;
import hundun.gdxgame.idlemushroom.ui.main.GameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.AbstractAnimationVM;
import hundun.gdxgame.idleshare.core.framework.GameImageDrawer;
import hundun.gdxgame.idleshare.core.framework.GameImageDrawer.IGameImageDrawerHolder;
import hundun.gdxgame.idleshare.core.framework.manager.GameEntityManager;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class IdleMushroomMainPlayScreen extends BaseIdleMushroomPlayScreen
        implements IGameImageDrawerHolder {
    protected GameEntityManager gameEntityManager;
    protected GameImageDrawer<IdleMushroomGame, RootSaveData> gameImageDrawer;
    protected MainClickerAnimationVM mainClickerAnimationVM;
    protected MainScreenConstructionControlBoard constructionControlBoard;
    public IdleMushroomMainPlayScreen(IdleMushroomGame game) {
        super(game, IdleMushroomScreenId.SCREEN_MAIN);
    }

    public static class MainClickerAnimationVM
            extends AbstractAnimationVM<IdleMushroomGame, RootSaveData, Void>
    {


        BaseConstruction construction;
        TextureRegion[][] regions;
        BaseIdleMushroomScreen parent;

        // Constant rows and columns of the sprite sheet
        private static final int FRAME_COLS = 6, FRAME_ROWS = 5;




        public MainClickerAnimationVM(
                BaseIdleMushroomScreen parent,
                TextureRegion[][] regions
        ) {
            super(parent.getGame(), null);
            this.parent = parent;
            this.regions = regions;

            this.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (construction != null && construction.getOutputComponent().isTypeClickOutput()) {
                        construction.getOutputComponent().doOutput();
                    } else {
                        parent.getGame().getFrontend().log(MainClickerAnimationVM.this.getClass().getSimpleName(),
                                "skip construction doOutput"
                        );
                    }
                    if (!isRunningState()) {
                        callShow(null);
                    }
                }
            });

            this.setTouchable(Touchable.enabled);
            //this.setTransform(true);
            this.construction = parent.getGame().getIdleGameplayExport()
                    .getGameplayContext()
                    .getConstructionManager()
                    .getMainClickConstructionInstances()
            ;

            setAnimation(animationFactory(
                    regions,
                    0.1f, PlayMode.NORMAL
            ));
            super.resetFrame(false);
        }

        @Override
        public void callShow(Void unused) {


            super.resetFrame(true);
        }

    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        mainClickerAnimationVM = new MainClickerAnimationVM(
                this,
                game.getTextureManager().getMainClickAnimationTextureAtlas()
        );

        constructionControlBoard = new MainScreenConstructionControlBoard(this, this);
        uiRootTable.add(constructionControlBoard)
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
        ;
    }

    public void setMainClickerWithScale() {
        if (middleGroup != null) {
            int enlargementLevel = game.getIdleMushroomExtraGameplayExport().getCurrentRootEpochConfig().getEnlargementLevel();
            middleGroup.clearChildren();
            middleGroup.add(mainClickerAnimationVM).size(64 * enlargementLevel, 64 * enlargementLevel);
        }
    }

    @Override
    public void onLogicFrame() {
        super.onLogicFrame();

        constructionControlBoard.onLogicFrame();
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        this.gameImageDrawer = new GameImageDrawer<>(this, this);
        this.gameEntityManager = new GameEntityManager(this);
        gameEntityManager.lazyInit(
                game.getChildGameConfig().getAreaEntityEffectConfigMap()
        );
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this, this.layoutConst.gameEntityFactoryLayoutConst);
        gameImageDrawer.lazyInit(gameEntityFactory, gameEntityManager);

        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        buffInfoBoard.lazyInit(IdleMushroomBuffId.VALUES_FOR_SHOW_ORDER);

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
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();



    }

    @Override
    protected void updateUIForShow() {
        super.updateUIForShow();
        setMainClickerWithScale();
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
