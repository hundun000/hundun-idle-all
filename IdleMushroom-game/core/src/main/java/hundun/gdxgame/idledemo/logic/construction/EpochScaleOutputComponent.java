package hundun.gdxgame.idledemo.logic.construction;

import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.IdleMushroomGame.ConstructionEpochConfig;
import hundun.gdxgame.idledemo.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoOutputComponent;

public class EpochScaleOutputComponent extends BaseAutoOutputComponent {

    public EpochScaleOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public long calculateModifiedOutputGain(long baseValue, int level, int proficiency) {
        RootEpochConfig config = construction.getGameplayContext().getConstructionManager()
                .getSingletonConstructionInstancesOrEmpty()
                .stream()
                .filter(it -> it.getPrototypeId().equals(DemoConstructionPrototypeId.EPOCH_COUNTER))
                .findAny()
                .map(epochCounter -> IdleMushroomGame.epochConfigMap.get(epochCounter.getSaveData().getLevel()))
                .orElse(null);
        if (config == null || config.getOutputScale() == null) {
            return (long)(baseValue * level);
        } else {
            return (long)(baseValue * level * config.getOutputScale());
        }
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level, int proficiency) {
        return (long)((baseValue * level));
    }
}
