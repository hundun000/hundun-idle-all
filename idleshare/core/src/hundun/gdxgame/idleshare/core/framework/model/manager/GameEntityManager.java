package hundun.gdxgame.idleshare.core.framework.model.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.model.entity.GameEntity;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

/**
 * 管理GameEntity在内存中的数量，不负责绘制。
 * @author hundun
 * Created on 2021/12/04
 */
public class GameEntityManager {

    private BaseIdleGame<?> game;
    @Getter
    private Map<String, List<GameEntity>> gameEntitiesOfConstructionIds = new HashMap<>();
    @Getter
    private Map<String, List<GameEntity>> gameEntitiesOfResourceIds = new HashMap<>();
    @Getter
    private Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds;
    @Getter
    private Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    @Getter
    private Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;


    public GameEntityManager(BaseIdleGame<?> game) {
        super();
        this.game = game;
    }

    public void allEntityMoveForFrame() {
        for (Entry<String, List<GameEntity>> entry : gameEntitiesOfConstructionIds.entrySet()) {
            List<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                entity.frameLogic();
                positionChange(entity);
            });
        }

        for (Entry<String, List<GameEntity>> entry : gameEntitiesOfResourceIds.entrySet()) {
            List<GameEntity> queue = entry.getValue();
            queue.removeIf(entity -> {
                boolean remove = entity.checkRemove();
                if (remove) {
                    Gdx.app.log(this.getClass().getSimpleName(), "entity removed by self check");
                }
                return remove;
                });
            queue.forEach(entity -> {
                entity.frameLogic();
                positionChange(entity);
            });

        }
    }

    public void areaEntityCheckByOwnAmount(String gameArea, BaseGameEntityFactory gameEntityFactory) {
        List<String> shownConstructionIds = this.areaShowEntityByOwnAmountConstructionIds.get(gameArea);
        if (shownConstructionIds != null) {
            for (String shownConstructionId : shownConstructionIds) {
                checkConstructionEntityByOwnAmount(shownConstructionId, gameEntityFactory);
            }
        }

        List<String> shownResourceIds = this.areaShowEntityByOwnAmountResourceIds.get(gameArea);
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                checkResourceEntityByOwnAmount(resourceId, gameEntityFactory);
            }
        }
    }

    public void areaEntityCheckByChangeAmount(String gameArea, BaseGameEntityFactory gameEntityFactory, Map<String, Long> changeMap) {

        List<String> shownResourceIds = this.areaShowEntityByChangeAmountResourceIds.get(gameArea);
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                if (changeMap.getOrDefault(resourceId, 0L) > 0) {
                    addResourceEntityByChangeAmount(resourceId, gameEntityFactory, changeMap.get(resourceId).intValue());
                }
            }
        }
    }

    private void positionChange(GameEntity entity) {
        if (entity.isMoveable()) {
            entity.setX(entity.getX() + entity.getSpeedX());
            entity.setY(entity.getY() + entity.getSpeedY());
        }
    }

    private void checkResourceEntityByOwnAmount(String resourceId, BaseGameEntityFactory gameEntityFactory) {
        long resourceNum = game.getIdleGameplayExport().getResourceNumOrZero(resourceId);
        int drawNum = gameEntityFactory.calculateResourceDrawNum(resourceId, resourceNum);

        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " remove, current = " + gameEntities.size() + " , target = " + drawNum);
            gameEntities.remove(gameEntities.size() - 1);
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, newIndex);
            if (gameEntity != null) {
                Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " new, current = " + gameEntities.size() + " , target = " + drawNum);
                gameEntities.add(gameEntity);
            } else {
                break;
            }
        }
    }

    private void addResourceEntityByChangeAmount(String resourceId, BaseGameEntityFactory gameEntityFactory, int addAmount) {
        int drawNum = addAmount;

        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        for (int i = 0; i < drawNum; i++) {
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, i);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "addResourceEntityByChangeAmount " + resourceId + " new, target = " + drawNum);
            } else {
                break;
            }
        }
    }

    private void checkConstructionEntityByOwnAmount(String id, BaseGameEntityFactory gameEntityFactory) {
        BaseConstruction construction = game.getIdleGameplayExport().getConstruction(id);
        int resourceNum = construction.getSaveData().getWorkingLevel();
        int MAX_DRAW_NUM = construction.getMaxDrawNum();
        int drawNum = gameEntityFactory.calculateConstructionDrawNum(id, resourceNum, MAX_DRAW_NUM);
        gameEntitiesOfConstructionIds.computeIfAbsent(id, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfConstructionIds.get(id);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " remove, current = " + gameEntities.size() + " , target = " + drawNum);
            gameEntities.remove(gameEntities.size() - 1);
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newConstructionEntity(id, newIndex);
            if (gameEntity != null) {
                Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " new, current = " + gameEntities.size() + " , target = " + drawNum);
                gameEntities.add(gameEntity);
            } else {
                //Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " , cannot create new entity.");
                break;
            }
        }
    }

    public void lazyInit(Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds,
            Map<String, List<String>> areaShowEntityByOwnAmountResourceIds,
            Map<String, List<String>> areaShowEntityByChangeAmountResourceIds) {
        this.areaShowEntityByOwnAmountConstructionIds = areaShowEntityByOwnAmountConstructionIds;
        this.areaShowEntityByOwnAmountResourceIds = areaShowEntityByOwnAmountResourceIds;
        this.areaShowEntityByChangeAmountResourceIds = areaShowEntityByChangeAmountResourceIds;
    }




}
