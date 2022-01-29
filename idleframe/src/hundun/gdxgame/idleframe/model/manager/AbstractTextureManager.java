package hundun.gdxgame.idleframe.model.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractTextureManager {
    
    @Getter
    protected Texture winTexture;
    
    @Getter
    protected Texture menuTexture;
    protected Map<String, TextureRegion> resourceIconMap = new HashMap<>();
    protected Map<String, TextureRegion> resourceEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaLeftPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaRightPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaBackMap = new HashMap<>();

    protected TextureRegion defaultIcon;
    protected TextureRegion defaultAreaBack;
    

    
    public TextureRegion getBackgroundTexture(String gameArea) {
        return gameAreaBackMap.getOrDefault(gameArea, defaultAreaBack);
    }
    
    public TextureRegion getResourceIcon(String resourceType) {
        return resourceIconMap.getOrDefault(resourceType, defaultIcon);
    }
    
    public TextureRegion getResourceEntity(String resourceType) {
        return resourceEntityMap.getOrDefault(resourceType, defaultIcon);
    }
    
    public TextureRegion getConstructionEntity(String constructionId) {
        return constructionEntityMap.getOrDefault(constructionId, defaultIcon);
    }
    
    public TextureRegion getGameAreaTexture(String key, boolean longVersion) {
        if (longVersion) {
            return gameAreaLeftPartRegionMap.getOrDefault(key, defaultIcon);
        } else {
            return gameAreaRightPartRegionMap.getOrDefault(key, defaultIcon);
        }
        
    }
}