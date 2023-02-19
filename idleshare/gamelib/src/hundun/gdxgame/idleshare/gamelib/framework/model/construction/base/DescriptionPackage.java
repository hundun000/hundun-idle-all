package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionPackage {
    String outputCostDescriptionStart;
    String outputGainDescriptionStart;
    String upgradeCostDescriptionStart;
    String upgradeMaxLevelDescription;
    String buttonDescroption;
    ILevelDescroptionProvider levelDescroptionProvider;

    public static interface ILevelDescroptionProvider {

        public static ILevelDescroptionProvider EMPTY_IMP = (level, workingLevel, reachMaxLevel) -> "";
        public static ILevelDescroptionProvider ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
            return "lv." + level;
        };
        public static ILevelDescroptionProvider WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
            return workingLevel + "/" + level + (reachMaxLevel ? "(max)" : "");
        };
        public static ILevelDescroptionProvider LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
            return (reachMaxLevel ? "Unlocked" : "");
        };

        String provide(int level, int workingLevel, boolean reachMaxLevel);
    }

}
