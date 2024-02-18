package hundun.gdxgame.idleshare.gamelib.export;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public interface IIdleFrontend {
    int getLogicFramePerSecond();

    boolean modLogicFrameSecondZero(int mod);
    float getSecond();

    default void postConstructionCreate(BaseConstruction construction) {};
}
