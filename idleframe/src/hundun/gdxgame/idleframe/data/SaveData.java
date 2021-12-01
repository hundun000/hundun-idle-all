package hundun.gdxgame.idleframe.data;

import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/09
 */
@Data
public class SaveData {
    Map<String, Long> ownResoueces;
    Set<String> unlockedResourceTypes;
    Map<String, Integer> buffAmounts;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;
}
