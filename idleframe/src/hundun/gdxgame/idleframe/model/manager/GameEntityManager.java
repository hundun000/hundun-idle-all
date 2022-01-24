package hundun.gdxgame.idleframe.model.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/04
 */
public class GameEntityManager {
    
    BaseIdleGame game;
    
    @Getter
    private Map<String, Queue<GameEntity>> gameEntitiesOfConstructionIds = new ConcurrentHashMap<>();
    @Getter
    private Map<String, Queue<GameEntity>> gameEntitiesOfResourceIds = new ConcurrentHashMap<>();
    @Getter
    private Map<String, List<String>> areaShowEntityConstructionIds;
    @Getter
    private Map<String, List<String>> areaShowEntityResourceIds;
    
    public GameEntityManager(BaseIdleGame game) {
        super();
        this.game = game;
    }
    
    public void allEntityMoveForFrame() {
        for (var entry : gameEntitiesOfConstructionIds.entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                entity.checkRandomeMoveSpeedChange();
                positionChange(entity);
            });
        }
        
        for (var entry : gameEntitiesOfResourceIds.entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                entity.checkRandomeMoveSpeedChange();
                positionChange(entity);
            });

        }
    }
    
    public void areaEntityCheckAmount(String gameArea, BaseGameEntityFactory gameEntityFactory) {
        List<String> shownConstructionIds = this.areaShowEntityConstructionIds.get(gameArea);
        if (shownConstructionIds != null) {
            for (String shownConstructionId : shownConstructionIds) {
                checkConstructionEntityList(shownConstructionId, gameEntityFactory);
            }
        }
        
        
        List<String> shownResourceIds = this.areaShowEntityResourceIds.get(gameArea);
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                checkResourceEntityList(resourceId, gameEntityFactory);
            }
        }
    }
    
    private void positionChange(GameEntity entity) {
        if (entity.isRandomMove()) {
            entity.setX(entity.getX() + entity.getSpeedX());
            entity.setY(entity.getY() + entity.getSpeedY());
        }
    }
    
    private void checkResourceEntityList(String resourceId, BaseGameEntityFactory gameEntityFactory) {
        long resourceNum = game.getModelContext().getStorageManager().getResourceNumOrZero(resourceId);
        int drawNum = gameEntityFactory.calculateResourceDrawNum(resourceId, resourceNum);
        
        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new ConcurrentLinkedQueue<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + resourceId + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId);
            gameEntities.add(gameEntity);
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + resourceId + " new for " + gameEntities.size() + " -> " + drawNum);
        }
    }
    
    private void checkConstructionEntityList(String id, BaseGameEntityFactory gameEntityFactory) {
        BaseConstruction construction = game.getModelContext().getConstructionFactory().getConstruction(id);
        int resourceNum = construction.getSaveData().getWorkingLevel();
        int MAX_DRAW_NUM = construction.getMAX_DRAW_NUM();
        int drawNum = gameEntityFactory.calculateConstructionDrawNum(id, resourceNum, MAX_DRAW_NUM);
        gameEntitiesOfConstructionIds.computeIfAbsent(id, k -> new ConcurrentLinkedQueue<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfConstructionIds.get(id);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + id + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newConstructionEntity(id, newIndex);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + id + " new for " + gameEntities.size() + " -> " + drawNum);
            } else {
                Gdx.app.log(this.getClass().getSimpleName(), "checkEntityList " + id + " , cannot create new entity.");
                break;
            }
        }
    }

    public void lazyInit(Map<String, List<String>> areaShowEntityConstructionIds,
            Map<String, List<String>> areaShowEntityResourceIds) {
        this.areaShowEntityConstructionIds = areaShowEntityConstructionIds;
        this.areaShowEntityResourceIds = areaShowEntityResourceIds;
    }


    

}
