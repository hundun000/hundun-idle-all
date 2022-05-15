package hundun.gdxgame.idleframe.model.construction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class BaseConstructionFactory {

    Map<String, BaseConstruction> constructions = new HashMap<>();
    protected BaseIdleGame game;

    public void lazyInit(List<BaseConstruction> constructions) {
        constructions.forEach(item -> register(item));
    }

    protected void register(BaseConstruction construction) {
        constructions.put(construction.getId(), construction);
    }

    public BaseConstruction getConstruction(String id) {
        BaseConstruction result = constructions.get(id);
        if (result == null) {
            throw new RuntimeException("getConstruction " + id + " not found");
        }
        return result;
    }

    public Collection<BaseConstruction> getConstructions() {
        return constructions.values();
    }
}
