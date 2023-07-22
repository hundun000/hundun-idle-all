package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNodeMap;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.TileNodeUtils;

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

    private List<BaseConstruction> removeQueue = new ArrayList<>();
    private List<BaseConstruction> createQueue = new ArrayList<>();

    public ConstructionManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * 根据GameArea显示不同的ConstructionVM集合
     */
    Map<String, List<String>> areaControlableConstructionVMPrototypeIds;

    /**
     * 根据GameArea显示不同的ConstructionPrototypeVM集合
     */
    Map<String, List<String>> areaControlableConstructionPrototypeVMPrototypeIds;

    public void lazyInit(
            Map<String, List<String>> areaControlableConstructionVMPrototypeIds,
            Map<String, List<String>> areaControlableConstructionPrototypeVMPrototypeIds
    ) {
        this.areaControlableConstructionVMPrototypeIds = areaControlableConstructionVMPrototypeIds;
        this.areaControlableConstructionPrototypeVMPrototypeIds = areaControlableConstructionPrototypeVMPrototypeIds;

    }

    public void onSubLogicFrame() {
        createQueue.forEach(it -> {
            runningConstructionModelMap.put(it.getId(), it);
            TileNodeUtils.updateNeighborsAllStep(it, this);
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
        
        runningConstructionModelMap.values().forEach(item -> item.onSubLogicFrame());
    }

    public List<BaseConstruction> getAreaControlableConstructionsOrEmpty(String gameArea)
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> areaControlableConstructionVMPrototypeIds.containsKey(gameArea)
                        && areaControlableConstructionVMPrototypeIds.get(gameArea).contains(it.getPrototypeId()))
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

    public BaseConstruction getConstructionAt(GridPosition target)
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> it.getSaveData().getPosition().equals(target))
                .findAny()
                .orElse(null);
    }

    public List<BaseConstruction> getConstructions()
    {
        return runningConstructionModelMap.values().stream().collect(Collectors.toList());
    }

    public List<BaseConstruction> getConstructionsOfPrototype(String prototypeId)
    {
        return runningConstructionModelMap.values().stream()
                .filter(it -> it.getPrototypeId().equals(prototypeId))
                .collect(Collectors.toList());
    }






    private void addToRemoveQueueAt(GridPosition position)
    {
        runningConstructionModelMap.entrySet().stream()
            .filter(pair -> pair.getValue().getPosition().equals(position))
            .forEach(pair -> removeQueue.add(pair.getValue()));

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
    }

    public boolean canBuyInstanceOfPrototype(String prototypeId, GridPosition position)
    {
        AbstractConstructionPrototype prototype = gameContext.getConstructionFactory().getPrototype(prototypeId);
        boolean isCostEnough = this.gameContext.getStorageManager().isEnough(prototype.getBuyInstanceCostPack().getModifiedValues());
        boolean positionAllowCase1 = runningConstructionModelMap.entrySet().stream().noneMatch(pair -> pair.getValue().getPosition().equals(position));
        boolean positionAllowCase2 = runningConstructionModelMap.entrySet().stream().filter(pair -> pair.getValue().getPosition().equals(position) && pair.getValue().isAllowPositionOverwrite()).count() == 1;
        return isCostEnough && (positionAllowCase1 || positionAllowCase2);

    }



    public void buyInstanceOfPrototype(String prototypeId, GridPosition position)
    {
        AbstractConstructionPrototype prototype = gameContext.getConstructionFactory().getPrototype(prototypeId);
        this.gameContext.getStorageManager().modifyAllResourceNum(prototype.getBuyInstanceCostPack().getModifiedValues(), false);
        addToRemoveQueueAt(position);
        addToCreateQueue(prototypeId, position);
    }

    public void addToCreateQueue(String prototypeId, GridPosition position)
    {
        BaseConstruction construction = gameContext.getConstructionFactory().getInstanceOfPrototype(prototypeId, position);
        createQueue.add(construction);
    }



    public void AddToRemoveQueue(BaseConstruction construction)
    {
        throw new RuntimeException("NotImplementedException");
    }


    @Override
    public ITileNode<Void> getValidNodeOrNull(GridPosition position) {
        return getConstructionAt(position);
    }
}
