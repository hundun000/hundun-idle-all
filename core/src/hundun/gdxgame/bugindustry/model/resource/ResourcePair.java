package hundun.gdxgame.bugindustry.model.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResourceType and amount
 * @author hundun
 * Created on 2021/12/02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourcePair {
    ResourceType type;
    Long amount;
}
