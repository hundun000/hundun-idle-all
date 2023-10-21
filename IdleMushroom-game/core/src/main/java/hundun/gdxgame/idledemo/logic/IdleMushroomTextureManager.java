package hundun.gdxgame.idledemo.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractTextureManager;
import lombok.Getter;

public class IdleMushroomTextureManager extends AbstractTextureManager {

    @Getter
    TextureRegion questionMarkTexture;

    @Override
    public void lazyInitOnGameCreateStage2() {
        {
            Texture texture = new Texture(Gdx.files.internal("All Icons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            questionMarkTexture = regions[0][13];
        }

        achievementMaskBoardTexture = new Texture(Gdx.files.internal("win.png"));
        menuTexture = new Texture(Gdx.files.internal("menu.png"));
        defaultBoardNinePatchTexture = new Texture(Gdx.files.internal("table.9.png"));
        defaultBoardNinePatchDrawable = new NinePatchDrawable(new NinePatch(
                defaultBoardNinePatchTexture,
                3, 3, 3, 3
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
            resourceIconMap.put(ResourceType.DNA_POINT, regions[0][1]);
            resourceIconMap.put(ResourceType.MUSHROOM, regions[0][2]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("resourceEntities.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
            resourceEntityMap.put(ResourceType.MUSHROOM, regions[0][1]);
        }
        {
/*            Texture texture = new Texture(Gdx.files.internal("constructionEntities.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
            //constructionEntityMap.put(ConstructionId.COOKIE_CLICK_PROVIDER, regions[0][0]);
            constructionEntityMap.put(DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, regions[0][1]);
            constructionEntityMap.put(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER, regions[0][3]);
            //constructionEntityMap.put(ConstructionPrototypeId.COOKIE_SELLER, regions[0][2]);
            //constructionEntityMap.put(ConstructionId.WIN_PROVIDER, regions[0][3]);*/
        }  
        {
            Texture texture = new Texture(Gdx.files.internal("gameAreaIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 100, 50);
            gameAreaLeftPartRegionMap.put(DemoScreenId.SCREEN_MAIN, regions[0][0]);
            gameAreaLeftPartRegionMap.put(DemoScreenId.SCREEN_WORLD, regions[1][0]);
            //gameAreaLeftPartRegionMap.put(GameArea.AREA_WIN, regions[2][0]);
            gameAreaRightPartRegionMap.put(DemoScreenId.SCREEN_MAIN, regions[0][1]);
            gameAreaRightPartRegionMap.put(DemoScreenId.SCREEN_WORLD, regions[1][1]);
            //gameAreaRightPartRegionMap.put(GameArea.AREA_WIN, regions[2][1]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(DemoScreenId.SCREEN_MAIN, regions[0][1]);
            gameAreaBackMap.put(DemoScreenId.SCREEN_WORLD, regions[0][2]);
            //gameAreaBackMap.put(GameArea.AREA_WIN, regions[0][3]);
        }
        {
            Texture texture = new Texture(Gdx.files.internal("cells.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 48);
            constructionHexHighLightImage1 = regions[0][0];
            constructionHexHighLightImage2 = regions[0][1];
            constructionHexImageMap.put(DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL, regions[0][2]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.EPOCH_1_TREE, regions[0][4]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, regions[0][5]);
            constructionHexImageMap.put(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER, regions[0][7]);
            //gameAreaBackMap.put(GameArea.AREA_WIN, regions[0][3]);
        }
    }

}
