package hundun.gdxgame.idleframe.data;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleframe.model.construction.BaseConstruction;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Data
public class ChildGameConfig {
    Map<String, List<String>> areaShownConstructionIds;
    List<BaseConstruction> constructions;
    Map<String, Integer> constructionStarterLevelMap;
}
