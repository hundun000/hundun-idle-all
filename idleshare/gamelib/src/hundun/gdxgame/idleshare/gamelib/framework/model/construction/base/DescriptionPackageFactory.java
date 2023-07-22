package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.ILevelDescriptionProvider;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.IProficiencyDescroptionProvider;

/**
 * @author hundun
 * Created on 2023/03/01
 */
public class DescriptionPackageFactory {

    public static ILevelDescriptionProvider NO_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> "";
    public static ILevelDescriptionProvider ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "lv." + level;
    };
    public static ILevelDescriptionProvider WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "lv." + workingLevel + "/" + level + (reachMaxLevel ? "(max)" : "");
    };
    public static ILevelDescriptionProvider LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
        return (reachMaxLevel ? "Unlocked" : "");
    };
    public static IProficiencyDescroptionProvider EN_PROFICIENCY_IMP = (proficiency, reachMaxProficiency) -> {
        return "efficiency: " + proficiency;
    };

    public static ILevelDescriptionProvider CN_NO_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> "";
    public static ILevelDescriptionProvider CN_ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "等级" + level;
    };
    public static ILevelDescriptionProvider CN_WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "等级" +workingLevel + "/" + level + (reachMaxLevel ? "(最大)" : "");
    };
    public static ILevelDescriptionProvider CN_LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
        return (reachMaxLevel ? "已解锁" : "");
    };

    public static IProficiencyDescroptionProvider CN_PROFICIENCY_IMP = (proficiency, reachMaxProficiency) -> {
        return "效率" + proficiency;
    };

}
