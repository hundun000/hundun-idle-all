package hundun.gdxgame.idleframe.model.construction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public abstract class BaseConstructionFactory {
    
    Map<String, BaseConstruction> constructions = new HashMap<>();
    protected BaseIdleGame game;

    public abstract void lazyInit();
    
    protected void register(BaseConstruction construction) {
        constructions.put(construction.getId(), construction);
    }
    
    public BaseConstruction getConstruction(String id) {
        var result = constructions.get(id);
        if (result == null) {
            throw new RuntimeException("getConstruction " + id + " not found");
        }
        return result;
    }
    
    public Collection<BaseConstruction> getConstructions() {
        return constructions.values();
    }
}
