package hundun.gdxgame.idleshare.gamelib.export;

public interface IIdleFrontend {
    int getLogicFramePerSecond();

    boolean modLogicFrameSecondZero(int mod);
    float getSecond();
}
