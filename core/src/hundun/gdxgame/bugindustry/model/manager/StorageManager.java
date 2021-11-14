package hundun.gdxgame.bugindustry.model.manager;

import java.util.HashMap;
import java.util.Map;

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
    
    public void addResourceNum(ResourceType key, int add) {
        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
        game.getEventManager().notifyResourceAmountChange();
    }

}
