package hundun.gdxgame.idledemo.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.EmptyAutoOutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

public class BaseIdleDemoConstruction extends BaseConstruction {

    public BaseIdleDemoConstruction(
            String prototypeId,
            String id,
            GridPosition position,
            DescriptionPackage descriptionPackage
    ) {
        super(prototypeId, id);

        this.descriptionPackage = descriptionPackage;

        UpgradeComponent upgradeComponent = new UpgradeComponent(this);
        this.upgradeComponent = upgradeComponent;

        LevelComponent levelComponent = new LevelComponent(this, false);
        this.levelComponent = levelComponent;

        ExistenceComponent existenceComponent = new ExistenceComponent(this);
        this.existenceComponent = existenceComponent;

        this.saveData.setPosition(position);
        this.saveData.setLevel(1);
        this.saveData.setWorkingLevel(1);
        this.saveData.setProficiency(0);
    }

    public static BaseIdleDemoConstruction typeAutoConstProficienc(String prototypeId,
                                                                   String id,
                                                                   GridPosition position,
                                                                   DescriptionPackage descriptionPackage)
    {
        BaseIdleDemoConstruction thiz = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(thiz);
        thiz.proficiencyComponent = proficiencyComponent;

        DemoSimpleAutoOutputComponent outputComponent = new DemoSimpleAutoOutputComponent(thiz);
        thiz.outputComponent = outputComponent;

        return thiz;
    }

    public static BaseIdleDemoConstruction typeClick(String prototypeId,
                                                     String id,
                                                     GridPosition position,
                                                     DescriptionPackage descriptionPackage)
    {
        BaseIdleDemoConstruction thiz = new BaseIdleDemoConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(thiz);
        thiz.proficiencyComponent = proficiencyComponent;

        EmptyAutoOutputComponent outputComponent = new EmptyAutoOutputComponent(thiz);
        outputComponent.setTypeClickOutput(true);
        thiz.outputComponent = outputComponent;

        return thiz;
    }

}
