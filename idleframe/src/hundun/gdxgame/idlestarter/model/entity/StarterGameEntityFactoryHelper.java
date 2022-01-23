package hundun.gdxgame.idlestarter.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.entity.RandomMoveEntity;
import hundun.gdxgame.idleframe.model.manager.TextureManager;

/**
 * @author hundun
 * Created on 2022/01/24
 */
public class StarterGameEntityFactoryHelper {
    
    double DEFAULT_WIDTH_SCALE = 1.0;
    double DEFAULT_HEIGHT_SCALE = 1.0;
    
    int COLUMN_STABLE_FIRST_COL_X = 80;
    int COLUMN_STABLE_COL_PADDING_X = 90;
    int COLUMN_STABLE_FIRST_COL_Y = 250;
    int COLUMN_STABLE_ROW_PADDING_X = 15;
    int COLUMN_STABLE_ROW_PADDING_Y = 30;
    
    final TextureManager texeTextureManager;
    
    public StarterGameEntityFactoryHelper(TextureManager texeTextureManager) {
        this.texeTextureManager = texeTextureManager;
    }
    
    public GameEntity randomMove(String resourceId, int MIN_X, int MAX_X, int MIN_Y, int MAX_Y, double FLY_UNION_SPEED) {
        Sprite sprite = new Sprite(texeTextureManager.getResourceIcon(resourceId));
        MAX_X = (int) (MAX_X - DEFAULT_WIDTH_SCALE * sprite.getRegionWidth());
        MAX_Y = (int) (MAX_Y - DEFAULT_HEIGHT_SCALE * sprite.getRegionHeight());
        
        GameEntity entity = new RandomMoveEntity(MIN_X, MAX_X, MIN_Y, MAX_Y, FLY_UNION_SPEED);
        entity.setTexture(sprite);
        entity.setX((MAX_X - MIN_X) / 2);
        entity.setY((MAX_Y - MIN_Y) / 2);
        entity.setDrawWidth(entity.getTexture().getRegionWidth());
        entity.setDrawHeight(entity.getTexture().getRegionHeight());
        entity.setRandomMove(true);
        entity.setRandomMoveCount(0);
        entity.checkRandomeMoveSpeedChange();
        return entity;
    }
    
    public GameEntity randomPositionStableEntity(String constructionId, int MIN_X, int MAX_X, int MIN_Y, int MAX_Y) {
        Sprite sprite = new Sprite(texeTextureManager.getConstructionTexture(constructionId));
        MAX_X = (int) (MAX_X - DEFAULT_WIDTH_SCALE * sprite.getRegionWidth());
        MAX_Y = (int) (MAX_Y - DEFAULT_HEIGHT_SCALE * sprite.getRegionHeight());
        int randX = (int) (MIN_X + Math.random() * (MAX_X - MIN_X));
        int randY = (int) (MIN_Y + Math.random() * (MAX_Y - MIN_Y));
        
        return stableEntity(
                sprite,
                randX, 
                randY, 
                DEFAULT_WIDTH_SCALE, 
                DEFAULT_HEIGHT_SCALE, 
                constructionId);
    }
    
    public GameEntity columnStableEntity(String constructionId, int index, int col) {
        int x = COLUMN_STABLE_FIRST_COL_X + col * COLUMN_STABLE_COL_PADDING_X + COLUMN_STABLE_ROW_PADDING_X * index;
        int y = COLUMN_STABLE_FIRST_COL_Y - COLUMN_STABLE_ROW_PADDING_Y * index;
        return stableEntity(
                new Sprite(texeTextureManager.getConstructionTexture(constructionId)),
                x, 
                y, 
                DEFAULT_WIDTH_SCALE, 
                DEFAULT_HEIGHT_SCALE, 
                constructionId);
    }
    
    
    
    private GameEntity stableEntity(Sprite sprite, int x, int y, double WIDTH_SCALE, double HEIGHT_SCALE, String constructionId) {
        GameEntity entity = new GameEntity();
        entity.setTexture(sprite);
        entity.setX(x);
        entity.setY(y);
        entity.setDrawWidth((int) (WIDTH_SCALE * entity.getTexture().getRegionWidth()));
        entity.setDrawHeight((int) (HEIGHT_SCALE * entity.getTexture().getRegionHeight()));
        entity.setRandomMove(false);
        return entity;
    }
}
