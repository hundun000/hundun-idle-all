package hundun.gdxgame.idlemushroom.logic.prototype;

import hundun.gdxgame.idlemushroom.logic.construction.BaseIdleMushroomConstruction;
import hundun.gdxgame.idlemushroom.util.IdleMushroomJavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.SimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;



public class TreePrototype extends AbstractConstructionPrototype {
    public static DescriptionPackage descriptionPackageEN = DescriptionPackage.builder()
            .name("Tree Tile")
            .wikiText("Tree Tile" + "：\n" +
                    "•Beneficial for the growth of surrounding mushroom tiles。")
            .levelDescriptionProvider(DescriptionPackageFactory.EN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.EN_PROFICIENCY_IMP.build())
            .build();


    public static DescriptionPackage descriptionPackageCN = DescriptionPackage.builder()
            .name("树木地块")
            .wikiText("树木地块" + "：\n" +
                    "•利于周围蘑菇地块的成长。")
            .levelDescriptionProvider(DescriptionPackageFactory.CN_LEVEL_IMP.build())
            .proficiencyDescriptionProvider(DescriptionPackageFactory.CN_PROFICIENCY_IMP.build())
            .build();

    public TreePrototype(String prototypeId, Language language) {
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

        BaseIdleMushroomConstruction thiz = new BaseIdleMushroomConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(thiz);
        thiz.setProficiencyComponent(proficiencyComponent);

        SimpleAutoOutputComponent outputComponent = new SimpleAutoOutputComponent(thiz);
        outputComponent.setTypeClickOutput(true);
        thiz.setOutputComponent(outputComponent);

        return thiz;
    }
}
