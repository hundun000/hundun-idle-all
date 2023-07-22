package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.*;
import java.util.Map.Entry;

import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class StorageManager {

    IdleGameplayContext gameContext;

    @Getter
    @Setter
    Map<String, Long> ownResoueces = new HashMap<>();

    @Getter
    @Setter
    Set<String> unlockedResourceTypes = new HashSet<>();


    Map<String, Long> oneFrameDeltaResoueces = new HashMap<>();

    private Map<String, List<Long>> deltaHistoryMap = new HashMap<>();
    private final int HISTORY_SIZE = 100;
    public StorageManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }

    public String getResourceDescription(String key) {
        long amount = getResourceNumOrZero(key);
        return key + ": " + amount;
    }

    public long getResourceNumOrZero(String key) {
        return ownResoueces.getOrDefault(key, 0L);
    }



    /**
     * @param plus ture: plus the map; false: minus the map;
     */
    public void modifyAllResourceNum(Map<String, Long> map, boolean plus) {
        //Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (Entry<String, Long> entry : map.entrySet()) {
            unlockedResourceTypes.add(entry.getKey());
            ownResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
            oneFrameDeltaResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
        //game.getEventManager().notifyResourceAmountChange(false);
    }

    public void modifyAllResourceNum(List<ResourcePair> packs, boolean plus) {
        //Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + packs);
        for (ResourcePair pack : packs) {
            unlockedResourceTypes.add(pack.getType());
            ownResoueces.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
            oneFrameDeltaResoueces.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
        }
        //game.getEventManager().notifyResourceAmountChange(false);
    }

    public boolean isEnough(List<ResourcePair> pairs) {
        for (ResourcePair pair : pairs) {
            long own = this.getResourceNumOrZero(pair.getType());
            if (own < pair.getAmount()) {
                return false;
            }
        }
        return true;
    }


    public void onSubLogicFrame() {
        // ------ frameDeltaAmountClear ------
        Map<String, Long> changeMap = new HashMap<>(oneFrameDeltaResoueces);
        oneFrameDeltaResoueces.clear();
        changeMap.keySet().forEach(resourceType -> deltaHistoryMap.computeIfAbsent(resourceType, it -> new ArrayList<>()));
        deltaHistoryMap.forEach((resourceType, value) -> {
            value.add(changeMap.getOrDefault(resourceType, 0L));
            while (value.size() > HISTORY_SIZE) {
                value.remove(0);
            }
        });
        gameContext.getEventManager().notifyOneFrameResourceChange(changeMap, deltaHistoryMap);
    }

//    public void addResourceNum(String key, int add) {
//        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
//        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
//        game.getEventManager().notifyResourceAmountChange(false);
//    }

}
