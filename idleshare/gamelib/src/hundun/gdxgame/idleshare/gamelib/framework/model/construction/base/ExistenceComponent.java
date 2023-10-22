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

    public void lazyInitDescription(IdleGameplayContext gameContext, Language language)
    {
        if (destroyGainPack != null && destroyCostPack != null)
        {
            this.destroyGainPack.setDescriptionStart(construction.descriptionPackage.getDestroyGainDescriptionStart());
            this.destroyCostPack.setDescriptionStart(construction.descriptionPackage.getDestroyCostDescriptionStart());
        }
        if (buyCandidateConfigs != null)
        {
            buyCandidateConfigs.forEach(it -> it.getBuyCostPack().setDescriptionStart(gameContext.getGameDictionary().getPlayScreenTexts(language).get(1)));
        }
    }

    public void updateModifiedValues()
    {
        if (destroyGainPack != null && destroyCostPack != null)
        {
            destroyCostPack.setModifiedValues(destroyCostPack.getBaseValues());
            destroyCostPack.setModifiedValuesDescription(ResourcePack.toDescription(destroyCostPack.getModifiedValues()));
            destroyGainPack.setModifiedValues(destroyGainPack.getBaseValues());
            destroyGainPack.setModifiedValuesDescription(ResourcePack.toDescription(destroyGainPack.getModifiedValues()));
        }
        if (buyCandidateConfigs != null)
        {
            buyCandidateConfigs.forEach(it -> {
                it.getBuyCostPack().setModifiedValues(it.getBuyCostPack().getBaseValues());
                it.getBuyCostPack().setModifiedValuesDescription(ResourcePack.toDescription(it.getBuyCostPack().getModifiedValues()));
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
