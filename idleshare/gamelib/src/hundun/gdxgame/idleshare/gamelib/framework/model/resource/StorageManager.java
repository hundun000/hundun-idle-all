package hundun.gdxgame.idleshare.gamelib.framework.model.resource;

import java.util.*;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEvent;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEventOneTagData;
import lombok.*;

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


    //Map<ModifyResourceTag, Map<String, Long>> oneFrameDeltaResourceMap = new HashMap<>();
    final int historySecondSize;
    Map<ModifyResourceTag, Map<String, OneSecondHistoryData>> oneSecondDeltaResourceMap = new HashMap<>();
    private final Map<ModifyResourceTag, Map<String, List<OneSecondHistoryData>>> historyChangeMap = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OneSecondHistoryData {
        int second;
        long sum;
    }


    public StorageManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
        for (ModifyResourceTag tag : ModifyResourceTag.values()) {
            //oneFrameDeltaResourceMap.put(tag, new HashMap<>());
            oneSecondDeltaResourceMap.put(tag, new HashMap<>());
            historyChangeMap.put(tag, new HashMap<>());
        }
        this.historySecondSize = 10;
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
        //gameContext.getFrontend().log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + " " + tag + ": " + packs);
        for (ResourcePair pack : packs) {
            unlockedResourceTypes.add(pack.getType());
            ownResources.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
            //oneFrameDeltaResourceMap.get(tag).merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
            OneSecondHistoryData thisSecondHistoryData = oneSecondDeltaResourceMap.get(tag).get(pack.getType());
            if (thisSecondHistoryData == null) {
                thisSecondHistoryData = OneSecondHistoryData.builder().sum(0).build();
                oneSecondDeltaResourceMap.get(tag).put(pack.getType(), thisSecondHistoryData);
            }
            thisSecondHistoryData.setSum(thisSecondHistoryData.getSum() + (plus ? 1 : -1 ) * pack.getAmount());
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

    public Map<String, Long> getSecondChangeMap(ModifyResourceTag tag, int secondBeforeCurrent) {
        final int targetSecond = gameContext.getCurrentIntSecond() - secondBeforeCurrent;
        Map<String, List<OneSecondHistoryData>> thisTagHistoryChangeMap = historyChangeMap.get(tag);
        Map<String, Long> secondChangeMap = thisTagHistoryChangeMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> {
                            List<OneSecondHistoryData> allSecondHistoryDataList = entry.getValue();
                            long targetSum = entry.getValue().stream()
                                    .filter(it -> it.second == targetSecond)
                                    .map(it -> it.sum)
                                    .findFirst()
                                    .orElse(0L);
                            return targetSum;
                        }
                ));
        secondChangeMap.entrySet().removeIf(it -> it.getValue() == 0);
        return secondChangeMap;
    }



    public void onSubLogicFrame() {

        if (!gameContext.getIdleFrontend().modLogicFrameSecondZero(1)) {
            return;
        }
        // this.oneFrameDeltaResourceMap merge进 this.historyChangeMap, 然后清空前者；
        oneSecondDeltaResourceMap.forEach((modifyResourceTag, map) -> {
            Map<String, List<OneSecondHistoryData>> thisTagHistoryChangeMap = historyChangeMap.get(modifyResourceTag);
            map.forEach((resourceType, oneSecondHistoryData) -> {
                oneSecondHistoryData.setSecond(gameContext.getCurrentIntSecond());
                thisTagHistoryChangeMap.computeIfAbsent(
                        resourceType,
                        it -> new ArrayList<>()
                );
                // FIFO 不大于size阈值
                thisTagHistoryChangeMap.get(resourceType).add(oneSecondHistoryData);
                while (thisTagHistoryChangeMap.get(resourceType).size() > historySecondSize) {
                    thisTagHistoryChangeMap.get(resourceType).remove(0);
                }
            });
            map.clear();
        });
        oneSecondDeltaResourceMap.values().forEach(it -> it.clear());

        // 从this.historyChangeMap中截取latestSecondChangeMap；
        Map<ModifyResourceTag, OneSecondResourceChangeEventOneTagData> tagDataMap = historyChangeMap.keySet().stream()
                .collect(Collectors.toMap(
                        modifyResourceTag -> modifyResourceTag,
                        modifyResourceTag -> {
                            Map<String, Long> secondChangeMap = getSecondChangeMap(modifyResourceTag, 0);
                            return OneSecondResourceChangeEventOneTagData.builder()
                                    .secondChangeMap(secondChangeMap)
                                    .build();
                        }
                ));

        Map<String, Long> totalFrameChangeMap = new HashMap<>();
        tagDataMap.values().forEach(oneTagValue -> {
            oneTagValue.getSecondChangeMap().forEach((k, v) -> {
                totalFrameChangeMap.merge(k, v, (o, n) -> o + n);
            });
        });

        OneSecondResourceChangeEvent event = OneSecondResourceChangeEvent.builder()
                .allTagData(OneSecondResourceChangeEventOneTagData.builder()
                        .secondChangeMap(totalFrameChangeMap)
                        .build())
                .tagDataMap(tagDataMap)
                .build();
        gameContext.getEventManager().notifyOneSecondResourceChange(event);
    }

}
