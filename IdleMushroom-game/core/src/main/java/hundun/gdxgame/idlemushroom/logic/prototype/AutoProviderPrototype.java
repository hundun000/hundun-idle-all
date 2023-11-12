package hundun.gdxgame.idlemushroom.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.logic.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idlemushroom.logic.ResourceType;
import hundun.gdxgame.idlemushroom.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idlemushroom.logic.construction.DemoSimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.IProficiencyDescroptionProvider;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.UUID;

public class AutoProviderPrototype extends AbstractConstructionPrototype {

    public static IProficiencyDescroptionProvider CN_PROFICIENCY_IMP = (proficiency, reachMaxProficiency) -> {
        return "成长度：" + proficiency;
    };

    public static IProficiencyDescroptionProvider EN_PROFICIENCY_IMP = (proficiency, reachMaxProficiency) -> {
        return "Growth: " + proficiency;
    };
    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .upgradeButtonText("Upgrade")
            .outputCostDescriptionStart("Consume")
            .outputGainDescriptionStart("Produce")
            .upgradeCostDescriptionStart("Upgrade cost")
            .upgradeMaxLevelDescription("(max)")
            .levelDescriptionProvider(DescriptionPackageFactory.ONLY_LEVEL_IMP)
            .proficiencyDescriptionProvider(AutoProviderPrototype.EN_PROFICIENCY_IMP)
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .upgradeButtonText("升级")
            .outputCostDescriptionStart("消耗")
            .outputGainDescriptionStart("产出")
            .upgradeCostDescriptionStart("升级费用")
            .upgradeMaxLevelDescription("(已达到最大等级)")
            .levelDescriptionProvider(DescriptionPackageFactory.ONLY_LEVEL_IMP)
            .proficiencyDescriptionProvider(AutoProviderPrototype.CN_PROFICIENCY_IMP)
            .build();


    private final RootEpochConfig rootEpochConfig;

    public AutoProviderPrototype(String prototypeId, RootEpochConfig rootEpochConfig, Language language) {
        super(
                prototypeId,
                language
        );
        this.rootEpochConfig = rootEpochConfig;
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
        String id = prototypeId + "_" + UUID.randomUUID().toString();
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
                ResourceType.DNA_POINT, 50
        )));

        construction.getLevelComponent().maxLevel = rootEpochConfig.getMaxLevel();

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
            long neighborCount = construction.getNeighbors().values().stream()
                    .map(it -> (BaseConstruction)it)
                    .filter(it -> it != null
                                    && (
                                    it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER)
                                            || it.getSaveData().prototypeId.equals(IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER)
                            )
                    )
                    .count();
            this.changeProficiency((int) neighborCount);
        }
    }
}
