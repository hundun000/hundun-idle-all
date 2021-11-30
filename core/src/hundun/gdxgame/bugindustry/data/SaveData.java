package hundun.gdxgame.bugindustry.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.BuffId;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/09
 */
@Data
public class SaveData {
    Map<ResourceType, Long> ownResoueces;
    Set<ResourceType> unlockedResourceTypes;
    Map<BuffId, Integer> buffAmounts;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;
}
