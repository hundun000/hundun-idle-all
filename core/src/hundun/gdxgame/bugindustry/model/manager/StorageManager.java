package hundun.gdxgame.bugindustry.model.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class StorageManager {
    
    BugIndustryGame game;
    @Getter
    @Setter
    Map<ResourceType, Integer> ownResoueces = new HashMap<>();
    
    public StorageManager(BugIndustryGame game) {
        this.game = game;
    }
    
    public String getResourceDescription(ResourceType key) {
        int amount = getResourceNum(key);
        return key.getShowName() + ": " + amount;
    }
    
    public int getResourceNum(ResourceType key) {
        return ownResoueces.getOrDefault(key, 0);
    }
    
    /**
     * @param plus ture: plus the map; false: minus the map;
     */
    public void modifyAllResourceNum(Map<ResourceType, Integer> map, boolean plus) {
        Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (var entry : map.entrySet()) {
            ownResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
    }
    
    public void addResourceNum(ResourceType key, int add) {
        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
        game.getEventManager().notifyResourceAmountChange(false);
    }

}
