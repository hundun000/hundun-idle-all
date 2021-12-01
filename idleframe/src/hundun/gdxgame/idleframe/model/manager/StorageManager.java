package hundun.gdxgame.idleframe.model.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class StorageManager {
    
    BaseIdleGame game;
    @Getter
    @Setter
    Map<String, Long> ownResoueces = new HashMap<>();
    @Getter
    @Setter
    Set<String> unlockedResourceTypes = new HashSet<>();
    
    
    
    public StorageManager(BaseIdleGame game) {
        this.game = game;
        

    }
    
    public String getResourceDescription(String key) {
        long amount = getResourceNumOrZero(key);
        return key + ": " + amount;
    }
    
    public long getResourceNumOrZero(String key) {
        return ownResoueces.getOrDefault(key, 0L);
    }
    

    
    /**
     * @param plus ture: plus the map; false: minus the map;
     */
    public void modifyAllResourceNum(Map<String, Long> map, boolean plus) {
        Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (var entry : map.entrySet()) {
            unlockedResourceTypes.add(entry.getKey());
            ownResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
        game.getEventManager().notifyResourceAmountChange(false);
    }
    
    public void modifyAllResourceNum(List<ResourcePair> packs, boolean plus) {
        Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + packs);
        for (var pack : packs) {
            unlockedResourceTypes.add(pack.getType());
            ownResoueces.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
        }
        game.getEventManager().notifyResourceAmountChange(false);
    }
    
    public boolean isEnough(List<ResourcePair> pairs) {
        for (ResourcePair pair : pairs) {
            long own = this.getResourceNumOrZero(pair.getType());
            if (own < pair.getAmount()) {
                return false;
            }
        }
        return true;
    }
    
//    public void addResourceNum(String key, int add) {
//        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
//        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
//        game.getEventManager().notifyResourceAmountChange(false);
//    }

}
