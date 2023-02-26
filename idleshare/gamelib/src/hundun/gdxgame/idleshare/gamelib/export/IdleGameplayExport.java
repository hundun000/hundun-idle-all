package hundun.gdxgame.idleshare.gamelib.export;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.gamelib.starter.save.StarterSaveHandler.ISubGameplaySaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2023/02/21
 */
public class IdleGameplayExport implements ILogicFrameListener, ISubGameplaySaveHandler<GameplaySaveData>{

    private IdleGameplayContext gameplayContext;
    
    public IdleGameplayExport(
            IFrontend frontEnd, 
            IGameDictionary gameDictionary,
            BaseConstructionFactory constructionFactory,
            int LOGIC_FRAME_PER_SECOND, ChildGameConfig childGameConfig) {
        this.gameplayContext = new IdleGameplayContext(frontEnd, gameDictionary, constructionFactory, LOGIC_FRAME_PER_SECOND, childGameConfig);
    }

    public long getResourceNumOrZero(String resourceId) {
        return gameplayContext.getStorageManager().getResourceNumOrZero(resourceId);
    }

    public BaseConstruction getConstruction(String id) {
        return gameplayContext.getConstructionFactory().getConstruction(id);
    }

    @Override
    public void onLogicFrame() {
        gameplayContext.getConstructionManager().onSubLogicFrame();
        gameplayContext.getStorageManager().onSubLogicFrame();
    }
    

    public static class ConstructionExportProxy {
        
        private BaseConstruction model;
        @Getter
        String id;
        @Getter
        String name;
        @Getter
        DescriptionPackage descriptionPackage;
        @Getter
        ResourcePack outputGainPack;
        @Getter
        ResourcePack outputCostPack;
        @Getter
        UpgradeState upgradeState;
        @Getter
        ResourcePack upgradeCostPack;
        
        private static ConstructionExportProxy fromModel(BaseConstruction model) {
            ConstructionExportProxy result = new ConstructionExportProxy();
            result.model = model;
            result.id = model.id;
            result.name = model.name;
            result.outputCostPack = (model.getOutputComponent().getOutputCostPack());
            result.outputGainPack = (model.getOutputComponent().getOutputGainPack());
            result.upgradeState = (model.getUpgradeComponent().getUpgradeState());
            result.upgradeCostPack = (model.getUpgradeComponent().getUpgradeCostPack());
            result.descriptionPackage = (model.getDescriptionPackage());
            
            return result;
        }

        // ------- need runtime calculate ------
        
        public boolean isWorkingLevelChangable() {
            return model.getLevelComponent().isWorkingLevelChangable();
        }
        
        public String getButtonDescroption() {
            return model.getButtonDescroption();
        }
        
        public String getDetailDescroptionConstPart() {
            return model.detailDescroptionConstPart;
        }
        
        public String getWorkingLevelDescroption() {
            return model.getLevelComponent().getWorkingLevelDescroption();
        }
    }

    public List<ConstructionExportProxy> getAreaShownConstructionsOrEmpty(String current) {
        return gameplayContext.getConstructionManager().getAreaShownConstructionsOrEmpty(current).stream()
                .map(it -> ConstructionExportProxy.fromModel(it))
                .collect(Collectors.toList())
                ;
    }

    public void eventManagerRegisterListener(Object object) {
        gameplayContext.getEventManager().registerListener(object);
    }

    public Set<String> getUnlockedResourceTypes() {
        return gameplayContext.getStorageManager().getUnlockedResourceTypes();
    }

    public void constructionChangeWorkingLevel(String id, int delta) {
        BaseConstruction model = gameplayContext.getConstructionFactory().getConstruction(id);
        model.getLevelComponent().changeWorkingLevel(delta);
    }

    public void constructionOnClick(String id) {
        BaseConstruction model = gameplayContext.getConstructionFactory().getConstruction(id);
        model.onClick();
    }

    public boolean constructionCanClickEffect(String id) {
        BaseConstruction model = gameplayContext.getConstructionFactory().getConstruction(id);
        return model.canClickEffect();
    }

    public boolean constructionCanChangeWorkingLevel(String id, int delta) {
        BaseConstruction model = gameplayContext.getConstructionFactory().getConstruction(id);
        return model.getLevelComponent().canChangeWorkingLevel(delta);
    }
    
    @Override
    public void applyGameSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = gameplayContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            if (gameplaySaveData.getConstructionSaveDataMap().containsKey(construction.id)) {
                construction.setSaveData(gameplaySaveData.getConstructionSaveDataMap().get(construction.id));
                construction.updateModifiedValues();
            }
        }
        gameplayContext.getStorageManager().setUnlockedResourceTypes(gameplaySaveData.getUnlockedResourceTypes());
        gameplayContext.getStorageManager().setOwnResoueces(gameplaySaveData.getOwnResoueces());
        gameplayContext.getAchievementManager().setUnlockedAchievementNames(gameplaySaveData.getUnlockedAchievementNames());
    }

    @Override
    public void currentSituationToSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = gameplayContext.getConstructionFactory().getConstructions();
        gameplaySaveData.setConstructionSaveDataMap(constructions.stream()
                .collect(Collectors.toMap(
                        it -> it.getId(), 
                        it -> it.getSaveData()
                        ))
                );
        gameplaySaveData.setUnlockedResourceTypes(gameplayContext.getStorageManager().getUnlockedResourceTypes());
        gameplaySaveData.setOwnResoueces(gameplayContext.getStorageManager().getOwnResoueces());
        gameplaySaveData.setUnlockedAchievementNames(gameplayContext.getAchievementManager().getUnlockedAchievementNames());
    }
    
}
