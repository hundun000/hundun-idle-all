package hundun.gdxgame.idleshare.gamelib.framework;

import java.util.Collection;
import java.util.stream.Collectors;

import hundun.gdxgame.gamelib.base.IFrontend;
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
public class IdleGameplayContext {
    public final int LOGIC_FRAME_PER_SECOND;


    
    final IFrontend frontEnd;
    
    final EventManager eventManager;
    final StorageManager storageManager;
    final BuffManager buffManager;
    final AchievementManager achievementManager;
    final BaseConstructionFactory constructionFactory;
    final ConstructionManager constructionManager;
    final IGameDictionary gameDictionary;
    
    public IdleGameplayContext(
            IFrontend frontEnd, 
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
    

}
