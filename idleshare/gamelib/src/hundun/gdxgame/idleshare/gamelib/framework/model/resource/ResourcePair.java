package hundun.gdxgame.idleshare.gamelib.framework.model.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResourceType and amount
 * @author hundun
 * Created on 2021/12/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcePair {
    String type;
    Long amount;
}
