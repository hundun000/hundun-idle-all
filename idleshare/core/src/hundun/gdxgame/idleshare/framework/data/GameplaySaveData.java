package hundun.gdxgame.idleshare.framework.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;
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
    Map<String, Long> ownResoueces;
    Set<String> unlockedResourceTypes;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;

}
