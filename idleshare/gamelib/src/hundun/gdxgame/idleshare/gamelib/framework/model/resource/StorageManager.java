package hundun.gdxgame.idleshare.gamelib.framework.model.resource;

import java.util.*;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneFrameResourceChangeEvent;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneFrameResourceChangeEventOneTagData;
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
    Map<String, Long> ownResources = new HashMap<>();

    @Getter
    @Setter
    Set<String> unlockedResourceTypes = new HashSet<>();


    Map<ModifyResourceTag, Map<String, Long>> oneFrameDeltaResourceMap = new HashMap<>();

    private final Map<ModifyResourceTag, Map<String, List<Long>>> historyChangeMap = new HashMap<>();
    public StorageManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
        for (ModifyResourceTag tag : ModifyResourceTag.values()) {
            oneFrameDeltaResourceMap.put(tag, new HashMap<>());
            historyChangeMap.put(tag, new HashMap<>());
        }
    }

    public String getResourceDescription(String key) {
        long amount = getResourceNumOrZero(key);
        return key + ": " + amount;
    }

    public long getResourceNumOrZero(String key) {
        return ownResources.getOrDefault(key, 0L);
    }




    public void modifyAllResourceNum(List<ResourcePair> packs, boolean plus) {
        modifyAllResourceNum(packs, plus, ModifyResourceTag.OTHER);
    }

    public enum ModifyResourceTag {
        OUTPUT,
        OTHER
    }

    public void modifyAllResourceNum(List<ResourcePair> packs, boolean plus, ModifyResourceTag tag) {
        gameContext.getFrontend().log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + " " + tag + ": " + packs);
        for (ResourcePair pack : packs) {
            unlockedResourceTypes.add(pack.getType());
            ownResources.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
            oneFrameDeltaResourceMap.get(tag).merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
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
        final int latestSecondSize = gameContext.getIdleFrontend().getLogicFramePerSecond();
        Map<ModifyResourceTag, OneFrameResourceChangeEventOneTagData> tagDataMap = oneFrameDeltaResourceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        oneTagEntry -> oneTagEntry.getKey(),
                        oneTagEntry -> {
                            Map<String, Long> changeMap = new HashMap<>(oneTagEntry.getValue());
                            oneTagEntry.getValue().clear();
                            Map<String, List<Long>> thisTagHistoryChangeMap = historyChangeMap.get(oneTagEntry.getKey());
                            changeMap.keySet().forEach(resourceType -> thisTagHistoryChangeMap.computeIfAbsent(resourceType, it -> new ArrayList<>()));
                            thisTagHistoryChangeMap.forEach((resourceType, value) -> {
                                value.add(changeMap.getOrDefault(resourceType, 0L));
                                while (value.size() > latestSecondSize) {
                                    value.remove(0);
                                }
                            });
                            Map<String, Long> latestSecondChangeMap = thisTagHistoryChangeMap.entrySet().stream()
                                    .collect(Collectors.toMap(
                                            it -> it.getKey(),
                                            it -> it.getValue().stream().mapToLong(itt -> itt).sum()
                                    ));
                            return OneFrameResourceChangeEventOneTagData.builder()
                                    .frameChangeMap(changeMap)
                                    .latestSecondChangeMap(latestSecondChangeMap)
                                    .build();
                        }
                ));

        Map<String, Long> totalFrameChangeMap = new HashMap<>();
        Map<String, Long> totalLatestSecondChangeMap = new HashMap<>();
        tagDataMap.values().forEach(oneTagValue -> {
            oneTagValue.getFrameChangeMap().forEach((k, v) -> {
                totalFrameChangeMap.merge(k, v, (o, n) -> o + n);
            });
            oneTagValue.getLatestSecondChangeMap().forEach((k, v) -> {
                totalLatestSecondChangeMap.merge(k, v, (o, n) -> o + n);
            });
        });

        OneFrameResourceChangeEvent event = OneFrameResourceChangeEvent.builder()
                .allTagData(OneFrameResourceChangeEventOneTagData.builder()
                        .frameChangeMap(totalFrameChangeMap)
                        .latestSecondChangeMap(totalLatestSecondChangeMap)
                        .build())
                .tagDataMap(tagDataMap)
                .build();
        gameContext.getEventManager().notifyOneFrameResourceChange(event);
    }

}
