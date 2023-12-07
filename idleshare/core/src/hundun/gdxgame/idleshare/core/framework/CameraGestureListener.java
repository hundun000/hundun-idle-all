package hundun.gdxgame.idleshare.core.framework;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class CameraGestureListener extends ActorGestureListener {

    private final CameraDataPackage cameraDataPackage;
    float laseZoomValue;

    public CameraGestureListener(CameraDataPackage cameraDataPackage) {
        this.cameraDataPackage = cameraDataPackage;
    }

    @Override
    public void zoom(InputEvent event, float initialDistance, float distance) {
        super.zoom(event, initialDistance, distance);

        float newWeight = (initialDistance / distance) * cameraDataPackage.getCurrentCameraZoomWeight();
        cameraDataPackage.modifyCurrentCameraZoomWeight(newWeight - cameraDataPackage.getCurrentCameraZoomWeight());
    }

    @Override
    public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        super.pan(event, x, y, deltaX, deltaY);

        float cameraDeltaX = -deltaX;
        float cameraDeltaY = -deltaY;
        cameraDataPackage.modifyCurrentCamera(cameraDeltaX, cameraDeltaY);
    }
}
