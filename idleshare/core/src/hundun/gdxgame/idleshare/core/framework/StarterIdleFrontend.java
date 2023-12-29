package hundun.gdxgame.idleshare.core.framework;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.idleshare.gamelib.export.IIdleFrontend;

public class StarterIdleFrontend implements IIdleFrontend {
    BaseHundunGame<?> game;
    public StarterIdleFrontend(BaseHundunGame<?> game) {
        this.game = game;
    }

    @Override
    public int getLogicFramePerSecond() {
        return game.getLogicFrameHelper().secondToFrameNum(1.0f);
    }

    @Override
    public boolean modLogicFrameSecondZero(int mod) {
        return game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(mod) == 0;
    }

    @Override
    public float getSecond() {
        return game.getLogicFrameHelper().getClockCount() * 1.0f / game.getLogicFrameHelper().secondToFrameNum(1);
    }
}
