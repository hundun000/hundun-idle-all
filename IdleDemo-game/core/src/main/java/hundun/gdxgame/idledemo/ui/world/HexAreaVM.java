package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idleshare.core.framework.model.CameraDataPackage;

import hundun.gdxgame.idleshare.core.framework.model.CameraGestureListener;
import hundun.gdxgame.idleshare.core.framework.model.CameraMouseListener;
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

    public HexAreaVM(WorldPlayScreen screen) {
        this.screen = screen;
        this.cameraDataPackage = new CameraDataPackage();

        if (screen.getGame().debugMode) {
            this.debugAll();
        }
    }

    public void updateDeskDatas(
            List<BaseConstruction> chessRuntimeDataList) {
        this.clear();
        nodes.clear();

        Image background = new Image();

        background.setDrawable(DrawableFactory.getSimpleBoardBackground());
        background.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.onCellClicked(null);
            }
        });



        background.setBounds(0, 0, roomWidth, roomHeight);
        this.addActor(background);
        this.addListener(new CameraGestureListener(cameraDataPackage));
        this.addListener(new CameraMouseListener(cameraDataPackage));
        this.getCameraDataPackage().forceSet(
                roomWidth / 2.0f,
                roomHeight / 2.0f,
                null
        );

        chessRuntimeDataList.forEach(deskData -> {

            HexCellVM actor = new HexCellVM(screen, this, deskData);
            nodes.put(deskData.getId(), actor);
            actor.addListener(new HexCellClickListener(screen, actor));
            this.addActor(actor);

        });



    }



}
