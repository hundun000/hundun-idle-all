package hundun.gdxgame.idlemushroom.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.loader.IdleMushroomConstructionsLoader;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idlemushroom.logic.construction.BaseIdleMushroomConstruction;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.construction.MainClickerOutputComponent;
import hundun.gdxgame.idlemushroom.util.IdleMushroomJavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;


public class MainMushroomPrototype extends AbstractConstructionPrototype {
    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .outputGainDescriptionStart("Click gain: ")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP.build())
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .outputGainDescriptionStart("点击获得：")
            .levelDescriptionProvider(DescriptionPackageFactory.CN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.CN_PROFICIENCY_IMP.build())
            .build();

    public MainMushroomPrototype(Language language) {
        super(
                IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM,
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
        BaseIdleMushroomConstruction construction = new BaseIdleMushroomConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(construction);
        construction.setProficiencyComponent(proficiencyComponent);

        MainClickerOutputComponent outputComponent = new MainClickerOutputComponent(construction);
        outputComponent.setTypeClickOutput(true);
        construction.setOutputComponent(outputComponent);

        construction.getOutputComponent().setOutputCostPack(IdleMushroomConstructionsLoader.toPack(new HashMap<>()));
        construction.getOutputComponent().setOutputGainPack(IdleMushroomConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.MUSHROOM, 1
        )));

        construction.getUpgradeComponent().setUpgradeCostPack(IdleMushroomConstructionsLoader.toPack(new HashMap<>()));

        return construction;
    }
}
