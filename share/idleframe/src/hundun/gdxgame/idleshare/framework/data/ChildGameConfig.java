package hundun.gdxgame.idleshare.framework.data;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/12/02
 */
public abstract class ChildGameConfig {
    Map<String, List<String>> areaControlableConstructionIds;
    Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds;
    Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;
    List<BaseConstruction> constructions;
    StarterData starterData;
    Map<String, String> screenIdToFilePathMap;
    List<AchievementPrototype> achievementPrototypes;

    // ------ replace-lombok ------
    public Map<String, List<String>> getAreaControlableConstructionIds() {
        return areaControlableConstructionIds;
    }
    public void setAreaControlableConstructionIds(Map<String, List<String>> areaControlableConstructionIds) {
        this.areaControlableConstructionIds = areaControlableConstructionIds;
    }
    public Map<String, List<String>> getAreaShowEntityByOwnAmountConstructionIds() {
        return areaShowEntityByOwnAmountConstructionIds;
    }
    public void setAreaShowEntityByOwnAmountConstructionIds(
            Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds) {
        this.areaShowEntityByOwnAmountConstructionIds = areaShowEntityByOwnAmountConstructionIds;
    }
    public Map<String, List<String>> getAreaShowEntityByOwnAmountResourceIds() {
        return areaShowEntityByOwnAmountResourceIds;
    }
    public void setAreaShowEntityByOwnAmountResourceIds(Map<String, List<String>> areaShowEntityByOwnAmountResourceIds) {
        this.areaShowEntityByOwnAmountResourceIds = areaShowEntityByOwnAmountResourceIds;
    }
    public Map<String, List<String>> getAreaShowEntityByChangeAmountResourceIds() {
        return areaShowEntityByChangeAmountResourceIds;
    }
    public void setAreaShowEntityByChangeAmountResourceIds(
            Map<String, List<String>> areaShowEntityByChangeAmountResourceIds) {
        this.areaShowEntityByChangeAmountResourceIds = areaShowEntityByChangeAmountResourceIds;
    }
    public List<BaseConstruction> getConstructions() {
        return constructions;
    }
    public void setConstructions(List<BaseConstruction> constructions) {
        this.constructions = constructions;
    }
    public StarterData getStarterData() {
        return starterData;
    }
    public void setStarterData(StarterData starterData) {
        this.starterData = starterData;
    }
    public Map<String, String> getScreenIdToFilePathMap() {
        return screenIdToFilePathMap;
    }
    public void setScreenIdToFilePathMap(Map<String, String> screenIdToFilePathMap) {
        this.screenIdToFilePathMap = screenIdToFilePathMap;
    }
    public List<AchievementPrototype> getAchievementPrototypes() {
        return achievementPrototypes;
    }
    public void setAchievementPrototypes(List<AchievementPrototype> achievementPrototypes) {
        this.achievementPrototypes = achievementPrototypes;
    }

}
