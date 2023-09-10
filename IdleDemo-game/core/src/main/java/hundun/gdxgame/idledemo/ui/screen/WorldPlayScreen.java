package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.ui.entity.HexCellVM;
import hundun.gdxgame.idledemo.ui.entity.HexAreaVM;
import hundun.gdxgame.idledemo.ui.world.WorldCellDetailBoardVM;
import hundun.gdxgame.idleshare.core.framework.model.CameraDataPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class WorldPlayScreen extends BaseDemoPlayScreen {

    HexAreaVM hexAreaVM;
    protected OrthographicCamera deskCamera;
    protected Stage deskStage;

    protected WorldCellDetailBoardVM worldCellDetailBoardVM;

    public WorldPlayScreen(DemoIdleGame game) {
        super(game);

        this.deskCamera = new OrthographicCamera();
        this.deskStage = new Stage(new ScreenViewport(deskCamera), game.getBatch());
    }


    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        worldCellDetailBoardVM = new WorldCellDetailBoardVM(this);
        uiRootTable.add(worldCellDetailBoardVM).height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT).fill();
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();
        
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        gameAreaControlBoard.lazyInit(GameArea.values);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(worldCellDetailBoardVM);
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
                .getConstructions();
        hexAreaVM.updateDeskDatas(constructions);
        // start area
        setAreaAndNotifyChildren(GameArea.AREA_FOREST);
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
    public void onDeskClicked(HexCellVM vm) {
        worldCellDetailBoardVM.updateDetail(vm.getDeskData());
    }
}
