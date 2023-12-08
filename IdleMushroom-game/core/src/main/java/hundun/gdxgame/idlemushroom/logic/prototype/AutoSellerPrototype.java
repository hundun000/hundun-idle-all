package hundun.gdxgame.idlemushroom.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idlemushroom.logic.construction.DemoSimpleAutoOutputComponent;
import hundun.gdxgame.idlemushroom.logic.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idlemushroom.logic.ResourceType;
import hundun.gdxgame.idlemushroom.util.IdleMushroomJavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;



public class AutoSellerPrototype extends AbstractConstructionPrototype {

    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .name("Research Center")
            .wikiText("Research Center" + "：\n" +
                    "•Timely consumption of mushroom resources, production of genetic points.\n" +
                    "•The active level can be adjusted。")
            .upgradeButtonText("Upgrade")
            .outputCostDescriptionStart("Cost")
            .outputGainDescriptionStart("Produce")
            .upgradeCostDescriptionStart("Upgrade cost")
            .upgradeMaxLevelDescription("(max)")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP.build())
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .name("科研中心")
            .wikiText("科研中心" + "：\n" +
                    "•定时消耗蘑菇资源，生产基因点数。\n" +
                    "•可以调整启用的等级。")
            .upgradeButtonText("升级")
            .outputCostDescriptionStart("消耗")
            .outputGainDescriptionStart("产出")
            .upgradeCostDescriptionStart("升级费用")
            .upgradeMaxLevelDescription("(已达到最大等级)")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.CN_PROFICIENCY_IMP.build())
            .build();

    public AutoSellerPrototype(Language language) {
        super(
                IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER,
                language
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = AutoSellerPrototype.descriptionPackageCN;
                break;
            default:
                this.descriptionPackage = AutoSellerPrototype.descriptionPackageEN;
                break;
        }

    }

    @Override
    public BaseConstruction getInstance(GridPosition position) {
        String id = prototypeId + "_" + IdleMushroomJavaFeatureForGwt.uuid();
        BaseIdleDemoConstruction construction = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(construction);
        construction.setProficiencyComponent(proficiencyComponent);

        DemoSimpleAutoOutputComponent outputComponent = new DemoSimpleAutoOutputComponent(construction);
        construction.setOutputComponent(outputComponent);

        construction.getOutputComponent().setOutputCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.MUSHROOM, 1
        )));
        construction.getOutputComponent().setOutputGainPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.DNA_POINT, 2
        )));

        construction.getUpgradeComponent().setUpgradeCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.MUSHROOM, 100
        )));
        construction.getLevelComponent().setTypeWorkingLevelChangeable(true);
        construction.getLevelComponent().maxLevel = 100;

        return construction;
    }
}
