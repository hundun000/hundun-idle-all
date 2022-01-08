package hundun.gdxgame.idlestarter;

import java.util.List;

import hundun.gdxgame.idleframe.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/12/17
 */
@Data
public class ConstructionsConfig {
    
    List<ConstructionNode> nodes;
    
    @Data
    public static class ConstructionNode {
        String id;
        ConstructionClazz clazz;
        String detailDescroptionConstPart;
        DescriptionPackage descriptionPackage;
        ResourcePack upgradeCostPack;
        ResourcePack outputGainPack;
        ResourcePack outputCostPack;
        boolean workingLevelChangable;
    }
    
    
    public static enum ConstructionClazz {
        AUTO,
        CLICK,
        ;
    }
}
