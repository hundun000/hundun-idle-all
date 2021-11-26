package hundun.gdxgame.bugindustry.ui.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.component.ConstructionControlBoard;
import hundun.gdxgame.bugindustry.ui.component.StorageInfoBoard;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory {
    public float FLY_MIN_X;
    public float FLY_MAX_X;
    public float FLY_MIN_Y;
    public float FLY_MAX_Y;
    public float FLY_UNION_SPEED = 5;
    public float COOKIE_WIDTH = 50;
    public float COOKIE_HEIGHT = 50;
    
    ResourceType type;
    int baseX;
    int baseY;
    TextureRegion textureRegion;
    
    public GameEntityFactory() {
        FLY_MAX_X = Gdx.graphics.getWidth();
        FLY_MIN_X = 0;
        FLY_MAX_Y = Gdx.graphics.getHeight() - (StorageInfoBoard.BOARD_HEIGHT + StorageInfoBoard.BOARD_DISTANCE_TO_FRAME_TOP);
        FLY_MIN_Y = ConstructionControlBoard.BOARD_DISTANCE_TO_FRAME + ConstructionControlBoard.BOARD_HEIGHT;
        
    }
    
    public GameEntity newEntity(ResourceType type2) {
        // FIXME
        return addBeeEntity();
    }
    
    
    public GameEntity addBeeEntity() {
        GameEntity entity = new GameEntity();
        entity.setX((FLY_MAX_X - FLY_MIN_X) / 2);
        entity.setY((FLY_MAX_Y - FLY_MIN_Y) / 2);
        entity.setRandomMove(true);
        entity.setRandomMoveCount(0);
        checkRandomeMoveSpeedChange(entity);
        return entity;
    }
    
    public void checkRandomeMoveSpeedChange(GameEntity entity) {
        if (entity.isRandomMove()) {
            if (entity.getRandomMoveCount() > 0) {
                entity.setRandomMoveCount(entity.getRandomMoveCount() - 1);
            } else {
                entity.setRandomMoveCount(50 + (int) (Math.random() * 50));
                double angle = Math.toRadians(Math.random() * 360);
                double unionSpeed = FLY_UNION_SPEED;
                entity.setSpeedX((float) (unionSpeed * Math.cos(angle)));
                entity.setSpeedY((float) (unionSpeed * Math.sin(angle)));
            }
            
            if ((entity.getX() < FLY_MIN_X && entity.getSpeedX() < 0) 
                    || (entity.getX() + COOKIE_WIDTH > FLY_MAX_X && entity.getSpeedX() > 0)) {
                entity.setSpeedX(entity.getSpeedX() * -1);
            }
            if ((entity.getY() < FLY_MIN_Y && entity.getSpeedY() < 0) 
                    || (entity.getY() + COOKIE_HEIGHT > FLY_MAX_Y && entity.getSpeedY() > 0)) {
                entity.setSpeedY(entity.getSpeedY() * -1);
            }
        }
    }
    
    
}
