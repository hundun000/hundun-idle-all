package hundun.gdxgame.bugindustry.ui.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.GameEntity;
import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class GameImageDrawHelper implements IAmountChangeEventListener {
    
    private float COOKIE_WIDTH = 50;
    private float COOKIE_HEIGHT = 50;
    private int MAX_DRAW_BEE_NUM = 5;
    private float FLY_MIN_X;
    private float FLY_MAX_X;
    private float FLY_MIN_Y;
    private float FLY_MAX_Y;
    private float FLY_UNION_SPEED = 5;
    
    
    GameScreen parent;
    
    private Texture beeTexture;
    
    private Queue<GameEntity> beeEntities = new ConcurrentLinkedQueue<>();
    
    
    
    
    public GameImageDrawHelper(GameScreen parent, Camera camera) {
        this.parent = parent;
        this.beeTexture = new Texture(Gdx.files.internal("cookie.png"));
        FLY_MAX_X = Gdx.graphics.getWidth();
        FLY_MIN_X = 0;
        FLY_MAX_Y = Gdx.graphics.getHeight() - (StorageInfoBoard.BOARD_HEIGHT + StorageInfoBoard.BOARD_DISTANCE_TO_FRAME_TOP);
        FLY_MIN_Y = ConstructionControlBoard.BOARD_DISTANCE_TO_FRAME + ConstructionControlBoard.BOARD_HEIGHT;
        
        parent.game.getEventManager().registerListener(this);
    }
    
    
    public void drawAll() {
        parent.game.getBatch().begin();
        beeEntities.forEach(entity -> {
            checkRandomeMoveSpeedChange(entity);
            positionChange(entity);
            
            parent.game.getBatch().draw(beeTexture, entity.getX(), entity.getY(), COOKIE_WIDTH, COOKIE_HEIGHT);
            
        });
        parent.game.getBatch().end();
    }
    
    public void positionChange(GameEntity entity) {
        if (entity.isRandomMove()) {
            entity.setX(entity.getX() + entity.getSpeedX());
            entity.setY(entity.getY() + entity.getSpeedY());
        }
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
    
    public void addBeeEntity() {
        GameEntity entity = new GameEntity();
        entity.setX((FLY_MAX_X - FLY_MIN_X) / 2);
        entity.setY((FLY_MAX_Y - FLY_MIN_Y) / 2);
        entity.setRandomMove(true);
        entity.setRandomMoveCount(0);
        checkRandomeMoveSpeedChange(entity);
        beeEntities.add(entity);
    }
    
    @Override
    public void onResourceChange(boolean fromLoad) {
        int beeNum = parent.game.getModelContext().getStorageManager().getResourceNum(ResourceType.BEE);
        int drawBeeNum = (int) Math.min(MAX_DRAW_BEE_NUM, Math.ceil(Math.log(beeNum)));
        while (beeEntities.size() > drawBeeNum) {
            beeEntities.remove();
        }
        while (beeEntities.size() < drawBeeNum) {
            addBeeEntity();
        }
    }
    
}
