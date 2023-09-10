package hundun.gdxgame.idleshare.core.framework.model.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import lombok.Getter;

public abstract class AbstractTextureManager {

    @Getter
    protected Texture achievementMaskBoardTexture;

    @Getter
    protected Texture menuTexture;

    @Getter
    protected Texture defaultBoardNinePatchTexture;

    @Getter
    protected TextureRegion defaultBoardNinePatchMiddle;

    @Getter
    protected int defaultBoardNinePatchEdgeSize;


    protected Map<String, TextureRegion> resourceIconMap = new HashMap<>();
    protected Map<String, TextureRegion> resourceEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaLeftPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaRightPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaBackMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionHexImageMap = new HashMap<>();
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

    public abstract void lazyInitOnGameCreateStage2();

    public TextureRegion getConstructionHexImage(String constructionId) {
        return constructionHexImageMap.getOrDefault(constructionId, defaultIcon);
    }
}
