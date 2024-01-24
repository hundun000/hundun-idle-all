package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomWorldPlayScreen;

public class CameraControlBoard extends Table {

    protected IdleMushroomWorldPlayScreen screen;

    public CameraControlBoard(IdleMushroomWorldPlayScreen parent) {
        this.screen = parent;
        int buttonSize = parent.getLayoutConst().CAMERA_CONTROL_BOARD_BUTTON_SIZE;
        float step = 0.25f;

        TextButton zoomInButton = new TextButton("+", parent.getGame().getMainSkin());
        zoomInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getHexAreaVM().getCameraDataPackage().modifyCurrentCameraZoomWeight(-step);
            }
        });
        this.add(zoomInButton).size(buttonSize, buttonSize).row();

        TextButton resetButton = new TextButton("R", parent.getGame().getMainSkin());
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getHexAreaVM().cameraReset();
            }
        });
        this.add(resetButton).size(buttonSize, buttonSize).row();

        TextButton zoomOutButton = new TextButton("-", parent.getGame().getMainSkin());
        zoomOutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getHexAreaVM().getCameraDataPackage().modifyCurrentCameraZoomWeight(step);
            }
        });
        this.add(zoomOutButton).size(buttonSize, buttonSize).row();
    }
}
