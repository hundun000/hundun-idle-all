package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idledemo.logic.construction.DemoSimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.UUID;

public class CookieSingletonAutoSellerPrototype extends AbstractConstructionPrototype {

    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .name("AutoSeller")
            .upgradeButtonText("Upgrade")
            .outputCostDescriptionStart("Consume")
            .outputGainDescriptionStart("Produce")
            .upgradeCostDescriptionStart("Upgrade cost")
            .upgradeMaxLevelDescription("(max)")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP.build())
            .build();

    public CookieSingletonAutoSellerPrototype(Language language) {
        super(
                DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER,
                language
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = Const.templateDescriptionPackageCN
                        .name("自动出售器")
                        .wikiText("自动出售饼干")
                        .build();
                break;
            default:
                this.descriptionPackage = Const.templateDescriptionPackageEN
                        .name("AutoSeller")
                        .wikiText("Auto sell some cookie")
                        .build();
                break;
        }

    }

    @Override
    public BaseConstruction getInstance(GridPosition position) {
        String id = prototypeId + "_" + UUID.randomUUID().toString();
        BaseIdleDemoConstruction construction = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(construction);
        construction.setProficiencyComponent(proficiencyComponent);

        DemoSimpleAutoOutputComponent outputComponent = new DemoSimpleAutoOutputComponent(construction);
        construction.setOutputComponent(outputComponent);

        construction.getOutputComponent().setOutputCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.COOKIE, 1
        )));
        construction.getOutputComponent().setOutputGainPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.COIN, 5
        )));

        construction.getUpgradeComponent().setUpgradeCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.COIN, 50
        )));
        construction.getLevelComponent().setTypeWorkingLevelChangeable(true);

        return construction;
    }
}
