package hundun.gdxgame.idledemo.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DemoTextureManager {

    @Getter
    protected Texture achievementMaskBoardTexture;

    @Getter
    protected Texture menuTexture;

    @Getter
    protected Texture defaultBoardNinePatchTexture;
    @Getter
    protected Drawable defaultBoardNinePatchDrawable;
    @Getter
    protected TextureRegion defaultBoardNinePatchMiddle;
    @Getter
    protected TextureRegion[][] mainClickAnimationTextureAtlas;
    @Getter
    protected int defaultBoardNinePatchEdgeSize;


    protected Map<String, TextureRegion> resourceIconMap = new HashMap<>();
    protected Map<String, TextureRegion> resourceEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaLeftPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaRightPartRegionMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaBackMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionHexImageMap = new HashMap<>();
    @Getter
    protected TextureRegion constructionHexHighLightImage1;
    @Getter
    protected TextureRegion constructionHexHighLightImage2;
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


    public TextureRegion getConstructionHexImage(String constructionId) {
        return constructionHexImageMap.getOrDefault(constructionId, defaultIcon);
    }

    public void lazyInitOnGameCreateStage2() {

        achievementMaskBoardTexture = new Texture(Gdx.files.internal("win.png"));
        menuTexture = new Texture(Gdx.files.internal("menu.png"));
        defaultBoardNinePatchTexture = new Texture(Gdx.files.internal("defaultBoardNinePatch.png"));
        defaultBoardNinePatchDrawable = new NinePatchDrawable(new NinePatch(
                defaultBoardNinePatchTexture,
                (defaultBoardNinePatchTexture.getWidth() - 1) / 2,
                (defaultBoardNinePatchTexture.getWidth() - 1) / 2,
                (defaultBoardNinePatchTexture.getHeight() - 1) / 2,
                (defaultBoardNinePatchTexture.getHeight() - 1) / 2
        ));
        {
            Texture texture = new Texture(Gdx.files.internal("mainCokie.png"));
             mainClickAnimationTextureAtlas = TextureRegion.split(texture, 256, 256);
        }
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
            constructionEntityMap.put(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER, regions[0][1]);
            constructionEntityMap.put(DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER, regions[0][3]);
            //constructionEntityMap.put(ConstructionPrototypeId.COOKIE_SELLER, regions[0][2]);
            //constructionEntityMap.put(ConstructionId.WIN_PROVIDER, regions[0][3]);
        }  
        {
            Texture texture = new Texture(Gdx.files.internal("gameAreaIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 100, 50);
            gameAreaLeftPartRegionMap.put(DemoScreenId.SCREEN_COOKIE, regions[0][0]);
            gameAreaLeftPartRegionMap.put(DemoScreenId.SCREEN_WORLD, regions[1][0]);
            //gameAreaLeftPartRegionMap.put(GameArea.AREA_WIN, regions[2][0]);
            gameAreaRightPartRegionMap.put(DemoScreenId.SCREEN_COOKIE, regions[0][1]);
            gameAreaRightPartRegionMap.put(DemoScreenId.SCREEN_WORLD, regions[1][1]);
            //gameAreaRightPartRegionMap.put(GameArea.AREA_WIN, regions[2][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(DemoScreenId.SCREEN_COOKIE, regions[0][1]);
            gameAreaBackMap.put(DemoScreenId.SCREEN_WORLD, regions[0][2]);
            //gameAreaBackMap.put(GameArea.AREA_WIN, regions[0][3]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("fantasyhextiles_v3.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 48);
            defaultAreaBack = regions[0][1];
            constructionHexImageMap.put(DemoConstructionPrototypeId.EMPTY_CELL, regions[0][0]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER, regions[0][1]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER, regions[0][2]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER, regions[0][3]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER, regions[1][2]);
            //gameAreaBackMap.put(GameArea.AREA_WIN, regions[0][3]);
        }
    }

}
