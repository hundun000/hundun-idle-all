package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2023/03/01
 */
public class DescriptionPackageFactory {
    
    private static ILevelDescroptionProvider EMPTY_IMP = (level, workingLevel, reachMaxLevel) -> "";
    private static ILevelDescroptionProvider ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "lv." + level;
    };
    private static ILevelDescroptionProvider WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "lv." + workingLevel + "/" + level + (reachMaxLevel ? "(max)" : "");
    };
    private static ILevelDescroptionProvider LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
        return (reachMaxLevel ? "Unlocked" : "");
    };
    
    private static ILevelDescroptionProvider CN_EMPTY_IMP = (level, workingLevel, reachMaxLevel) -> "";
    private static ILevelDescroptionProvider CN_ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "等级" + level;
    };
    private static ILevelDescroptionProvider CN_WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
        return "等级" +workingLevel + "/" + level + (reachMaxLevel ? "(最大)" : "");
    };
    private static ILevelDescroptionProvider CN_LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
        return (reachMaxLevel ? "已解锁" : "");
    };
    
    public static DescriptionPackage getWorkingLevelAutoDescriptionPackage(Language language) {
        switch (language) {
            case CN:
                return new DescriptionPackage(
                        "自动消耗", "自动产出", "升级费用", "(已达到最大等级)", "升级",
                        CN_WORKING_LEVEL_IMP);
            default:
                return new DescriptionPackage(
                        "AutoCost", "AutoGain", "UpgradeCost", "(max level)", "Upgrade",
                        WORKING_LEVEL_IMP);
        }
    }
    
    public static DescriptionPackage getMaxLevelAutoDescriptionPackage(Language language) {
        switch (language) {
            case CN:
                return new DescriptionPackage(
                        "自动消耗", "自动产出", "升级费用", "(已达到最大等级)", "升级",
                        CN_ONLY_LEVEL_IMP);
            default:
                return new DescriptionPackage(
                        "AutoCost", "AutoGain", "UpgradeCost", "(max level)", "Upgrade",
                        ONLY_LEVEL_IMP);
        }
    }
    
    public static DescriptionPackage getSellingDescriptionPackage(Language language) {
        switch (language) {
            case CN:
                return new DescriptionPackage(
                        "自动出售", "自动获得", "升级费用", "(已达到最大等级)", "升级",
                        CN_WORKING_LEVEL_IMP);
            default:
                return new DescriptionPackage(
                        "Sell", "Gain", "UpgradeCost", "(max level)", "Upgrade",
                        WORKING_LEVEL_IMP);
        }
    }
    

    public static DescriptionPackage getGatherDescriptionPackage(Language language) {
        switch (language) {
            case CN:
                return new DescriptionPackage(
                        "消耗", "获得", null, null, "采集",
                        CN_EMPTY_IMP);
            default:
                return new DescriptionPackage(
                        "Pay", "Gain", null, null, "Gather",
                        EMPTY_IMP);
        }
    }
    
    
    public static DescriptionPackage getWinDescriptionPackage(Language language) {
        switch (language) {
            case CN:
                return new DescriptionPackage(
                        null, null, "支付", null, "解锁",
                        CN_LOCK_IMP);
            default:
                return new DescriptionPackage(
                        null, null, "Pay", null, "Unlock",
                        LOCK_IMP);
        }
    }
}
