package hundun.gdxgame.idledemo.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.ConstProficiencyComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

public class BaseIdleForestConstruction extends BaseConstruction {

    public BaseIdleForestConstruction(
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

    public static BaseIdleForestConstruction typeAutoConstProficienc(String prototypeId,
                                                      String id,
                                                      GridPosition position,
                                                      DescriptionPackage descriptionPackage)
    {
        BaseIdleForestConstruction thiz = new BaseIdleForestConstruction(prototypeId, id, position, descriptionPackage);

        ConstProficiencyComponent proficiencyComponent = new ConstProficiencyComponent(thiz);
        thiz.proficiencyComponent = proficiencyComponent;

        IdleForestOutputComponent outputComponent = new IdleForestOutputComponent(thiz);
        thiz.outputComponent = outputComponent;

        return thiz;
    }

    public static BaseIdleForestConstruction typeClick(String prototypeId,
                                                                     String id,
                                                                     GridPosition position,
                                                                     DescriptionPackage descriptionPackage)
    {
        BaseIdleForestConstruction thiz = new BaseIdleForestConstruction(prototypeId, id, position, descriptionPackage);

        IdleForestClickOutputComponent outputComponent = new IdleForestClickOutputComponent(thiz);
        thiz.outputComponent = outputComponent;

        return thiz;
    }

}
