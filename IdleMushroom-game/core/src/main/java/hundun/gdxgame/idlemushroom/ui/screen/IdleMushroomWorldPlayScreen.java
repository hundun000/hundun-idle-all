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
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomScreenId;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idlemushroom.ui.world.CameraControlBoard;
import hundun.gdxgame.idlemushroom.ui.world.HexCellVM;
import hundun.gdxgame.idlemushroom.ui.world.HexAreaVM;
import hundun.gdxgame.idlemushroom.ui.world.WorldDetailBoardVM;
import hundun.gdxgame.idleshare.core.framework.CameraDataPackage;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class IdleMushroomWorldPlayScreen extends BaseIdleMushroomPlayScreen implements IConstructionCollectionListener {

    @Getter
    HexAreaVM hexAreaVM;
    protected OrthographicCamera deskCamera;
    protected Stage deskStage;
    protected WorldDetailBoardVM worldDetailBoardVM;
    @Getter
    private boolean disableHexAreaInput;

    public IdleMushroomWorldPlayScreen(IdleMushroomGame game) {
        super(game, IdleMushroomScreenId.SCREEN_WORLD);

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

        CameraControlBoard cameraControlBoard = new CameraControlBoard(this);
        middleGroup.add(cameraControlBoard);
        middleGroup.right().bottom();
    }

    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();
        
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        buffInfoBoard.lazyInit(IdleMushroomBuffId.VALUES_FOR_SHOW_ORDER);

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(worldDetailBoardVM);
    }

    @Override
    public void onLogicFrame() {
        super.onLogicFrame();

        worldDetailBoardVM.onLogicFrame();
        hexAreaVM.getNodes().values().forEach(it -> it.onLogicFrame());
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
        super.updateUIForShow();
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

}
