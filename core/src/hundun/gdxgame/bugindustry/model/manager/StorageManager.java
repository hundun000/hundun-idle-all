package hundun.gdxgame.bugindustry.model.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    @Getter
    @Setter
    Set<ResourceType> unlockedResourceTypes = new HashSet<>();
    
    private Map<ResourceType, TextureRegion> textureRegionMap = new HashMap<>();
    TextureRegion defaultIcon;
    
    public StorageManager(BugIndustryGame game) {
        this.game = game;
        
        Texture texture = new Texture(Gdx.files.internal("resourceIcons.png"));
        TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
        defaultIcon = regions[0][0];
        textureRegionMap.put(ResourceType.COIN, regions[0][1]);
        
    }
    
    public String getResourceDescription(ResourceType key) {
        int amount = getResourceNum(key);
        return key.getShowName() + ": " + amount;
    }
    
    public int getResourceNum(ResourceType key) {
        return ownResoueces.getOrDefault(key, 0);
    }
    
    public TextureRegion getResourceIcon(ResourceType key) {
        return textureRegionMap.getOrDefault(key, defaultIcon);
    }
    
    /**
     * @param plus ture: plus the map; false: minus the map;
     */
    public void modifyAllResourceNum(Map<ResourceType, Integer> map, boolean plus) {
        Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (var entry : map.entrySet()) {
            unlockedResourceTypes.add(entry.getKey());
            ownResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
    }
    
//    public void addResourceNum(ResourceType key, int add) {
//        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
//        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
//        game.getEventManager().notifyResourceAmountChange(false);
//    }

}
