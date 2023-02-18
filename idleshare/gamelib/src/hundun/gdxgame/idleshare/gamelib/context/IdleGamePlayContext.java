package hundun.gdxgame.idleshare.gamelib.context;

import java.util.Collection;
import java.util.stream.Collectors;

import hundun.gdxgame.gamelib.base.IFrontEnd;
import hundun.gdxgame.gamelib.starter.save.StarterSaveHandler.ISubGameplaySaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.BuffManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.ConstructionManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.EventManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.StorageManager;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2023/02/21
 */
@Getter
public class IdleGamePlayContext implements ISubGameplaySaveHandler<GameplaySaveData> {
    public final int LOGIC_FRAME_PER_SECOND;


    
    final IFrontEnd frontEnd;
    
    final EventManager eventManager;
    final StorageManager storageManager;
    final BuffManager buffManager;
    final AchievementManager achievementManager;
    final BaseConstructionFactory constructionFactory;
    final ConstructionManager constructionManager;
    final IGameDictionary gameDictionary;
    
    public IdleGamePlayContext(
            IFrontEnd frontEnd, 
            IGameDictionary gameDictionary,
            BaseConstructionFactory constructionFactory,
            int LOGIC_FRAME_PER_SECOND, ChildGameConfig childGameConfig) {
        this.LOGIC_FRAME_PER_SECOND = LOGIC_FRAME_PER_SECOND;
        
        this.frontEnd = frontEnd;
        
        this.eventManager = new EventManager(this);
        this.storageManager = new StorageManager(this);
        this.buffManager = new BuffManager(this);
        this.achievementManager = new AchievementManager(this);
        this.constructionFactory = constructionFactory;
        this.constructionManager = new ConstructionManager(this);
        this.gameDictionary = gameDictionary; 
        
        this.getConstructionFactory().lazyInit(this);
        this.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        this.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
    }
    
    @Override
    public void applyGameSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = this.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            if (gameplaySaveData.getConstructionSaveDataMap().containsKey(construction.id)) {
                construction.setSaveData(gameplaySaveData.getConstructionSaveDataMap().get(construction.id));
                construction.updateModifiedValues();
            }
        }
        this.getStorageManager().setUnlockedResourceTypes(gameplaySaveData.getUnlockedResourceTypes());
        this.getStorageManager().setOwnResoueces(gameplaySaveData.getOwnResoueces());
        this.getAchievementManager().setUnlockedAchievementNames(gameplaySaveData.getUnlockedAchievementNames());
    }

    @Override
    public void currentSituationToSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = this.getConstructionFactory().getConstructions();
        gameplaySaveData.setConstructionSaveDataMap(constructions.stream()
                .collect(Collectors.toMap(
                        it -> it.getId(), 
                        it -> it.getSaveData()
                        ))
                );
        gameplaySaveData.setUnlockedResourceTypes(this.getStorageManager().getUnlockedResourceTypes());
        gameplaySaveData.setOwnResoueces(this.getStorageManager().getOwnResoueces());
        gameplaySaveData.setUnlockedAchievementNames(this.getAchievementManager().getUnlockedAchievementNames());
    }
}
