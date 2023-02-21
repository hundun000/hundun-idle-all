package hundun.gdxgame.idleshare.core.framework.model;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.BuffManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.ConstructionManager;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.StorageManager;
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
    GameEntityManager gameEntityManager;
    
    
    public ManagerContext(T_GAME game) {
        this.game = game;
    }
    
    public void lazyInitOnGameCreate(ChildGameConfig childGameConfig) {
        

        this.setGameEntityManager(new GameEntityManager(game));
        
        
        this.getGameEntityManager().lazyInit(childGameConfig.getAreaShowEntityByOwnAmountConstructionIds(), childGameConfig.getAreaShowEntityByOwnAmountResourceIds(), childGameConfig.getAreaShowEntityByChangeAmountResourceIds());
        //this.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        //this.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        //this.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
    }

}
