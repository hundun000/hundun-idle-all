package hundun.gdxgame.idleshare.core.framework;

import lombok.Getter;
import lombok.Setter;

public class CameraDataPackage {
    @Getter
    private float currentCameraX;
    @Getter
    private float currentCameraY;
    @Setter
    private Float boundLeft;
    @Setter
    private Float boundRight;
    @Setter
    private Float boundUp;
    @Setter
    private Float boundDown;
    @Setter
    private Float boundZoomMax;
    @Setter
    private float boundZoomMin;
    @Getter
    private float currentCameraZoomWeight;
    @Getter
    private boolean currentCameraZoomDirty;
    @Setter
    private boolean cameraZoomWeightOnlyAllowForceSet;

    public CameraDataPackage() {
        this.boundZoomMin = 0.1f;
    }

    public static float cameraZoomWeightToZoomValue(float weight){
        //return weight <= 0 ? (float)Math.pow(2, weight) : (float)Math.log(weight + 2);
        return weight;
    }


    public void modifyCurrentCamera(Float deltaX, Float deltaY) {
        float totalDeltaX = 0;
        float totalDeltaY = 0;
        if (deltaX != null) {
            totalDeltaX += deltaX;
        }
        if (deltaY != null) {
            totalDeltaY += deltaY;
        }
        if (boundLeft != null && currentCameraX + totalDeltaX < boundLeft) {
            totalDeltaX = 0;
        }
        if (boundRight != null && currentCameraX + totalDeltaX > boundRight) {
            totalDeltaX = 0;
        }
        if (boundDown != null && currentCameraY + totalDeltaY < boundDown) {
            totalDeltaY = 0;
        }
        if (boundUp != null && currentCameraY + totalDeltaY > boundUp) {
            totalDeltaY = 0;
        }
        currentCameraX += totalDeltaX;
        currentCameraY += totalDeltaY;
    }

    public void modifyCurrentCameraZoomWeight(Float delta) {
        if (cameraZoomWeightOnlyAllowForceSet) {
            return;
        }
        currentCameraZoomWeight += delta;
        currentCameraZoomWeight = Math.max(boundZoomMin, currentCameraZoomWeight);
        if (boundZoomMax != null) {
            currentCameraZoomWeight = Math.min(boundZoomMax, currentCameraZoomWeight);
        }
        currentCameraZoomDirty = true;
    }

    public boolean getAndClearCameraZoomDirty () {
        boolean result = currentCameraZoomDirty;
        currentCameraZoomDirty = false;
        return result;
    }

    public void forceSet(Float currentCameraX, Float currentCameraY, Float currentCameraZoomWeight) {
        if (currentCameraX != null) {
            this.currentCameraX = currentCameraX;
        }
        if (currentCameraY != null) {
            this.currentCameraY = currentCameraY;
        }
        if (currentCameraZoomWeight != null) {
            this.currentCameraZoomWeight = currentCameraZoomWeight;
            this.currentCameraZoomDirty = true;
        }
    }
}
