package hundun.gdxgame.idleshare.gamelib.framework.model.buff;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffSaveData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

public class OutputScaleBuffPrototype extends AbstractBuffPrototype {


    Map<String, OutputScaleOneConstructionConfig> allConstructionConfigMap;

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
            String levelPart,
            Map<String, OutputScaleOneConstructionConfig> allConstructionConfigMap
    ) {
        super(id, name, description, levelPart);
        this.allConstructionConfigMap = allConstructionConfigMap;
    }


    @Override
    public void lazyInitDescription(IdleGameplayContext gameplayContext) {
        super.lazyInitDescription(gameplayContext);


    }

    @Override
    public long modifyOutputCost(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        float targetScaleArg = Optional.ofNullable(allConstructionConfigMap.get(constructionPrototypeId))
                        .map(it -> it.getScaleCostArg())
                        .orElse(1.0f);
        double totalScale = Math.pow(targetScaleArg, saveData.buffLevel);
        return (long) (lastPhaseAmount * totalScale);
    }

    @Override
    public long modifyOutputGain(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        float targetScaleArg = Optional.ofNullable(allConstructionConfigMap.get(constructionPrototypeId))
                .map(it -> it.getScaleGainArg())
                .orElse(1.0f);
        double totalScale = Math.pow(targetScaleArg, saveData.buffLevel);
        return (long) (lastPhaseAmount * totalScale);
    }
}
