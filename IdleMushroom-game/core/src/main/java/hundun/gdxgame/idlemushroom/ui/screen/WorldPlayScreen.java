package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.logic.ResourceType;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idlemushroom.ui.world.HexCellVM;
import hundun.gdxgame.idlemushroom.ui.world.HexAreaVM;
import hundun.gdxgame.idlemushroom.ui.world.WorldDetailBoardVM;
import hundun.gdxgame.idlemushroom.ui.world.WorldScreenPopupInfoBoard;
import hundun.gdxgame.idleshare.core.framework.model.CameraDataPackage;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class WorldPlayScreen extends BaseIdleMushroomPlayScreen implements IConstructionCollectionListener, ISecondaryInfoBoardCallback<BaseConstruction> {

    HexAreaVM hexAreaVM;
    protected OrthographicCamera deskCamera;
    protected Stage deskStage;
    protected WorldScreenPopupInfoBoard secondaryInfoBoard;
    protected WorldDetailBoardVM worldDetailBoardVM;
    @Getter
    private boolean disableHexAreaInput;

    public WorldPlayScreen(IdleMushroomGame game) {
        super(game, DemoScreenId.SCREEN_WORLD);

        this.deskCamera = new OrthographicCamera();
        this.deskStage = new Stage(new ScreenViewport(deskCamera), game.getBatch());
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        worldDetailBoardVM = new WorldDetailBoardVM(this);
        worldDetailBoardVM.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                disableHexAreaInput = true;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                disableHexAreaInput = false;
            }
        });
        uiRootTable.add(worldDetailBoardVM)
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
        ;
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();
        
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);

        this.secondaryInfoBoard = new WorldScreenPopupInfoBoard(this);
        popupRootTable.add(secondaryInfoBoard).center().expand();

        logicFrameListeners.add(worldDetailBoardVM);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(worldDetailBoardVM);
    }

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();

        hexAreaVM = new HexAreaVM(this);
        deskStage.addActor(hexAreaVM);
        deskStage.setScrollFocus(hexAreaVM);

    }

    @Override
    protected void updateUIForShow() {
        List<BaseConstruction> constructions = game.getIdleGameplayExport().getGameplayContext().getConstructionManager()
                .getWorldConstructionInstances();
        hexAreaVM.updateUIForShow(constructions);
        onCellClicked(null);
    }

    @Override
    protected void belowUiStageDraw(float delta) {
        deskStage.act();
        deskStage.getViewport().getCamera().position.set(
                hexAreaVM.getCameraDataPackage().getCurrentCameraX(),
                hexAreaVM.getCameraDataPackage().getCurrentCameraY(),
                0);
        if (hexAreaVM.getCameraDataPackage().getAndClearCameraZoomDirty()) {
            float weight = hexAreaVM.getCameraDataPackage().getCurrentCameraZoomWeight();
            deskCamera.zoom = CameraDataPackage.cameraZoomWeightToZoomValue(weight);
            game.getFrontend().log(this.getClass().getSimpleName(), "deskCamera.zoom = %s", deskCamera.zoom);
        }
        deskStage.getViewport().apply();
        deskStage.draw();
    }


    @Override
    protected InputProcessor provideDefaultInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(popupUiStage);
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(deskStage);
        return multiplexer;
    }

    @Override
    public void onCellClicked(HexCellVM vm) {
        worldDetailBoardVM.selectCell(vm != null ? vm.getDeskData() : null);
        hexAreaVM.selectCell(vm != null ? vm.getDeskData() : null);
    }

    @Override
    public void onConstructionCollectionChange() {
        List<BaseConstruction> constructions = game.getIdleGameplayExport().getGameplayContext().getConstructionManager()
                .getWorldConstructionInstances();
        hexAreaVM.updateUIForConstructionCollectionChange(constructions);
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
}
