package hundun.gdxgame.idledemo.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.idleframe.model.manager.AbstractTextureManager;
import lombok.Getter;

public class TextureManager extends AbstractTextureManager {

    public TextureManager() {

        winTexture = new Texture(Gdx.files.internal("win.png"));
        menuTexture = new Texture(Gdx.files.internal("menu.png"));

        {
            Texture texture = new Texture(Gdx.files.internal("item.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            defaultIcon = regions[0][0];
            itemRegionMap.put(ResourceType.COIN, regions[0][1]);
            itemRegionMap.put(ResourceType.COOKIE, regions[0][2]);
            itemRegionMap.put(ResourceType.WIN_TROPHY, regions[0][3]);
        }
        
        {
            Texture texture = new Texture(Gdx.files.internal("construction.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 32);
            constructionRegionMap.put(ConstructionId.COOKIE_CLICK_PROVIDER, regions[0][0]);
            constructionRegionMap.put(ConstructionId.COOKIE_AUTO_PROVIDER, regions[0][1]);
            constructionRegionMap.put(ConstructionId.COOKIE_SELLER, regions[0][2]);
            constructionRegionMap.put(ConstructionId.WIN_PROVIDER, regions[0][3]);
        }  
        {
            Texture texture = new Texture(Gdx.files.internal("gameAreaIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 100, 50);
            gameAreaLeftPartRegionMap.put(GameArea.AREA_0, regions[0][0]);
            gameAreaLeftPartRegionMap.put(GameArea.AREA_1, regions[1][0]);
            gameAreaRightPartRegionMap.put(GameArea.AREA_0, regions[0][1]);
            gameAreaRightPartRegionMap.put(GameArea.AREA_1, regions[1][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(GameArea.AREA_0, regions[0][1]);
            gameAreaBackMap.put(GameArea.AREA_1, regions[0][2]);
        }
        
    }

}
