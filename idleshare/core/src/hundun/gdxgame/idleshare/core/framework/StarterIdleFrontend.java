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
}
