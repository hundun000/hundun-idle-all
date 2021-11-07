package hundun.gdxgame.bugindustry.model;

import hundun.gdxgame.bugindustry.model.construction.auto.SmallBeehiveConstruction;
import hundun.gdxgame.bugindustry.model.construction.buff.HoneyBuffConstruction;
import hundun.gdxgame.bugindustry.model.construction.click.BeeGatherConstruction;
import hundun.gdxgame.bugindustry.model.construction.click.WoodGatherConstruction;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class ModelContext {
    StorageModel storageModel;
    BuffManager buffManager;
    WoodGatherConstruction woodGatherConstruction;
    BeeGatherConstruction beeGatherConstruction;
    SmallBeehiveConstruction smallBeehiveConstruction;
    HoneyBuffConstruction honeyBuffConstruction;
}
