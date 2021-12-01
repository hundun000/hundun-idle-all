package hundun.gdxgame.bugindustry.model.resource;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Data
public class ResourcePack {
    String descriptionStart;
    List<ResourcePair> baseValues;
    List<ResourcePair> modifiedValues;
    String modifiedValuesDescription;
}
