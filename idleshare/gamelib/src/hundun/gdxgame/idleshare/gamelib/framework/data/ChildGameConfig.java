package hundun.gdxgame.idleshare.gamelib.framework.data;

import java.util.List;
import java.util.Map;

import lombok.*;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Getter
@Setter
public abstract class ChildGameConfig {
    ConstructionConfig constructionConfig;
    Map<String, List<String>> areaShowEntityByOwnAmountConstructionPrototypeIds;
    Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;
    Map<String, String> screenIdToFilePathMap;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ConstructionConfig {
        EmptyConstructionConfig emptyConstructionConfig;
        List<String> singletonPrototypeIds;
        List<String> worldPrototypeIds;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EmptyConstructionConfig {
        String prototypeId;
        List<String> buyCandidatePrototypeIds;
    }

}
