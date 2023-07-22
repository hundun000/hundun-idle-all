package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;

public class ExistenceComponent {
    private final BaseConstruction construction;

    /**
     * Nullable
     */
    public ResourcePack destoryCostPack;
    /**
     * Nullable
     */
    public ResourcePack destoryGainPack;

    public boolean allowAnyProficiencyDestory;

    public ExistenceComponent(BaseConstruction construction)
    {
        this.construction = construction;

    }

    public void lazyInitDescription()
    {
        if (destoryGainPack != null && destoryCostPack != null)
        {
            this.destoryGainPack.setDescriptionStart(construction.descriptionPackage.getDestroyGainDescriptionStart());
            this.destoryCostPack.setDescriptionStart(construction.descriptionPackage.getDestroyCostDescriptionStart());
        }
    }

    public void updateModifiedValues()
    {
        if (destoryGainPack != null && destoryCostPack != null)
        {
            destoryCostPack.setModifiedValues(destoryCostPack.getBaseValues());
            destoryCostPack.setModifiedValuesDescription(ResourcePack.toDescription(destoryCostPack.getModifiedValues()));
            destoryGainPack.setModifiedValues(destoryGainPack.getBaseValues());
            destoryGainPack.setModifiedValuesDescription(ResourcePack.toDescription(destoryGainPack.getModifiedValues()));
        }
    }

    public Boolean canDestory()
    {
        if (!allowAnyProficiencyDestory && !construction.proficiencyComponent.isMaxProficiency())
        {
            return false;
        }
        return destoryGainPack != null && destoryCostPack != null && construction.gameplayContext.getStorageManager().isEnough(destoryCostPack.getModifiedValues());
    }

    public void doDestory(String constructionPrototypeIdOfEmpty)
    {
        construction.gameplayContext.getConstructionManager().addToRemoveQueue(construction);
        construction.gameplayContext.getStorageManager().modifyAllResourceNum(construction.existenceComponent.destoryCostPack.getModifiedValues(), false);
        construction.gameplayContext.getStorageManager().modifyAllResourceNum(construction.existenceComponent.destoryGainPack.getModifiedValues(), true);

        if (constructionPrototypeIdOfEmpty != null)
        {
            construction.gameplayContext.getConstructionManager().addToCreateQueue(constructionPrototypeIdOfEmpty, construction.getPosition());
        }
    }
}
