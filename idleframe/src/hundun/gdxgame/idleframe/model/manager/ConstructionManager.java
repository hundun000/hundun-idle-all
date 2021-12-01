package hundun.gdxgame.idleframe.model.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.ModelContext;
import hundun.gdxgame.idleframe.model.construction.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/29
 */
public class ConstructionManager {
    
    BaseIdleGame game;
    
    
    public ConstructionManager(BaseIdleGame game, Map<String, List<String>> areaShownConstructionIds) {
        this.game = game;
        
        init(game.getModelContext(), areaShownConstructionIds);
    }
    
    
    /**
     * 运行中的设施集合
     */
    Set<BaseConstruction> runningConstructionModels = new HashSet<>();
//    /**
//     * 后台运行。即不显示在当前screen，但也需要结算逻辑帧的Construction集合。
//     */
//    Set<BaseConstruction> backgroundConstructionModels = new HashSet<>();
    
    /**
     * 根据GameArea显示不同的Construction集合
     */
    Map<String, List<BaseConstruction>> areaShownConstructions; 
    
    private void init(ModelContext modelContext, Map<String, List<String>> areaShownConstructionIds) {
        areaShownConstructions = new HashMap<>();
        if (areaShownConstructionIds != null) {
            for (var entry : areaShownConstructionIds.entrySet()) {
                areaShownConstructions.put(
                        entry.getKey(), 
                        entry.getValue()
                            .stream()
                            .map(id -> modelContext.getConstructionFactory().getConstruction(id))
                            .collect(Collectors.toList())
                        );
            }
        }
        
//        areaShownConstructions.put(String.BEE_FARM, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEE_GATHER_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.SMALL_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.MID_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BIG_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.QUEEN_BEEHIVE)
//                ));
//        areaShownConstructions.put(String.FOREST_FARM, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_GATHER_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_KEEPING),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_BOARD_MAKER),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WIN_THE_GAME)
//                ));
//        areaShownConstructions.put(String.SHOP, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_BOARD_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEE_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.HONEY_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEEWAX_SELL_HOUSE)
//                ));
        areaShownConstructions.values().forEach(item -> runningConstructionModels.addAll(item));
    }

    public void logicFrameForAllConstructionModels() {
        runningConstructionModels.forEach(item -> item.onLogicFrame());
    }
    
    public List<BaseConstruction> getAreaShownConstructions(String gameArea) {
        return areaShownConstructions.get(gameArea);
    }
    
}
