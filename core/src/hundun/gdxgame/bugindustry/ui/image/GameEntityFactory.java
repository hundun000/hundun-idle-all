package hundun.gdxgame.bugindustry.ui.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.ConstructionId;
import hundun.gdxgame.bugindustry.ui.component.ConstructionControlBoard;
import hundun.gdxgame.bugindustry.ui.component.GameAreaControlBoard;
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
    public float FLY_UNION_SPEED = 3;

    
    ResourceType type;
    int baseX;
    int baseY;
    //TextureRegion textureRegion;
    
    //private Texture beeTexture;
    //private Texture beehiveTexture;
    
    BugIndustryGame game;
    
    public GameEntityFactory(BugIndustryGame game) {
        this.game = game;
        
        FLY_MAX_X = Gdx.graphics.getWidth() - GameAreaControlBoard.WIDTH;
        FLY_MIN_X = 0;
        FLY_MAX_Y = Gdx.graphics.getHeight() - (StorageInfoBoard.BOARD_HEIGHT + StorageInfoBoard.BOARD_DISTANCE_TO_FRAME_TOP);
        FLY_MIN_Y = FLY_MAX_Y - 200;
        
        
        //this.beehiveTexture = new Texture(Gdx.files.internal("beehive.png"));
    }
    
    public GameEntity newConstructionEntity(ConstructionId id, int index) {
        switch (id) {
            case SMALL_BEEHIVE:
                return newBeehiveEntity(index);
            default:
                break;
        }
        return newBeeEntity();
    }
    
    public GameEntity newBeehiveEntity(int index) {
        GameEntity entity = new GameEntity();
        entity.setTexture(new Sprite(game.getTextureManager().getConstructionTexture(ConstructionId.SMALL_BEEHIVE)));
        entity.setX(300 - 30 * index);
        entity.setY(300 - 30 * index);
        entity.setDrawWidth(64);
        entity.setDrawHeight(64);
        entity.setRandomMove(false);
        return entity;
    }
    
    
    public GameEntity newBeeEntity() {
        GameEntity entity = new GameEntity();
        entity.setTexture(new Sprite(game.getTextureManager().getBeeTexture()));
        entity.setX((FLY_MAX_X - FLY_MIN_X) / 2);
        entity.setY((FLY_MAX_Y - FLY_MIN_Y) / 2);
        entity.setDrawWidth(64);
        entity.setDrawHeight(64);
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
                    || (entity.getX() + entity.getDrawWidth() > FLY_MAX_X && entity.getSpeedX() > 0)) {
                entity.setSpeedX(entity.getSpeedX() * -1);
            }
            if ((entity.getY() < FLY_MIN_Y && entity.getSpeedY() < 0) 
                    || (entity.getY() + entity.getDrawHeight() > FLY_MAX_Y && entity.getSpeedY() > 0)) {
                entity.setSpeedY(entity.getSpeedY() * -1);
            }
            
            entity.setTextureFlipX(entity.getSpeedX() < 0);

        }
    }
    
    
}
