package hundun.gdxgame.idleframe.model.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.Setter;

public abstract class TextureManager {
    
    @Getter
    protected Texture winTexture;
    
    @Getter
    protected Texture menuTexture;
    protected Map<String, TextureRegion> itemRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaLeftPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaRightPartRegionMap = new HashMap<>();
    protected Map<String, Texture> gameAreaBackMap = new HashMap<>();

    protected TextureRegion defaultIcon;

    

    
    public Texture getBackgroundTexture(String gameArea) {
        return gameAreaBackMap.getOrDefault(gameArea, defaultIcon.getTexture());
    }
    
    public TextureRegion getResourceIcon(String resourceType) {
        return itemRegionMap.getOrDefault(resourceType, defaultIcon);
    }
    
    public TextureRegion getConstructionTexture(String constructionId) {
        return constructionRegionMap.getOrDefault(constructionId, defaultIcon);
    }
    
    public TextureRegion getGameAreaTexture(String key, boolean longVersion) {
        if (longVersion) {
            return gameAreaLeftPartRegionMap.getOrDefault(key, defaultIcon);
        } else {
            return gameAreaRightPartRegionMap.getOrDefault(key, defaultIcon);
        }
        
    }
}
