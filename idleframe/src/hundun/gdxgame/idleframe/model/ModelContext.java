package hundun.gdxgame.idleframe.model;

import hundun.gdxgame.idleframe.data.ConstructionSaveData;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.manager.AchievementManager;
import hundun.gdxgame.idleframe.model.manager.BuffManager;
import hundun.gdxgame.idleframe.model.manager.ConstructionManager;
import hundun.gdxgame.idleframe.model.manager.EventManager;
import hundun.gdxgame.idleframe.model.manager.GameEntityManager;
import hundun.gdxgame.idleframe.model.manager.StorageManager;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class ModelContext {
    StorageManager storageManager;
    BuffManager buffManager;
    AchievementManager achievementManager;
    
    BaseConstructionFactory constructionFactory;
    ConstructionManager constructionManager;
    GameEntityManager gameEntityManager;
}
