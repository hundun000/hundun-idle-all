package hundun.gdxgame.idleshare.gamelib.framework.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffSaveData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author hundun
 * Created on 2022/04/08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameplaySaveData {
    String stageId;
    Map<String, Long> ownResources;
    Set<String> unlockedResourceTypes;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
    Map<String, AchievementSaveData> achievementSaveDataMap;
    Map<String, BuffSaveData> buffSaveDataMap;
}
