package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.ConstructionBuyCandidateConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ExistenceComponent {
    private final BaseConstruction construction;

    /**
     * Nullable
     */
    public ResourcePack destroyCostPack;
    /**
     * Nullable
     */
    public ResourcePack destroyGainPack;

    public boolean allowAnyProficiencyDestroy;
    /**
     * Nullable
     */
    @Setter
    @Getter
    private List<ConstructionBuyCandidateConfig> buyCandidateConfigs;


    public String constructionPrototypeIdOfEmpty;
    public ExistenceComponent(BaseConstruction construction)
    {
        this.construction = construction;

    }

    public void updateModifiedValues()
    {
        if (destroyGainPack != null && destroyCostPack != null)
        {
            destroyCostPack.setModifiedValues(destroyCostPack.getBaseValues());
            destroyGainPack.setModifiedValues(destroyGainPack.getBaseValues());
        }
        if (buyCandidateConfigs != null)
        {
            buyCandidateConfigs.forEach(it -> {
                it.getBuyCostPack().setModifiedValues(it.getBuyCostPack().getBaseValues());
            });
        }
    }

    public Boolean canDestroy()
    {
        if (!allowAnyProficiencyDestroy && !construction.proficiencyComponent.isMaxProficiency())
        {
            return false;
        }
        return destroyGainPack != null && destroyCostPack != null && construction.gameplayContext.getStorageManager().isEnough(destroyCostPack.getModifiedValues());
    }

    public void doDestroy()
    {
        construction.gameplayContext.getConstructionManager().addToRemoveQueue(construction);
        construction.gameplayContext.getStorageManager().modifyAllResourceNum(construction.existenceComponent.destroyCostPack.getModifiedValues(), false);
        construction.gameplayContext.getStorageManager().modifyAllResourceNum(construction.existenceComponent.destroyGainPack.getModifiedValues(), true);

        if (constructionPrototypeIdOfEmpty != null)
        {
            construction.gameplayContext.getConstructionManager().addToCreateQueue(constructionPrototypeIdOfEmpty, construction.getPosition());
        }
    }
}
