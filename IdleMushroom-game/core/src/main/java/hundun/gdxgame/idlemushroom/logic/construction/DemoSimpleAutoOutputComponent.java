package hundun.gdxgame.idlemushroom.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoOutputComponent;

public class DemoSimpleAutoOutputComponent extends BaseAutoOutputComponent {

    public DemoSimpleAutoOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public long calculateModifiedOutputGain(long baseValue, int level, int proficiency) {
        return (long)((baseValue * level));
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level, int proficiency) {
        return (long)((baseValue * level));
    }
}
