package hundun.gdxgame.idleshare.gamelib.framework.data;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import lombok.*;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Getter
@Setter
public abstract class ChildGameConfig {
    ConstructionConfig constructionConfig;
    Map<String, AreaEntityEffectConfig> areaEntityEffectConfigMap;
    Map<String, String> screenIdToFilePathMap;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AreaEntityEffectConfig {
        List<String> ownAmountConstructionPrototypeIds;
        List<String> ownAmountResourceIds;
        List<String> changeAmountResourceIds;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ConstructionConfig {
        String mainClickerConstructionPrototypeId;
        List<String> singletonPrototypeIds;
        List<String> worldPrototypeIds;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ConstructionBuyCandidateConfig {
        String prototypeId;
        ResourcePack buyCostPack;
    }

}
