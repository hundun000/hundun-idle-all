package hundun.gdxgame.idleshare.gamelib.framework.model.construction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.ConstructionBuyCandidateConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.ConstructionConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNodeMap;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.TileNodeUtils;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/29
 */
public class ConstructionManager implements ITileNodeMap<Void> {

    IdleGameplayContext gameContext;
    /**
     * 运行中的设施集合。key: constructionId
     */
    public Map<String, BaseConstruction> runningConstructionModelMap = new HashMap<>();

    private final List<BaseConstruction> removeQueue = new ArrayList<>();
    private final List<BaseConstruction> createQueue = new ArrayList<>();
    private final List<BaseConstruction> loadPostQueue = new ArrayList<>();
    @Getter
    ConstructionConfig constructionConfig;

    public ConstructionManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }



    public void onSubLogicFrame() {
        createQueue.forEach(it -> {
            runningConstructionModelMap.put(it.getId(), it);
            gameContext.getIdleFrontend().postConstructionCreate(it);
            TileNodeUtils.updateNeighborsAllStep(it, this);
        });

        loadPostQueue.forEach(it -> {
            gameContext.getIdleFrontend().postConstructionCreate(it);
        });

        removeQueue.forEach(it -> {
            runningConstructionModelMap.remove(it.getId());
            TileNodeUtils.updateNeighborsAllStep(it, this);
        });

        if (removeQueue.size() > 0 || createQueue.size() > 0)
        {
            this.gameContext.getEventManager().notifyConstructionCollectionChange();
        }
        removeQueue.clear();
        createQueue.clear();
        loadPostQueue.clear();

        runningConstructionModelMap.values().forEach(item -> item.onSubLogicFrame());
    }

    public List<BaseConstruction> getSingletonConstructionInstancesOrEmpty()
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> constructionConfig.getSingletonPrototypeIds().contains(it.getPrototypeId()))
                .collect(Collectors.toList());
    }

    public BaseConstruction getConstruction(String id)
    {
        BaseConstruction result = runningConstructionModelMap.get(id);
        if (result == null)
        {
            throw new RuntimeException("getConstruction " + id + " not found");
        }
        return result;
    }

    public BaseConstruction getWorldConstructionAt(GridPosition target)
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> constructionConfig.getWorldPrototypeIds().contains(it.getPrototypeId()))
                .filter(it -> it.getSaveData().getPosition().equals(target))
                .findAny()
                .orElse(null);
    }

    public List<BaseConstruction> getWorldConstructionInstances()
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> constructionConfig.getWorldPrototypeIds().contains(it.getPrototypeId()))
                .collect(Collectors.toList());
    }

    public List<BaseConstruction> getAllConstructionInstances()
    {
        return runningConstructionModelMap.values().stream()
                .collect(Collectors.toList());
    }

    public List<BaseConstruction> getConstructionsOfPrototype(String prototypeId)
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> it.getPrototypeId().equals(prototypeId))
                .collect(Collectors.toList());
    }






    private void addToRemoveQueueAt(GridPosition position)
    {
        BaseConstruction target = getWorldConstructionAt(position);
        if (target != null) {
            removeQueue.add(target);
        }
    }


    public void addToRemoveQueue(BaseConstruction construction)
    {
        removeQueue.add(construction);
    }

    public void loadInstance(ConstructionSaveData saveData)
    {
        String prototypeId = saveData.prototypeId;
        GridPosition position = saveData.getPosition();
        BaseConstruction construction = gameContext.getConstructionFactory().getInstanceOfPrototype(prototypeId, position);
        construction.setSaveData(saveData);
        construction.updateModifiedValues();

        runningConstructionModelMap.put(construction.getId(), construction);
        TileNodeUtils.updateNeighborsAllStep(construction, this);
        loadPostQueue.add(construction);
    }

    public boolean canBuyInstanceOfPrototype(ConstructionBuyCandidateConfig config, GridPosition position)
    {
        List<BaseConstruction> worldConstructionInstances = gameContext
                .getConstructionManager()
                .getWorldConstructionInstances();
        boolean isCostEnough = gameContext.getStorageManager().isEnough(config.getBuyCostPack().getModifiedValues());
        boolean positionAllowCase1 = worldConstructionInstances.stream().noneMatch(it -> it.getPosition().equals(position));
        boolean positionAllowCase2 = worldConstructionInstances.stream().anyMatch(it -> it.getPosition().equals(position) && it.isAllowPositionOverwrite());
        return isCostEnough && (positionAllowCase1 || positionAllowCase2);

    }



    public void buyInstanceOfPrototype(ConstructionBuyCandidateConfig config, GridPosition position)
    {
        this.gameContext.getStorageManager().modifyAllResourceNum(config.getBuyCostPack().getModifiedValues(), false);
        addToRemoveQueueAt(position);
        addToCreateQueue(config.getPrototypeId(), position);
    }

    public void addToCreateQueue(String prototypeId, GridPosition position)
    {
        BaseConstruction construction = gameContext.getConstructionFactory().getInstanceOfPrototype(prototypeId, position);
        createQueue.add(construction);
    }


    @Override
    public ITileNode<Void> getValidNodeOrNull(GridPosition position) {
        return getWorldConstructionAt(position);
    }

    public void lazyInit(ConstructionConfig constructionConfig) {
        this.constructionConfig = constructionConfig;
    }

    public BaseConstruction getMainClickConstructionInstances() {
        return runningConstructionModelMap.values().stream()
                .filter(it -> it.getPrototypeId().equals(constructionConfig.getMainClickerConstructionPrototypeId()))
                .findFirst()
                .orElse(null);
    }
}
