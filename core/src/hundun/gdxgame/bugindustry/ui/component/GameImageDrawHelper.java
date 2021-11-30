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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.model.construction.ConstructionId;
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
    
    private Map<ConstructionId, Queue<GameEntity>> gameEntitiesOfTypes = new ConcurrentHashMap<>();
    Queue<GameEntity> beeQueue = new ConcurrentLinkedQueue<>();
    
    public GameImageDrawHelper(GameScreen parent, Camera camera) {
        this.parent = parent;
        
        this.gameEntityFactory = new GameEntityFactory(parent.game);
        parent.game.getEventManager().registerListener(this);
    }
    
    
    public void drawAll() {
        parent.game.getBatch().begin();
        
        
        var shownConstructions = parent.game.getModelContext().getConstructionManager().getAreaShownConstructions(parent.getArea());
        for (BaseConstruction shownConstruction : shownConstructions) {
            if (gameEntitiesOfTypes.containsKey(shownConstruction.getId())) {
                Queue<GameEntity> queue = gameEntitiesOfTypes.get(shownConstruction.getId());
                //Gdx.app.log(this.getClass().getSimpleName(), "drawAll for " + type + " size = " + queue.size());
                queue.forEach(entity -> {
                    gameEntityFactory.checkRandomeMoveSpeedChange(entity);
                    positionChange(entity);
                    
                    parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                    
                });
            }
        }

        if (parent.getArea() == GameArea.BEE_FARM) {
            beeQueue.forEach(entity -> {
                gameEntityFactory.checkRandomeMoveSpeedChange(entity);
                positionChange(entity);
                
                parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                
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
        GameArea gameArea = parent.getArea();
        List<BaseConstruction> shownConstructions = parent.game.getModelContext().getConstructionManager().getAreaShownConstructions(gameArea);
        if (parent.getArea() == GameArea.BEE_FARM) {
            checkBeeEntityList();
        }
        for (BaseConstruction shownConstruction : shownConstructions) {
            checkEntityList(shownConstruction.getId());
        }
    }
    
    private void checkBeeEntityList() {
        ResourceType type = ResourceType.BEE;
        long resourceNum = parent.game.getModelContext().getStorageManager().getResourceNum(type);
        int drawNum = (int) Math.min(MAX_DRAW_BEE_NUM, resourceNum);
        
        Queue<GameEntity> gameEntities = beeQueue;
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newBeeEntity();
            gameEntities.add(gameEntity);
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " new for " + gameEntities.size() + " -> " + drawNum);
        }
    }
    
    private void checkEntityList(ConstructionId type) {
        BaseConstruction construction = parent.game.getModelContext().getConstructionFactory().getConstruction(type);
        int resourceNum = construction.getSaveData().getWorkingLevel();
        int drawNum = (int) Math.min(construction.getMAX_DRAW_NUM(), resourceNum);
        gameEntitiesOfTypes.computeIfAbsent(type, k -> new ConcurrentLinkedQueue<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfTypes.get(type);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newConstructionEntity(type, newIndex);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " new for " + gameEntities.size() + " -> " + drawNum);
            } else {
                Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + type + " , cannot create new entity.");
                break;
            }
        }
    }
    

    

    
}
