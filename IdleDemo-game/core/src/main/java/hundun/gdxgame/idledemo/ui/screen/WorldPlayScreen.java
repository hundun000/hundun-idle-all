package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.world.HexAreaVM;
import hundun.gdxgame.idledemo.ui.world.WorldCellDetailBoardVM;
import hundun.gdxgame.idleshare.core.framework.CameraDataPackage;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class WorldPlayScreen extends BaseDemoPlayScreen implements IConstructionCollectionListener {

    HexAreaVM hexAreaVM;
    protected OrthographicCamera deskCamera;
    protected Stage deskStage;

    protected WorldCellDetailBoardVM worldCellDetailBoardVM;

    public WorldPlayScreen(IdleDemoGame game) {
        super(game, DemoScreenId.SCREEN_WORLD);

        this.deskCamera = new OrthographicCamera();
        this.deskStage = new Stage(new ScreenViewport(deskCamera), game.getBatch());
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        worldCellDetailBoardVM = new WorldCellDetailBoardVM(this);
        uiRootTable.add(worldCellDetailBoardVM)
                .height(layoutConst.CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
        ;
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(worldCellDetailBoardVM);
    }

    @Override
    public void onLogicFrame() {
        super.onLogicFrame();
        worldCellDetailBoardVM.onLogicFrame();
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
        worldCellDetailBoardVM.selectCell(null);
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
        worldCellDetailBoardVM.selectCell(vm != null ? vm.getDeskData() : null);
        hexAreaVM.selectCell(vm != null ? vm.getDeskData() : null);
    }

    @Override
    public void onConstructionCollectionChange() {
        List<BaseConstruction> constructions = game.getIdleGameplayExport().getGameplayContext().getConstructionManager()
                .getWorldConstructionInstances();
        hexAreaVM.updateUIForConstructionCollectionChange(constructions);
    }
}
