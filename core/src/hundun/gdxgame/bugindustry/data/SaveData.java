package hundun.gdxgame.bugindustry.data;

import java.util.Map;

import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/09
 */
@Data
public class SaveData {
    Map<ResourceType, Integer> ownResoueces;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
}
