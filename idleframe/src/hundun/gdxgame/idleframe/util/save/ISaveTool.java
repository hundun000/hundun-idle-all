package hundun.gdxgame.idleframe.util.save;

import java.util.Map;

import hundun.gdxgame.idleframe.model.ModelContext;

/**
 * @author hundun
 * Created on 2022/04/08
 */
public interface ISaveTool {
    boolean hasSave();
    void save(ModelContext modelContext);
    void load(ModelContext modelContext);
}