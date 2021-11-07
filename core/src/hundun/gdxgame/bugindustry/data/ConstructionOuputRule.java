package hundun.gdxgame.bugindustry.data;
/**
 * per logic-frame
 * @author hundun
 * Created on 2021/11/05
 */

import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstructionOuputRule {
    
    ResourceType resourceType;
    int amount;
    int successRate;
}
