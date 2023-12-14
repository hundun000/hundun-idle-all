package hundun.gdxgame.idleshare.gamelib.framework;

import java.util.Map;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.ConstructionManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.StorageManager;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
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

    public IdleGameplayContext(
            IFrontend frontEnd,
            int LOGIC_FRAME_PER_SECOND) {
        this.LOGIC_FRAME_PER_SECOND = LOGIC_FRAME_PER_SECOND;
        
        this.frontEnd = frontEnd;
        
        this.eventManager = new EventManager(this);
        this.storageManager = new StorageManager(this);
        this.buffManager = new BuffManager(this);
        this.achievementManager = new AchievementManager(this);
        this.constructionFactory = new BaseConstructionFactory();
        this.constructionManager = new ConstructionManager(this);

    }
    
    public void allLazyInit(
            Language language,
            ChildGameConfig childGameConfig,
            Map<String, AbstractConstructionPrototype> providerMap,
            Map<String, AbstractAchievementPrototype> achievementProviderMap
    ) {
        this.getConstructionFactory().lazyInit(this, language, providerMap);
        this.getConstructionManager().lazyInit(childGameConfig.getConstructionConfig());
    }

}
