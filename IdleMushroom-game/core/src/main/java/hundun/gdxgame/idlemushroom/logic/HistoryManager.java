package hundun.gdxgame.idlemushroom.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEvent;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEventOneTagData;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.StorageManager.ModifyResourceTag;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryManager implements IResourceChangeListener, ILogicFrameListener {
    private final List<OneSecondResourceChangeEventOneTagData> outputHistoryList = new ArrayList<>();
    private final IdleMushroomGame game;
    @Getter
    private List<ProxyRunRecord> proxyRunRecords = new ArrayList<>();


    public HistoryManager(IdleMushroomGame game) {
        this.game = game;
    }

    public void addProxyRunRecord(ProxyActionType actionType, Object... extras) {
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .second(game.getIdleGameplayExport().getGameplayContext().getIdleFrontend().getSecond())
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
                        .second(game.getIdleGameplayExport().getGameplayContext().getIdleFrontend().getSecond())
                        .actionType(ProxyActionType.LogSaveCurrentResult)
                        .situation(rootSaveDataToSituation(lastSaveCurrentResult))
                        .build()
        );
    }

    private void addProxyRunRecordTypeLogResourcesDeltaMap() {
        Map<String, Float> avgResourceDeltaMap = new HashMap<>();
        outputHistoryList.stream()
                .forEach(it -> {
                    it.getSecondChangeMap().forEach((k, v) -> {
                        avgResourceDeltaMap.merge(k, v * 1.0f / outputHistoryList.size(), (o, n) -> o + n);
                    });
                });
        outputHistoryList.clear();
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .second(game.getIdleGameplayExport().getGameplayContext().getIdleFrontend().getSecond())
                        .actionType(ProxyActionType.LogResourcesDeltaMap)
                        .avgResourceDeltaMap(avgResourceDeltaMap)
                        .build()
        );
    }

    @Override
    public void onResourceChange(OneSecondResourceChangeEvent event) {
        outputHistoryList.add(event.getTagDataMap().get(ModifyResourceTag.OUTPUT));
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
        if (game.getIdleGameplayExport().getGameplayContext().getIdleFrontend().modLogicFrameSecondZero(5))
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
        float second;
        ProxyActionType actionType;
        List<String> extra;
        ProxyGameSituationDTO situation;
        Map<String, Float> avgResourceDeltaMap;
    }

    public enum ProxyActionType {
        LogSaveCurrentResult, doUpgrade, LogResourcesDeltaMap, changeWorkingLevel, proxyCauseExit, buyInstanceOfPrototype

    }
}
