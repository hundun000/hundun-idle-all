package hundun.gdxgame.idleshare.gamelib.framework.model.resource;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Data
public class ResourcePack {
    String descriptionStart;
    List<ResourcePair> baseValues;
    List<ResourcePair> modifiedValues;
    List<ResourcePair> previewNextLevelModifiedValues;
    public static String toDescription(List<ResourcePair> list) {
        return list.stream()
                .map(pair -> pair.getType() + "x" + pair.getAmount())
                .collect(Collectors.joining(", "))
                + "; ";
    }
}
