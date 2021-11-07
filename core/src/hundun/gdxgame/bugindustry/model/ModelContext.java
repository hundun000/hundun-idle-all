package hundun.gdxgame.bugindustry.model;

import hundun.gdxgame.bugindustry.model.construction.BeeGatherConstruction;
import hundun.gdxgame.bugindustry.model.construction.SmallBeehiveConstruction;
import hundun.gdxgame.bugindustry.model.construction.WoodGatherConstruction;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class ModelContext {
    StorageModel storageModel;
    WoodGatherConstruction woodGatherConstruction;
    BeeGatherConstruction beeGatherConstruction;
    SmallBeehiveConstruction smallBeehiveConstruction;
}
