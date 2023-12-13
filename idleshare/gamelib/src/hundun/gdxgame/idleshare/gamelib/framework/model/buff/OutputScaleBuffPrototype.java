package hundun.gdxgame.idleshare.gamelib.framework.model.buff;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffSaveData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OutputScaleBuffPrototype extends AbstractBuffPrototype {


    OutputScaleOneConstructionConfig defaultConstructionConfig;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OutputScaleOneConstructionConfig {
        Float scaleCostArg;
        Float scaleGainArg;
    }


    public OutputScaleBuffPrototype(
            String id,
            String name,
            String description,
            OutputScaleOneConstructionConfig defaultConstructionConfig
    ) {
        super(id, name, description);
        this.defaultConstructionConfig = defaultConstructionConfig;
    }


    @Override
    public void lazyInitDescription(IdleGameplayContext gameplayContext) {
        super.lazyInitDescription(gameplayContext);


    }

    @Override
    public long modifyOutputCost(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        OutputScaleOneConstructionConfig targetConstructionConfig = defaultConstructionConfig;
        float targetScaleArg = targetConstructionConfig.getScaleCostArg() != null ? targetConstructionConfig.getScaleCostArg() : 1.0f;
        double totalScale = Math.pow(targetScaleArg, saveData.buffLevel);
        return (long) (lastPhaseAmount * totalScale);
    }

    @Override
    public long modifyOutputGain(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        OutputScaleOneConstructionConfig targetConstructionConfig = defaultConstructionConfig;
        float targetScaleArg = targetConstructionConfig.getScaleGainArg() != null ? targetConstructionConfig.getScaleGainArg() : 1.0f;
        double totalScale = Math.pow(targetScaleArg, saveData.buffLevel);
        return (long) (lastPhaseAmount * totalScale);
    }
}
