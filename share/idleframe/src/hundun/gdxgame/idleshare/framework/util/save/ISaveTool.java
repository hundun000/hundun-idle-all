package hundun.gdxgame.idleshare.framework.util.save;

import hundun.gdxgame.idleshare.framework.model.ModelContext;

/**
 * @author hundun
 * Created on 2022/04/08
 */
public interface ISaveTool {
    void lazyInitOnGameCreate();
    boolean hasSave();
    void save(ModelContext modelContext);
    void load(ModelContext modelContext);
}