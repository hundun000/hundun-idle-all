package hundun.gdxgame.idleshare.gamelib.framework.data;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleshare.gamelib.framework.model.AbstractAchievement;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Getter
@Setter
public abstract class ChildGameConfig {
    Map<String, List<String>> areaControlableConstructionVMPrototypeIds;
    Map<String, List<String>> areaControlableConstructionPrototypeVMPrototypeIds;
    Map<String, List<String>> areaShowEntityByOwnAmountConstructionPrototypeIds;
    Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;
    Map<String, String> screenIdToFilePathMap;
    List<String> achievementPrototypeIds;
}
