package hundun.gdxgame.idleshare.framework.model;

import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.framework.model.manager.AchievementManager;
import hundun.gdxgame.idleshare.framework.model.manager.BuffManager;
import hundun.gdxgame.idleshare.framework.model.manager.ConstructionManager;
import hundun.gdxgame.idleshare.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.framework.model.manager.StorageManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Getter
@Setter
public class ManagerContext<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> {
    
    T_GAME game;
    
    StorageManager storageManager;
    BuffManager buffManager;
    AchievementManager achievementManager;

    BaseConstructionFactory constructionFactory;
    ConstructionManager constructionManager;
    GameEntityManager gameEntityManager;
    
    public ManagerContext(T_GAME game) {
        this.game = game;
    }
    
    public void lazyInitOnGameCreate(ChildGameConfig childGameConfig) {
        
        this.setStorageManager(new StorageManager(game));
        this.setBuffManager(new BuffManager(game));

        this.setConstructionFactory(new BaseConstructionFactory());
        this.setAchievementManager(new AchievementManager(game));
        this.setConstructionManager(new ConstructionManager(game));
        this.setGameEntityManager(new GameEntityManager(game));
        
        this.getGameEntityManager().lazyInit(childGameConfig.getAreaShowEntityByOwnAmountConstructionIds(), childGameConfig.getAreaShowEntityByOwnAmountResourceIds(), childGameConfig.getAreaShowEntityByChangeAmountResourceIds());
        this.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        this.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        this.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
    }

}
