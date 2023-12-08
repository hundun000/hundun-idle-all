package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;

public class Const {
    public static DescriptionPackage.DescriptionPackageBuilder templateDescriptionPackageEN = DescriptionPackage.builder()
            .name("ComplexAutoClicker")
            .upgradeButtonText("Upgrade")
            .outputCostDescriptionStart("Consume")
            .outputGainDescriptionStart("Produce")
            .upgradeCostDescriptionStart("Upgrade cost")
            .upgradeMaxLevelDescription("(max)")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP.build())
            ;


    public static DescriptionPackage.DescriptionPackageBuilder templateDescriptionPackageCN = DescriptionPackage.builder()
            .upgradeButtonText("升级")
            .outputCostDescriptionStart("自动消耗")
            .outputGainDescriptionStart("自动产出")
            .upgradeCostDescriptionStart("升级费用")
            .upgradeMaxLevelDescription("(已达到最大等级)")
            .levelDescriptionProvider(DescriptionPackageFactory.CN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.CN_PROFICIENCY_IMP.build())
            ;
}
