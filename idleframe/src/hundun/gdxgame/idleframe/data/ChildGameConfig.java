package hundun.gdxgame.idleframe.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.entity.IGameEntityFactory;
import lombok.Data;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Data
public abstract class ChildGameConfig {
    Map<String, List<String>> areaControlableConstructionIds;
    Map<String, List<String>> areaShowEntityConstructionIds;
    Map<String, List<String>> areaShowEntityResourceIds;
    //Map<String, List<String>> areaShownResourceIds;
    List<BaseConstruction> constructions;
    Map<String, Integer> constructionStarterLevelMap;
    Map<String, Boolean> constructionStarterWorkingLevelMap;
    Map<String, String> screenIdToFilePathMap;
    String skinFilePath;
}
