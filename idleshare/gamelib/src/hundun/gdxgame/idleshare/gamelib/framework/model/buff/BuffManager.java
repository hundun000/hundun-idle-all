package hundun.gdxgame.idleshare.gamelib.framework.model.buff;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import lombok.*;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {

    private final IdleGameplayContext gameContext;

    Map<String, BuffAndStatus> models = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BuffAndStatus {
        AbstractBuffPrototype buffPrototype;
        BuffSaveData saveData;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BuffSaveData {
        int buffLevel;

    }
    public BuffManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }

    public long modifyOutputGain(String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        for (BuffAndStatus model : models.values()) {
            if (model.getSaveData().getBuffLevel() == 0) {
                continue;
            }
            lastPhaseAmount = model.getBuffPrototype().modifyOutputGain(model.getSaveData(), constructionPrototypeId, resourceType, lastPhaseAmount);
        }
        return lastPhaseAmount;
    }

    public long modifyOutputCost(String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        for (BuffAndStatus model : models.values()) {
            if (model.getSaveData().getBuffLevel() == 0) {
                continue;
            }
            lastPhaseAmount = model.getBuffPrototype().modifyOutputCost(model.getSaveData(), constructionPrototypeId, resourceType, lastPhaseAmount);
        }
        return lastPhaseAmount;
    }

    public void subApplyGameplaySaveData(
            Map<String, AbstractBuffPrototype> achievementProviderMap,
            Map<String, BuffSaveData> statusDataMap
    ) {
        achievementProviderMap.values().forEach(it -> {
            BuffSaveData statusData;
            if (statusDataMap.containsKey(it.getId())) {
                statusData = statusDataMap.get(it.getId());
            } else {
                statusData = new BuffSaveData(0);
            }
            it.lazyInitDescription(gameContext);
            models.put(it.getId(), new BuffAndStatus(it, statusData));
        });
    }

    public Map<String, BuffSaveData> getBuffSaveDataMap() {
        return models.values().stream()
                .collect(Collectors.toMap(
                        it -> it.getBuffPrototype().getId(),
                        it -> it.getSaveData()
                ));
    }

    public BuffAndStatus getBuffAndStatus(String id) {
        return models.get(id);
    }

    public Map<String, Integer> getBuffLevelMap() {
        return models.values().stream()
                .collect(Collectors.toMap(
                        it -> it.getBuffPrototype().getId(),
                        it -> it.getSaveData().getBuffLevel()
                ));
    }

    public void modifyBuffLevel(Map<String, Integer> map) {
        //Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (Entry<String, Integer> entry : map.entrySet()) {
            BuffSaveData buffSaveData = models.get(entry.getKey()).getSaveData();
            buffSaveData.setBuffLevel(buffSaveData.getBuffLevel() + entry.getValue());
        }
        gameContext.getEventManager().notifyBuffChange(map);
    }
}
