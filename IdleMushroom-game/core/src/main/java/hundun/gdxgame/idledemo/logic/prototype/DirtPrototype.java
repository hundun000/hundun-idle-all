package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.ConstructionBuyCandidateConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.SimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.UUID;

public class DirtPrototype extends AbstractConstructionPrototype {
    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .levelDescriptionProvider(DescriptionPackageFactory.NO_LEVEL_IMP)
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP)
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .levelDescriptionProvider(DescriptionPackageFactory.CN_NO_LEVEL_IMP)
            .proficiencyDescriptionProvider(DescriptionPackageFactory.CN_PROFICIENCY_IMP)
            .build();

    public DirtPrototype(String prototypeId, Language language) {
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
        String id = prototypeId + "_" + UUID.randomUUID().toString();

        BaseIdleDemoConstruction thiz = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(thiz);
        thiz.setProficiencyComponent(proficiencyComponent);

        SimpleAutoOutputComponent outputComponent = new SimpleAutoOutputComponent(thiz);
        thiz.setOutputComponent(outputComponent);


        switch (prototypeId) {
            case DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
                thiz.getExistenceComponent().setBuyCandidateConfigs(JavaFeatureForGwt.listOf(
                        ConstructionBuyCandidateConfig.builder()
                                .prototypeId(DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER)
                                .buyCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                                        ResourceType.MUSHROOM, 50
                                )))
                                .build()
                ));
                break;
            case DemoConstructionPrototypeId.EPOCH_2_EMPTY_CELL:
                thiz.getExistenceComponent().setBuyCandidateConfigs(JavaFeatureForGwt.listOf(
                        ConstructionBuyCandidateConfig.builder()
                                .prototypeId(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER)
                                .buyCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                                        ResourceType.MUSHROOM, 50
                                )))
                                .build()
                ));
                break;
            case DemoConstructionPrototypeId.EPOCH_3_EMPTY_CELL:
                thiz.getExistenceComponent().setBuyCandidateConfigs(JavaFeatureForGwt.listOf(
                        ConstructionBuyCandidateConfig.builder()
                                .prototypeId(DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER)
                                .buyCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                                        ResourceType.MUSHROOM, 50
                                )))
                                .build()
                ));
                break;
            default:
        }


        return thiz;
    }
}
