package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idleshare.gamelib.context.IdleGamePlayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class StorageManager implements ILogicFrameListener {

    IdleGamePlayContext gameContext;

    @Getter
    @Setter
    Map<String, Long> ownResoueces = new HashMap<>();

    @Getter
    @Setter
    Set<String> unlockedResourceTypes = new HashSet<>();


    Map<String, Long> oneFrameDeltaResoueces = new HashMap<>();

    public StorageManager(IdleGamePlayContext gameContext) {
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


    @Override
    public void onLogicFrame() {
        // ------ frameDeltaAmountClear ------
        Map<String, Long> temp = new HashMap<>(oneFrameDeltaResoueces);
        oneFrameDeltaResoueces.clear();
        //Gdx.app.log(this.getClass().getSimpleName(), "frameDeltaAmountClear: " + temp);
        gameContext.getEventManager().notifyOneFrameResourceChange(temp);
    }

//    public void addResourceNum(String key, int add) {
//        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
//        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
//        game.getEventManager().notifyResourceAmountChange(false);
//    }

}
