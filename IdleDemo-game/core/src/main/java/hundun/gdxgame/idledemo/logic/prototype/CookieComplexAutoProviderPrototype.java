package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idledemo.logic.construction.DemoSimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackageFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.UUID;

public class CookieComplexAutoProviderPrototype extends AbstractConstructionPrototype {

    public CookieComplexAutoProviderPrototype(Language language) {
        super(
                DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER,
                language
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = Const.templateDescriptionPackageCN
                        .name("高级自动点击器")
                        .wikiText("自动获得饼干")
                        .build();
                break;
            default:
                this.descriptionPackage = Const.templateDescriptionPackageEN
                        .name("ComplexAutoClicker")
                        .wikiText("Auto gain some cookie")
                        .build();
                break;
        }

    }

    @Override
    public BaseConstruction getInstance(GridPosition position) {
        String id = prototypeId + "_" + UUID.randomUUID().toString();
        BaseIdleDemoConstruction construction = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        CookieComplexAutoProviderProficiencyComponent proficiencyComponent = new CookieComplexAutoProviderProficiencyComponent(construction);
        construction.setProficiencyComponent(proficiencyComponent);

        DemoSimpleAutoOutputComponent outputComponent = new DemoSimpleAutoOutputComponent(construction);
        construction.setOutputComponent(outputComponent);

        construction.getOutputComponent().setOutputCostPack(DemoBuiltinConstructionsLoader.toPack(new HashMap<>()));
        construction.getOutputComponent().setOutputGainPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.COOKIE, 1
        )));

        construction.getUpgradeComponent().setUpgradeCostPack(DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf(
                ResourceType.COIN, 50
        )));

        return construction;
    }

    public static class CookieComplexAutoProviderProficiencyComponent extends BaseAutoProficiencyComponent {

        public CookieComplexAutoProviderProficiencyComponent(
                BaseConstruction construction
        ) {
            super(construction, 1, 0);
        }

        @Override
        protected void tryProficiencyOnce() {
            long neighborCount = construction.getNeighbors().values().stream()
                    .map(it -> (BaseConstruction)it)
                    .filter(it -> it != null
                            && (
                                    it.getSaveData().prototypeId.equals(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER)
                                    || it.getSaveData().prototypeId.equals(DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER)
                            )
                    )
                    .count();
            this.changeProficiency((int) neighborCount);
        }
    }
}
