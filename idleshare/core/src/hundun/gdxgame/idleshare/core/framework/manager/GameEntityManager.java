package hundun.gdxgame.idleshare.core.framework.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hundun.gdxgame.idleshare.core.framework.GameImageDrawer.IGameImageDrawerHolder;
import hundun.gdxgame.idleshare.core.framework.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.entity.GameEntity;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.AreaEntityEffectConfig;
import lombok.Getter;

/**
 * 管理GameEntity在内存中的数量，不负责绘制。
 * @author hundun
 * Created on 2021/12/04
 */
public class GameEntityManager {

    IGameImageDrawerHolder holder;
    @Getter
    private Map<String, List<GameEntity>> gameEntitiesOfConstructionPrototypeIds = new HashMap<String, List<GameEntity>>();
    @Getter
    private Map<String, List<GameEntity>> gameEntitiesOfResourceIds = new HashMap<>();
    @Getter
    private Map<String, AreaEntityEffectConfig> areaEntityEffectConfigMap;


    public GameEntityManager(IGameImageDrawerHolder holder) {
        super();
        this.holder = holder;
    }

    public void allEntityMoveForFrame() {
        for (Entry<String, List<GameEntity>> entry : gameEntitiesOfConstructionPrototypeIds.entrySet()) {
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
                return remove;
                });
            queue.forEach(entity -> {
                entity.frameLogic();
                positionChange(entity);
            });

        }
    }

    public void areaEntityCheckByOwnAmount(String gameArea, BaseGameEntityFactory gameEntityFactory) {
        List<String> shownConstructionIds = this.areaEntityEffectConfigMap.get(gameArea).getOwnAmountConstructionPrototypeIds();
        if (shownConstructionIds != null) {
            for (String shownConstructionId : shownConstructionIds) {
                checkConstructionEntityByOwnAmount(shownConstructionId, gameEntityFactory);
            }
        }

        List<String> shownResourceIds = this.areaEntityEffectConfigMap.get(gameArea).getOwnAmountResourceIds();
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                checkResourceEntityByOwnAmount(resourceId, gameEntityFactory);
            }
        }
    }

    public void areaEntityCheckByChangeAmount(String gameArea, BaseGameEntityFactory gameEntityFactory, Map<String, Long> changeMap) {

        List<String> shownResourceIds = this.areaEntityEffectConfigMap.get(gameArea).getChangeAmountResourceIds();
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
        long resourceNum = holder.getResourceNumOrZero(resourceId);
        int drawNum = gameEntityFactory.calculateResourceDrawNum(resourceId, resourceNum);

        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        while (gameEntities.size() > drawNum) {
            //Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " remove, current = " + gameEntities.size() + " , target = " + drawNum);
            gameEntities.remove(gameEntities.size() - 1);
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, newIndex);
            if (gameEntity != null) {
                //Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " new, current = " + gameEntities.size() + " , target = " + drawNum);
                gameEntities.add(gameEntity);
            } else {
                break;
            }
        }
    }

    private void addResourceEntityByChangeAmount(String resourceId, BaseGameEntityFactory gameEntityFactory, int addAmount) {
        int drawNum = gameEntityFactory.calculateResourceDrawNum(resourceId, addAmount);

        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        for (int i = 0; i < drawNum; i++) {
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, i);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                //Gdx.app.log(this.getClass().getSimpleName(), "addResourceEntityByChangeAmount " + resourceId + " new, target = " + drawNum);
            } else {
                break;
            }
        }
    }

    private void checkConstructionEntityByOwnAmount(String prototypeId, BaseGameEntityFactory gameEntityFactory) {
        int constructionWorkingLevelNum = holder.getConstructionWorkingLevelNumOrZero(prototypeId);
        int MAX_DRAW_NUM = 5;
        int drawNum = gameEntityFactory.calculateConstructionDrawNum(prototypeId, constructionWorkingLevelNum, MAX_DRAW_NUM);
        gameEntitiesOfConstructionPrototypeIds.computeIfAbsent(prototypeId, k -> new LinkedList<>());
        List<GameEntity> gameEntities = gameEntitiesOfConstructionPrototypeIds.get(prototypeId);
        while (gameEntities.size() > drawNum) {
            //Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + prototypeId + " remove, current = " + gameEntities.size() + " , target = " + drawNum);
            gameEntities.remove(gameEntities.size() - 1);
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newConstructionEntity(prototypeId, newIndex);
            if (gameEntity != null) {
                //Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + prototypeId + " new, current = " + gameEntities.size() + " , target = " + drawNum);
                gameEntities.add(gameEntity);
            } else {
                //Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " , cannot create new entity.");
                break;
            }
        }
    }

    public void lazyInit(Map<String, AreaEntityEffectConfig> areaEntityEffectConfigMap) {
        this.areaEntityEffectConfigMap = areaEntityEffectConfigMap;
    }

    /**
     * 某些引擎（如Unity）不光要在此处queue.clear()，还要queue.ForEach(entity => UnityEngine.Object.Destroy(entity.gameObject));
     */
    public void destroyNoNeedDrawConstructionIds(List<String> needDrawConstructionIds) {
        for (Entry<String, List<GameEntity>> entry : gameEntitiesOfConstructionPrototypeIds.entrySet()) {
            List<GameEntity> queue = entry.getValue();
            if (!needDrawConstructionIds.contains(entry.getKey())) {
                queue.clear();
            }
        }
    }




}
