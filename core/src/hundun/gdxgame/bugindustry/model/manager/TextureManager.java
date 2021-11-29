package hundun.gdxgame.bugindustry.model.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.ConstructionId;
import lombok.Getter;

public class TextureManager {
    private Texture mainAreaBackgroundTexture;
    private Texture shopAreaBackgroundTexture;
    private Map<ResourceType, TextureRegion> itemRegionMap = new HashMap<>();
    private Map<ConstructionId, TextureRegion> constructionRegionMap = new HashMap<>();
    @Getter
    private Texture beeTexture;
    TextureRegion defaultIcon;
    
    public TextureManager() {
        mainAreaBackgroundTexture = new Texture(Gdx.files.internal("640x480.png"));
        shopAreaBackgroundTexture = new Texture(Gdx.files.internal("shop-area.png"));
        this.beeTexture = new Texture(Gdx.files.internal("bee.png"));
        {
            Texture texture = new Texture(Gdx.files.internal("item.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            defaultIcon = regions[0][0];
            itemRegionMap.put(ResourceType.COIN, regions[0][1]);
            itemRegionMap.put(ResourceType.WOOD, regions[0][2]);
            itemRegionMap.put(ResourceType.HARD_WOOD, regions[0][3]);
            itemRegionMap.put(ResourceType.BEE, regions[0][4]);
            itemRegionMap.put(ResourceType.HONEY, regions[0][5]);
            itemRegionMap.put(ResourceType.BEEWAX, regions[0][6]);
            itemRegionMap.put(ResourceType.QUEEN_BEE, regions[0][7]);
            itemRegionMap.put(ResourceType.WIN_THE_GAME, regions[0][8]);
        }
        
        {
            Texture texture = new Texture(Gdx.files.internal("construction.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 32);
            constructionRegionMap.put(ConstructionId.SMALL_BEEHIVE, regions[0][0]);
            constructionRegionMap.put(ConstructionId.MID_BEEHIVE, regions[0][1]);
            constructionRegionMap.put(ConstructionId.BIG_BEEHIVE, regions[0][2]);
            constructionRegionMap.put(ConstructionId.KING_BEEHIVE, regions[0][3]);
            constructionRegionMap.put(ConstructionId.WOOD_AUTO_CUTTER, regions[0][4]);
        }
        
    }
    
    public Texture getBackgroundTexture(GameArea gameArea) {
        switch (gameArea) {
            case SHOP:
                return shopAreaBackgroundTexture;
            default:
                return mainAreaBackgroundTexture;
        }
    }
    
    public TextureRegion getResourceIcon(ResourceType key) {
        return itemRegionMap.getOrDefault(key, defaultIcon);
    }
    
    public TextureRegion getConstructionTexture(ConstructionId key) {
        return constructionRegionMap.getOrDefault(key, defaultIcon);
    }
}
