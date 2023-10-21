package hundun.gdxgame.idledemo.logic.prototype;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.construction.BaseIdleDemoConstruction;
import hundun.gdxgame.idledemo.logic.construction.DemoSimpleAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.UUID;

public class Epoch2AutoProviderPrototype extends AbstractConstructionPrototype {

    public Epoch2AutoProviderPrototype(Language language) {
        super(
                DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                language,
                DemoBuiltinConstructionsLoader.toPack(JavaFeatureForGwt.mapOf())
        );
        switch (language)
        {
            case CN:
                this.descriptionPackage = Epoch1AutoProviderPrototype.descriptionPackageCN;
                break;
            default:
                this.descriptionPackage = Epoch1AutoProviderPrototype.descriptionPackageEN;
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

        return construction;
    }

    public static class AutoProviderProficiencyComponent extends BaseAutoProficiencyComponent {

        public AutoProviderProficiencyComponent(
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
                                    it.getSaveData().prototypeId.equals(DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER)
                                    || it.getSaveData().prototypeId.equals(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER)
                            )
                    )
                    .count();
            this.changeProficiency((int) neighborCount);
        }
    }
}
