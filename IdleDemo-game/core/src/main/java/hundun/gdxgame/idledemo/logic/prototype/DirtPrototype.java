package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
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


    public DirtPrototype(Language language) {
        super(
                DemoConstructionPrototypeId.EMPTY_CELL,
                language
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = Const.templateDescriptionPackageEN
                        .name("空地块")
                        .wikiText("可转变为其他地块。")
                        .build();;
                break;
            default:
                this.descriptionPackage = Const.templateDescriptionPackageEN
                        .name("Empty tile")
                        .wikiText("Can be transformed into other tile.")
                        .build();
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
        outputComponent.setTypeClickOutput(true);
        thiz.setOutputComponent(outputComponent);

        thiz.getExistenceComponent().setBuyCandidateConfigs(JavaFeatureForGwt.listOf(
                ConstructionBuyCandidateConfig.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER)
                        .buyCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                                ResourceType.COIN, 50
                        )))
                        .build(),
                ConstructionBuyCandidateConfig.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER)
                        .buyCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                                ResourceType.COIN, 50
                        )))
                        .build()
        ));

        return thiz;
    }
}
