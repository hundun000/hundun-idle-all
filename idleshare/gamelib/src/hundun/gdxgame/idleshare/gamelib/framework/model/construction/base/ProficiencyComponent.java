package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

public abstract class ProficiencyComponent {

    public int maxProficiency = 100;
    protected final BaseConstruction construction;
    public String promoteConstructionPrototypeId;
    public String demoteConstructionPrototypeId;

    public ProficiencyComponent(BaseConstruction construction)
    {
        this.construction = construction;
    }

    public String getProficiencyDescroption()
    {
        Boolean reachMaxLevel = construction.saveData.getProficiency() >= this.maxProficiency;
        return construction.descriptionPackage.getProficiencyDescroptionProvider().provide(construction.saveData.getProficiency(), reachMaxLevel);
    }

    public Boolean canPromote()
    {
        return isMaxProficiency() && promoteConstructionPrototypeId != null;
    }

    public Boolean canDemote()
    {
        return (construction.saveData.getProficiency() < 0) && demoteConstructionPrototypeId != null;
    }

    public void doPromote()
    {
        construction.gameplayContext.getConstructionManager().addToRemoveQueue(construction);
        construction.gameplayContext.getConstructionManager().addToCreateQueue(construction.proficiencyComponent.promoteConstructionPrototypeId, construction.getPosition());
    }

    public void doDemote()
    {
        construction.gameplayContext.getConstructionManager().addToRemoveQueue(construction);
        construction.gameplayContext.getConstructionManager().addToCreateQueue(construction.proficiencyComponent.demoteConstructionPrototypeId, construction.getPosition());
    }

    public void changeProficiency(int delta)
    {
        construction.saveData.setProficiency(Math.max(0, Math.min(construction.saveData.getProficiency() + delta, this.maxProficiency)));
        construction.updateModifiedValues();
        //construction.gameContext.frontend.log(construction.name, "changeProficiency delta = " + delta + ", success to " + construction.saveData.proficiency);
    }

    public void cleanProficiency()
    {

        construction.saveData.setProficiency(0);
        construction.updateModifiedValues();
        construction.gameplayContext.getFrontEnd().log(construction.name, "cleanProficiency");

    }

    public abstract void onSubLogicFrame();

    protected void checkAutoPromoteDemote()
    {
        if (this.canPromote())
        {
            this.doPromote();
        }
        else if (this.canDemote())
        {
            this.doDemote();
        }
    }


    public abstract void afterUpgrade();
    public abstract boolean isEnoughProficiencyForUpgrade();
    public boolean isMaxProficiency()
    {
        return construction.saveData.getProficiency() >= maxProficiency;
    }
}
