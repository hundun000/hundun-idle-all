package hundun.gdxgame.bugindustry.ui.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;
import hundun.gdxgame.bugindustry.ui.image.GameEntity;
import hundun.gdxgame.bugindustry.ui.image.GameEntityFactory;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class GameImageDrawHelper implements IAmountChangeEventListener {
    

    private int MAX_DRAW_BEE_NUM = 5;

    
    
    GameScreen parent;
    GameEntityFactory gameEntityFactory;
    
    
    
    //private Queue<GameEntity> beeEntities = new ConcurrentLinkedQueue<>();
    
    private Map<ResourceType, Queue<GameEntity>> gameEntitiesOfTypes = new ConcurrentHashMap<>();
    
    
    public GameImageDrawHelper(GameScreen parent, Camera camera) {
        this.parent = parent;
        
        this.gameEntityFactory = new GameEntityFactory();
        parent.game.getEventManager().registerListener(this);
    }
    
    
    public void drawAll() {
        parent.game.getBatch().begin();
        
        for (var entry : gameEntitiesOfTypes.entrySet()) {
            ResourceType type = entry.getKey();
            Queue<GameEntity> queue = entry.getValue();
            //Gdx.app.log(this.getClass().getSimpleName(), "drawAll for " + type + " size = " + queue.size());
            queue.forEach(entity -> {
                gameEntityFactory.checkRandomeMoveSpeedChange(entity);
                positionChange(entity);
                
                parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), entity.getDrawWidth(), entity.getDrawHeight());
                
            });
        }
        
        
        parent.game.getBatch().end();
    }
    
    public void positionChange(GameEntity entity) {
        if (entity.isRandomMove()) {
            entity.setX(entity.getX() + entity.getSpeedX());
            entity.setY(entity.getY() + entity.getSpeedY());
        }
    }
    

    

    
    @Override
    public void onResourceChange(boolean fromLoad) {
        checkEntityList(ResourceType.BEE);
    }
    
    private void checkEntityList(ResourceType type) {
        int resourceNum = parent.game.getModelContext().getStorageManager().getResourceNum(type);
        int drawBeeNum = (int) Math.min(MAX_DRAW_BEE_NUM, resourceNum > 0 ? Math.ceil(Math.log(resourceNum)) : 0);
        gameEntitiesOfTypes.computeIfAbsent(type, k -> new ConcurrentLinkedQueue<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfTypes.get(type);
        while (gameEntities.size() > drawBeeNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " remove for " + gameEntities.size() + " -> " + drawBeeNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawBeeNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newEntity(type, newIndex);
            gameEntities.add(gameEntity);
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " new for " + gameEntities.size() + " -> " + drawBeeNum);
        }
    }
    

    
}
