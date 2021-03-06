package hundun.gdxgame.threecookie.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.idleshare.framework.model.manager.AbstractTextureManager;

public class TextureManager extends AbstractTextureManager {

    public TextureManager() {

        winTexture = new Texture(Gdx.files.internal("win.png"));
        menuTexture = new Texture(Gdx.files.internal("menu.png"));
        defaultBoardNinePatchTexture = new Texture(Gdx.files.internal("defaultBoardNinePatch.png"));
        defaultBoardNinePatchEdgeSize = 4;
        defaultBoardNinePatchMiddle = new TextureRegion(
                defaultBoardNinePatchTexture, 
                defaultBoardNinePatchEdgeSize, 
                defaultBoardNinePatchEdgeSize, 
                defaultBoardNinePatchTexture.getWidth() - defaultBoardNinePatchEdgeSize * 2, 
                defaultBoardNinePatchTexture.getHeight() - defaultBoardNinePatchEdgeSize * 2
                );
        {
            Texture texture = new Texture(Gdx.files.internal("resourceIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            defaultIcon = regions[0][0];
            resourceIconMap.put(ResourceType.COIN, regions[0][1]);
            resourceIconMap.put(ResourceType.COOKIE, regions[0][2]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("resourceEntities.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
            resourceEntityMap.put(ResourceType.COOKIE, regions[0][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("constructionEntities.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
            //constructionEntityMap.put(ConstructionId.COOKIE_CLICK_PROVIDER, regions[0][0]);
            constructionEntityMap.put(ConstructionId.COOKIE_AUTO_PROVIDER, regions[0][1]);
            constructionEntityMap.put(ConstructionId.COOKIE_SELLER, regions[0][2]);
            //constructionEntityMap.put(ConstructionId.WIN_PROVIDER, regions[0][3]);
        }  
        {
            Texture texture = new Texture(Gdx.files.internal("gameAreaIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 100, 50);
            gameAreaLeftPartRegionMap.put(GameArea.AREA_COOKIE, regions[0][0]);
            gameAreaLeftPartRegionMap.put(GameArea.AREA_BUILDING, regions[1][0]);
            gameAreaLeftPartRegionMap.put(GameArea.AREA_WIN, regions[2][0]);
            gameAreaRightPartRegionMap.put(GameArea.AREA_COOKIE, regions[0][1]);
            gameAreaRightPartRegionMap.put(GameArea.AREA_BUILDING, regions[1][1]);
            gameAreaRightPartRegionMap.put(GameArea.AREA_WIN, regions[2][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(GameArea.AREA_COOKIE, regions[0][1]);
            gameAreaBackMap.put(GameArea.AREA_BUILDING, regions[0][2]);
            gameAreaBackMap.put(GameArea.AREA_WIN, regions[0][3]);
        }
        
    }

}
