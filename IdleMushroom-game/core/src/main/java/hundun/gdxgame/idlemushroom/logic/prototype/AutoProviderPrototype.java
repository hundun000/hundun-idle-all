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
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.ProficiencyDescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;


public class AutoProviderPrototype extends AbstractConstructionPrototype {

    public static ProficiencyDescriptionPackage CN_PROFICIENCY_IMP = DescriptionPackageFactory.EN_PROFICIENCY_IMP
            .proficiencyPart("成长度: {0}")
            .build();

    public static ProficiencyDescriptionPackage EN_PROFICIENCY_IMP = DescriptionPackageFactory.EN_PROFICIENCY_IMP
            .proficiencyPart("Growth: {0}")
            .build();

    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .name("Mushroom Tile")
            .wikiText("Mushroom Tile" + "：\n" +
                    "•Timely production of mushroom resources.\n" +
                    "•When the growth reaches its full length, it can be upgraded, and the growth length will be reset after the upgrade.\n" +
                    "•The growth speed is related to the surrounding patches, trees benefit the growth, and the existence of other mushroom patches hinders the growth.")
            .upgradeButtonText("Upgrade")
            .outputCostDescriptionStart("Consume")
            .outputGainDescriptionStart("Produce")
            .upgradeCostDescriptionStart("Upgrade cost")
            .upgradeMaxLevelDescription("(max)")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(AutoProviderPrototype.EN_PROFICIENCY_IMP)
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .name("蘑菇地块")
            .wikiText("Mushroom Tile" + "：\n" +
                    "•定时生产蘑菇资源。\n" +
                    "•成长度满时可以升级，升级后成长度重置。\n" +
                    "•成长速度与周围地块有关，树木有利于成长，存在其他蘑菇地块不利于成长。")
            .upgradeButtonText("升级")
            .outputCostDescriptionStart("消耗")
            .outputGainDescriptionStart("产出")
            .upgradeCostDescriptionStart("升级费用")
            .upgradeMaxLevelDescription("(已达到最大等级)")
            .levelDescriptionProvider(DescriptionPackageFactory.CN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(AutoProviderPrototype.CN_PROFICIENCY_IMP)
            .build();




    public AutoProviderPrototype(String prototypeId, Language language) {
        super(
                prototypeId,
                language
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = descriptionPackageCN;
                break;
            default:
                this.descriptionPackage = descriptionPackageEN;
                break;
        }

    }

    @Override
    public BaseConstruction getInstance(GridPosition position) {
        String id = prototypeId + "_" + IdleMushroomJavaFeatureForGwt.uuid();
        BaseIdleDemoConstruction construction = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        AutoProviderProficiencyComponent proficiencyComponent = new AutoProviderProficiencyComponent(construction);
        construction.setProficiencyComponent(proficiencyComponent);

        DemoSimpleAutoOutputComponent outputComponent = new DemoSimpleAutoOutputComponent(construction);
        construction.setOutputComponent(outputComponent);

        construction.getOutputComponent().setOutputCostPack(DemoBuiltinConstructionsLoader.toPack(new HashMap<>()));
        construction.getOutputComponent().setOutputGainPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.MUSHROOM, 1
        )));

        construction.getUpgradeComponent().setUpgradeCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.MUSHROOM, 50
        )));

        return construction;
    }

    public static class AutoProviderProficiencyComponent extends BaseAutoProficiencyComponent {

        public AutoProviderProficiencyComponent(
                BaseConstruction construction
        ) {
            super(construction, 1, 100);
        }

        @Override
        protected void tryProficiencyOnce() {
            long neighborProviderCount = construction.getNeighbors().values().stream()
                    .map(it -> (BaseConstruction)it)
                    .filter(it -> it != null
                            && (
                                    it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER)
                                            || it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER)
                                            || it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER)
                            )
                    )
                    .count();
            long neighborTreeCount = construction.getNeighbors().values().stream()
                    .map(it -> (BaseConstruction)it)
                    .filter(it -> it != null
                                    && (
                                    it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_1_TREE)
                                            || it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_2_TREE)
                                            || it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_3_TREE)
                            )
                    )
                    .count();
            int add = (int) Math.max(0, 1 + neighborTreeCount * 2 - neighborProviderCount);
            this.changeProficiency(add);
        }
    }
}
