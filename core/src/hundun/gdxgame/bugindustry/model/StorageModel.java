package hundun.gdxgame.bugindustry.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class StorageModel {
    
    Map<ResourceType, Integer> ownResoueces = new HashMap<>();
    
    public String getResourceDescription(ResourceType key) {
        int amount = getResourceNum(key);
        return key.getShowName() + ": " + amount;
    }
    
    public int getResourceNum(ResourceType key) {
        return ownResoueces.getOrDefault(key, 0);
    }
    
    public int addResourceNum(ResourceType key, int add) {
        return ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
    }

}
