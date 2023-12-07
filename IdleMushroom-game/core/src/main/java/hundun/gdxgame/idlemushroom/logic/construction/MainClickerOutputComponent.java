package hundun.gdxgame.idlemushroom.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.OutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoOutputComponent;

public class MainClickerOutputComponent extends OutputComponent {

    public MainClickerOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public void onSubLogicFrame() {

    }

    @Override
    public long calculateModifiedOutputGain(long baseValue, int level, int proficiency) {
        return (long)((baseValue * Math.pow(10, level - 1)));
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level, int proficiency) {
        return (long)((baseValue * Math.pow(10, level - 1)));
    }
}
