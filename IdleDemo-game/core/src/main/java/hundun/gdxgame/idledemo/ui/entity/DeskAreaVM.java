package hundun.gdxgame.idledemo.ui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idleshare.core.framework.model.CameraDataPackage;

import hundun.gdxgame.idleshare.core.framework.model.CameraGestureListener;
import hundun.gdxgame.idleshare.core.framework.model.CameraMouseListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DeskAreaVM extends Table {
    public BaseDemoPlayScreen screen;
    @Getter
    Map<String, ChessVM> nodes = new LinkedHashMap<>();
    @Getter
    CameraDataPackage cameraDataPackage;

    public DeskAreaVM(BaseDemoPlayScreen screen) {
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

        int roomWidth = 5000;
        int roomHeight = 5000;

        background.setBounds(0, 0, roomWidth, roomHeight);
        this.addActor(background);
        this.addListener(new CameraGestureListener(cameraDataPackage));
        this.addListener(new CameraMouseListener(cameraDataPackage));
        this.getCameraDataPackage().forceSet(
                0.0f,
                0.0f,
                null
        );

        chessRuntimeDataList.forEach(deskData -> {

            ChessVM actor = new ChessVM(this, deskData);
            nodes.put(deskData.getId(), actor);
            actor.addListener(new DeskClickListener(screen, actor));
            this.addActor(actor);

        });


    }



}
