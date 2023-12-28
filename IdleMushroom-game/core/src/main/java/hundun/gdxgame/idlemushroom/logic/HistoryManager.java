package hundun.gdxgame.idlemushroom.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryManager implements IOneFrameResourceChangeListener, ILogicFrameListener {
    private final Map<String, List<Long>> deltaHistoryMap = new HashMap<>();
    private final IdleMushroomGame game;
    @Getter
    private List<ProxyRunRecord> proxyRunRecords = new ArrayList<>();


    public HistoryManager(IdleMushroomGame game) {
        this.game = game;
    }

    public void addProxyRunRecord(ProxyActionType actionType, Object... extras) {
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .logicFrameCount(game.getLogicFrameHelper().getClockCount())
                        .actionType(actionType)
                        .extra(JavaFeatureForGwt.listOf(extras).stream()
                                .map(it -> it.toString())
                                .collect(Collectors.toList())
                        )
                        .build()
        );
    }

    public void addProxyRunRecordTypeLogSaveCurrentResult(RootSaveData lastSaveCurrentResult) {
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .logicFrameCount(game.getLogicFrameHelper().getClockCount())
                        .actionType(ProxyActionType.LogSaveCurrentResult)
                        .situation(rootSaveDataToSituation(lastSaveCurrentResult))
                        .build()
        );
    }

    private void addProxyRunRecordTypeLogResourcesDeltaMap() {
        Map<String, Long> resourcesDeltaMap = deltaHistoryMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                it -> it.getKey(),
                                it -> it.getValue().stream().mapToLong(itt -> itt).sum()
                        ));
        deltaHistoryMap.clear();
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .logicFrameCount(game.getLogicFrameHelper().getClockCount())
                        .actionType(ProxyActionType.LogResourcesDeltaMap)
                        .resourcesDeltaMap(resourcesDeltaMap)
                        .build()
        );
    }

    @Override
    public void onResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        changeMap.keySet().forEach(resourceType -> this.deltaHistoryMap.computeIfAbsent(resourceType, it -> new ArrayList<>()));
        this.deltaHistoryMap.forEach((resourceType, value) -> {
            value.add(changeMap.getOrDefault(resourceType, 0L));
        });
    }

    private ProxyGameSituationDTO rootSaveDataToSituation(RootSaveData rootSaveData) {
        if (rootSaveData == null) {
            return null;
        }
        List<ConstructionSituation> constructionSituations = rootSaveData.getGameplaySave().getConstructionSaveDataMap().values().stream()
                .filter(it -> it.getLevel() > 0)
                .map(it -> ConstructionSituation.builder()
                        .prototypeId(it.prototypeId)
                        .level(it.getLevel())
                        .count(1)
                        .build()
                )
                .collect(Collectors.toList());
        List<ConstructionSituation> countedConstructionSituations = new ArrayList<>();
        constructionSituations.forEach(it -> {
            ConstructionSituation counted = countedConstructionSituations.stream()
                    .filter(itt -> itt.getPrototypeId().equals(it.getPrototypeId()) && itt.getLevel() == it.getLevel())
                    .findAny()
                    .orElse(null);
            if (counted == null) {
                countedConstructionSituations.add(it);
            } else {
                counted.setCount(counted.getCount() + it.getCount());
            }
        });

        return ProxyGameSituationDTO.builder()
                .ownResources(rootSaveData.getGameplaySave().getOwnResources())
                .constructionSituations(countedConstructionSituations)
                .build();
    }

    public void onLogicFrame() {
        // try LogSaveCurrentResult
        if (game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(5) == 0)
        {
            game.getHistoryManager().addProxyRunRecordTypeLogResourcesDeltaMap();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyGameSituationDTO {
        Map<String, Long> ownResources;
        List<ConstructionSituation> constructionSituations;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConstructionSituation {
        public String prototypeId;
        private int level;
        private int count;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyRunRecord {
        int logicFrameCount;
        ProxyActionType actionType;
        List<String> extra;
        ProxyGameSituationDTO situation;
        Map<String, Long> resourcesDeltaMap;
    }

    public enum ProxyActionType {
        LogSaveCurrentResult, doUpgrade, LogResourcesDeltaMap, buyInstanceOfPrototype

    }
}
