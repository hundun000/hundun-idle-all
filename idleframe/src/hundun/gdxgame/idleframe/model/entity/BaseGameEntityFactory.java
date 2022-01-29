package hundun.gdxgame.idleframe.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/12/04
 */
public abstract class BaseGameEntityFactory {
    public abstract int calculateResourceDrawNum(String resourceId, long logicAmount);
    public abstract int calculateConstructionDrawNum(String constructionid, long logicAmount, int maxDrawNum);
    public abstract GameEntity newResourceEntity(String resourceId);
    public abstract GameEntity newConstructionEntity(String constructionid, int index);
    
    protected double DEFAULT_CONSTRUCTION_WIDTH_SCALE = 1.0;
    protected double DEFAULT_CONSTRUCTION_HEIGHT_SCALE = 1.0;
    protected double DEFAULT_RESOURCE_WIDTH_SCALE = 1.0;
    protected double DEFAULT_RESOURCE_HEIGHT_SCALE = 1.0;
    protected int COLUMN_STABLE_FIRST_COL_X = 80;
    protected int COLUMN_STABLE_COL_PADDING_X = 90;
    protected int COLUMN_STABLE_FIRST_COL_Y = 250;
    protected int COLUMN_STABLE_ROW_PADDING_X = 15;
    protected int COLUMN_STABLE_ROW_PADDING_Y = 30;
    
    protected PlayScreenLayoutConst layoutConst;
    protected BasePlayScreen<?> parent;
    
    public BaseGameEntityFactory(PlayScreenLayoutConst layoutConst, BasePlayScreen<?> parent) {
        this.layoutConst = layoutConst;
        this.parent = parent;
    }
    
    protected GameEntity failingResourcEntity(String resourceId, int MIN_X, int MAX_X, int START_Y, int REMOVE_Y, double BASE_FAILING_SPEED, double FAILING_SPEED_RANDOM_RANGE) {
        Sprite sprite = new Sprite(parent.game.getTextureManager().getResourceEntity(resourceId));
        MAX_X = (int) (MAX_X - DEFAULT_RESOURCE_WIDTH_SCALE * sprite.getRegionWidth());
        REMOVE_Y = (int) (REMOVE_Y + DEFAULT_RESOURCE_HEIGHT_SCALE * sprite.getRegionHeight());
        int randX = (int) (MIN_X + Math.random() * (MAX_X - MIN_X));
        double speed = BASE_FAILING_SPEED + Math.random() * FAILING_SPEED_RANDOM_RANGE;
        
        FailingEntity entity = new FailingEntity(REMOVE_Y);
        entity.setTexture(sprite);
        entity.setX(randX);
        entity.setY(START_Y);
        entity.setDrawWidth((int) (DEFAULT_RESOURCE_WIDTH_SCALE * entity.getTexture().getRegionWidth()));
        entity.setDrawHeight((int) (DEFAULT_RESOURCE_HEIGHT_SCALE * entity.getTexture().getRegionHeight()));
        entity.setMoveable(true);
        entity.setSpeedY((float) (-1.0 * speed));
        entity.checkMoveSpeedChange();
        return entity;
    }
    
    
    protected GameEntity randomMoveResourcEntity(String resourceId, int MIN_X, int MAX_X, int MIN_Y, int MAX_Y, double FLY_UNION_SPEED) {
        Sprite sprite = new Sprite(parent.game.getTextureManager().getResourceEntity(resourceId));
        MAX_X = (int) (MAX_X - DEFAULT_RESOURCE_WIDTH_SCALE * sprite.getRegionWidth());
        MAX_Y = (int) (MAX_Y - DEFAULT_RESOURCE_HEIGHT_SCALE * sprite.getRegionHeight());
        
        GameEntity entity = new RandomMoveEntity(MIN_X, MAX_X, MIN_Y, MAX_Y, FLY_UNION_SPEED);
        entity.setTexture(sprite);
        entity.setX((MAX_X - MIN_X) / 2);
        entity.setY((MAX_Y - MIN_Y) / 2);
        entity.setDrawWidth(entity.getTexture().getRegionWidth());
        entity.setDrawHeight(entity.getTexture().getRegionHeight());
        entity.setMoveable(true);
        entity.checkMoveSpeedChange();
        return entity;
    }
    
    protected GameEntity randomPositionStableConstructionEntity(String constructionId, int MIN_X, int MAX_X, int MIN_Y, int MAX_Y) {
        Sprite sprite = new Sprite(parent.game.getTextureManager().getConstructionEntity(constructionId));
        MAX_X = (int) (MAX_X - DEFAULT_CONSTRUCTION_WIDTH_SCALE * sprite.getRegionWidth());
        MAX_Y = (int) (MAX_Y - DEFAULT_CONSTRUCTION_HEIGHT_SCALE * sprite.getRegionHeight());
        int randX = (int) (MIN_X + Math.random() * (MAX_X - MIN_X));
        int randY = (int) (MIN_Y + Math.random() * (MAX_Y - MIN_Y));
        
        return stableAnyEntity(
                sprite,
                randX, 
                randY, 
                DEFAULT_CONSTRUCTION_WIDTH_SCALE, 
                DEFAULT_CONSTRUCTION_HEIGHT_SCALE, 
                constructionId);
    }
    
    protected GameEntity columnStableConstructionEntity(String constructionId, int index, int col) {
        int x = COLUMN_STABLE_FIRST_COL_X + col * COLUMN_STABLE_COL_PADDING_X + COLUMN_STABLE_ROW_PADDING_X * index;
        int y = COLUMN_STABLE_FIRST_COL_Y - COLUMN_STABLE_ROW_PADDING_Y * index;
        return stableAnyEntity(
                new Sprite(parent.game.getTextureManager().getConstructionEntity(constructionId)),
                x, 
                y, 
                DEFAULT_CONSTRUCTION_WIDTH_SCALE, 
                DEFAULT_CONSTRUCTION_HEIGHT_SCALE, 
                constructionId);
    }
    
    
    
    protected GameEntity stableAnyEntity(Sprite sprite, int x, int y, double WIDTH_SCALE, double HEIGHT_SCALE, String constructionId) {
        GameEntity entity = new GameEntity();
        entity.setTexture(sprite);
        entity.setX(x);
        entity.setY(y);
        entity.setDrawWidth((int) (WIDTH_SCALE * entity.getTexture().getRegionWidth()));
        entity.setDrawHeight((int) (HEIGHT_SCALE * entity.getTexture().getRegionHeight()));
        entity.setMoveable(false);
        return entity;
    }
    
}
