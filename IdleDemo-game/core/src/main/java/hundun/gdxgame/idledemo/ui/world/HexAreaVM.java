package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idledemo.ui.world.HexCellVM.MaskMode;
import hundun.gdxgame.idleshare.core.framework.CameraDataPackage;

import hundun.gdxgame.idleshare.core.framework.CameraGestureListener;
import hundun.gdxgame.idleshare.core.framework.CameraMouseListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HexAreaVM extends Table {
    public static final int roomWidth = 5000;
    public static final int roomHeight = 5000;

    public WorldPlayScreen screen;
    @Getter
    Map<String, HexCellVM> nodes = new LinkedHashMap<>();
    @Getter
    CameraDataPackage cameraDataPackage;
    @Null
    BaseConstruction selectedConstruction;
    public HexAreaVM(WorldPlayScreen screen) {
        this.screen = screen;
        this.cameraDataPackage = new CameraDataPackage();
        this.cameraDataPackage.setCameraZoomWeightOnlyAllowForceSet(true);
        if (screen.getGame().debugMode) {
            this.debugAll();
        }
    }

    public void updateUIForShow(
            List<BaseConstruction> constructions) {

        this.clear();
        this.addListener(new CameraGestureListener(cameraDataPackage));
        this.addListener(new CameraMouseListener(cameraDataPackage));
        this.getCameraDataPackage().forceSet(
                roomWidth / 2.0f,
                roomHeight / 2.0f,
                null
        );
        updateUIForConstructionCollectionChange(constructions);
    }


    public void updateUIForConstructionCollectionChange(List<BaseConstruction> constructions) {
        this.clearChildren();
        nodes.clear();

        Image background = new Image();
        background.setDrawable(DrawableFactory.getSimpleBoardBackground());
        background.setBounds(0, 0, roomWidth, roomHeight);
        this.addActor(background);

        constructions.stream()
                .sorted((i1, i2) -> {
                    Vector2 uiPos1 = HexCellVM.calculatePosition(i1.getSaveData().getPosition().getX(), i1.getSaveData().getPosition().getY());
                    Vector2 uiPos2 = HexCellVM.calculatePosition(i2.getSaveData().getPosition().getX(), i2.getSaveData().getPosition().getY());
                    int compareY = Float.compare(uiPos1.y, uiPos2.y);
                    if (compareY != 0) {
                        return -compareY;
                    } else {
                        return -Float.compare(uiPos1.x, uiPos2.x);
                    }
                })
                .forEach(deskData -> {
                    HexCellVM hexCellVM = new HexCellVM(screen, this, deskData);
                    nodes.put(deskData.getId(), hexCellVM);
                    this.addActor(hexCellVM);

                });
        nodes.values().forEach(hexCellVM -> {
            Actor hitBox = new Image();
            hitBox.setBounds(
                    hexCellVM.getX() + HexCellVM.HIT_BOX_X,
                    hexCellVM.getY() + HexCellVM.HIT_BOX_Y,
                    HexCellVM.HIT_BOX_WIDTH,
                    HexCellVM.HIT_BOX_HEIGHT
            );
            if (screen.getGame().debugMode) {
                hitBox.debug();
            }
            this.addActor(hitBox);
            hitBox.addListener(new HexCellClickListener(screen, hexCellVM));
        });
    }

    public void selectCell(@Null BaseConstruction selectedConstruction) {
        this.selectedConstruction = selectedConstruction;
        if (selectedConstruction != null) {
            nodes.values().forEach(it -> {
                if (it.getDeskData() == selectedConstruction) {
                    it.updateMaskMode(MaskMode.SELECTED);
                } else if (selectedConstruction.getNeighbors().containsValue(it.getDeskData())) {
                    it.updateMaskMode(MaskMode.SELECTED_DISTANCE_1);
                } else {
                    it.updateMaskMode(MaskMode.NONE);
                }
            });
        }
    }
}
