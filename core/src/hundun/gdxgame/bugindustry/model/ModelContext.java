package hundun.gdxgame.bugindustry.model;

import hundun.gdxgame.bugindustry.data.ConstructionSaveData;
import hundun.gdxgame.bugindustry.model.construction.ConstructionFactory;
import hundun.gdxgame.bugindustry.model.manager.AchievementManager;
import hundun.gdxgame.bugindustry.model.manager.BuffManager;
import hundun.gdxgame.bugindustry.model.manager.ConstructionManager;
import hundun.gdxgame.bugindustry.model.manager.EventManager;
import hundun.gdxgame.bugindustry.model.manager.StorageManager;
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
    
    ConstructionFactory constructionFactory;
    ConstructionManager constructionManager;
}
