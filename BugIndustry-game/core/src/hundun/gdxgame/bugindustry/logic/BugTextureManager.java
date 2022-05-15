package hundun.gdxgame.bugindustry.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.idleshare.framework.model.manager.AbstractTextureManager;
import lombok.Getter;

public class BugTextureManager extends AbstractTextureManager {


    public BugTextureManager() {

        winTexture = new Texture(Gdx.files.internal("win.png"));
        menuTexture = new Texture(Gdx.files.internal("menu.png"));

        {
            Texture texture = new Texture(Gdx.files.internal("item.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            defaultIcon = regions[0][0];
            resourceIconMap.put(ResourceType.COIN, regions[0][1]);
            resourceIconMap.put(ResourceType.WOOD, regions[0][2]);
            resourceIconMap.put(ResourceType.WOOD_BOARD, regions[0][3]);
            resourceIconMap.put(ResourceType.BEE, regions[0][4]);
            resourceIconMap.put(ResourceType.HONEY, regions[0][5]);
            resourceIconMap.put(ResourceType.BEEWAX, regions[0][6]);
            resourceIconMap.put(ResourceType.QUEEN_BEE, regions[0][7]);
            //itemRegionMap.put(ResourceType.WIN_THE_GAME, regions[0][8]);
        }
        
        {
            Texture texture = new Texture(Gdx.files.internal("construction.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 32);
            constructionEntityMap.put(ConstructionId.SMALL_BEEHIVE, regions[0][0]);
            constructionEntityMap.put(ConstructionId.MID_BEEHIVE, regions[0][1]);
            constructionEntityMap.put(ConstructionId.BIG_BEEHIVE, regions[0][2]);
            constructionEntityMap.put(ConstructionId.QUEEN_BEEHIVE, regions[0][3]);
            constructionEntityMap.put(ConstructionId.WOOD_KEEPING, regions[0][4]);
        }
        
        {
            Texture texture = new Texture(Gdx.files.internal("selling.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 48, 32);
            constructionEntityMap.put(ConstructionId.WOOD_SELL_HOUSE, regions[0][0]);
            constructionEntityMap.put(ConstructionId.WOOD_BOARD_SELL_HOUSE, regions[0][1]);
            constructionEntityMap.put(ConstructionId.BEE_SELL_HOUSE, regions[0][2]);
            constructionEntityMap.put(ConstructionId.HONEY_SELL_HOUSE, regions[0][3]);
            constructionEntityMap.put(ConstructionId.BEEWAX_SELL_HOUSE, regions[0][4]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("gameAreaIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 100, 50);
            gameAreaLeftPartRegionMap.put(GameArea.BEE_FARM, regions[0][0]);
            gameAreaLeftPartRegionMap.put(GameArea.FOREST_FARM, regions[1][0]);
            gameAreaLeftPartRegionMap.put(GameArea.SHOP, regions[2][0]);
            gameAreaRightPartRegionMap.put(GameArea.BEE_FARM, regions[0][1]);
            gameAreaRightPartRegionMap.put(GameArea.FOREST_FARM, regions[1][1]);
            gameAreaRightPartRegionMap.put(GameArea.SHOP, regions[2][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(GameArea.BEE_FARM, regions[0][1]);
            gameAreaBackMap.put(GameArea.FOREST_FARM, regions[0][2]);
            gameAreaBackMap.put(GameArea.SHOP, regions[0][3]);
        }
        
    }

}
