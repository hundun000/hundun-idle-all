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
    private float MAX_X;
    private float MAX_Y;
    
    GameScreen parent;
    
    private Texture beeTexture;
    
    private Queue<GameEntity> beeEntities = new ConcurrentLinkedQueue<>();
    
    
    
    
    public GameImageDrawHelper(GameScreen parent, Camera camera) {
        this.parent = parent;
        this.beeTexture = new Texture(Gdx.files.internal("cookie.png"));
        this.MAX_X = camera.viewportWidth;
        this.MAX_Y = camera.viewportHeight;
        
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
                entity.setRandomMoveCount(100);
                double angle = Math.toRadians(Math.random() * 360);
                double unionSpeed = 5;
                entity.setSpeedX((float) (unionSpeed * Math.cos(angle)));
                entity.setSpeedY((float) (unionSpeed * Math.sin(angle)));
            }
            
            if (entity.getX() < 0 || entity.getX() > MAX_X) {
                entity.setSpeedX(entity.getSpeedX() * -1);
            }
            if (entity.getY() < 0 || entity.getY() > MAX_Y) {
                entity.setSpeedY(entity.getSpeedY() * -1);
            }
        }
    }
    
    public void addBeeEntity() {
        GameEntity entity = new GameEntity();
        entity.setX(50);
        entity.setY(50);
        entity.setRandomMove(true);
        entity.setRandomMoveCount(0);
        checkRandomeMoveSpeedChange(entity);
        beeEntities.add(entity);
    }
    
    @Override
    public void onResourceChange() {
        int beeNum = parent.game.getModelContext().getStorageManager().getResourceNum(ResourceType.WORKER_BEE);
        int drawBeeNum = (int) Math.min(MAX_DRAW_BEE_NUM, Math.ceil(Math.log(beeNum)));
        while (beeEntities.size() > drawBeeNum) {
            beeEntities.remove();
        }
        while (beeEntities.size() < drawBeeNum) {
            addBeeEntity();
        }
    }
    
}
