package hundun.gdxgame.idleshare.framework.data;

import java.util.Map;

/**
 * @author hundun
 * Created on 2022/04/08
 */
public class StarterData {
    Map<String, Integer> constructionStarterLevelMap;
    Map<String, Boolean> constructionStarterWorkingLevelMap;

    // ------ replace-lombok ------
    public Map<String, Integer> getConstructionStarterLevelMap() {
        return constructionStarterLevelMap;
    }
    public void setConstructionStarterLevelMap(Map<String, Integer> constructionStarterLevelMap) {
        this.constructionStarterLevelMap = constructionStarterLevelMap;
    }
    public Map<String, Boolean> getConstructionStarterWorkingLevelMap() {
        return constructionStarterWorkingLevelMap;
    }
    public void setConstructionStarterWorkingLevelMap(Map<String, Boolean> constructionStarterWorkingLevelMap) {
        this.constructionStarterWorkingLevelMap = constructionStarterWorkingLevelMap;
    }


}
