package hundun.gdxgame.idlemushroom.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.ProxyManager.ProxyGameSituationDTO.ConstructionSituation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyManager {

    IdleMushroomGame game;

    List<ProxyRunRecord> proxyRunRecords = new ArrayList<>();
    Json jsonTool;
    int lastLogicFrameCount = 0;
    ProxyConfig config;
    @Setter
    @Getter
    ProxyState proxyState;
    public enum ProxyState {
        RUNNING,
        PAUSE,
        STOP,
    }

    public ProxyManager(IdleMushroomGame game, ProxyConfig config) {
        this.game = game;
        this.config = config;
        this.jsonTool = new Json();
        this.proxyState = ProxyState.PAUSE;

        jsonTool.setOutputType(OutputType.json);
        jsonTool.setTypeName(null);
        jsonTool.setUsePrototypes(false);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyConfig {
        Integer maxLogicFrameCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyGameSituationDTO {
        Map<String, Long> ownResources;
        List<ConstructionSituation> constructionSituations;
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ConstructionSituation {
            public String prototypeId;
            private int level;
            private int count;
        }

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
    }

    public enum ProxyActionType {
        LogSaveCurrentResult, doUpgrade, buyInstanceOfPrototype

    }

    private void addProxyRunRecord(ProxyActionType actionType, Object... extras) {
        lastLogicFrameCount = game.getLogicFrameHelper().getClockCount();
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .logicFrameCount(lastLogicFrameCount)
                        .actionType(actionType)
                        .extra(JavaFeatureForGwt.listOf(extras).stream()
                                .map(it -> it.toString())
                                .collect(Collectors.toList())
                        )
                        .build()
        );
    }

    private void addProxyRunRecordTypeLogSaveCurrentResult() {
        lastLogicFrameCount = game.getLogicFrameHelper().getClockCount();
        proxyRunRecords.add(
                ProxyRunRecord.builder()
                        .logicFrameCount(lastLogicFrameCount)
                        .actionType(ProxyActionType.LogSaveCurrentResult)
                        .situation(rootSaveDataToSituation(game.getLastSaveCurrentResult()))
                        .build()
        );
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
        if (proxyState == ProxyState.PAUSE) {
            return;
        } else if (proxyState == ProxyState.STOP) {
            System.out.println(jsonTool.prettyPrint(proxyRunRecords));
            Gdx.app.exit();
        }
        if (config.maxLogicFrameCount != null && lastLogicFrameCount > config.maxLogicFrameCount) {
            proxyState = ProxyState.STOP;
        }
        if (game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(1) != 0)
        {
            return;
        }
        // try buyInstanceOfPrototype
        game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                .filter(model -> model.getExistenceComponent().getBuyCandidateConfigs() != null)
                .forEach(model -> {
                    model.getExistenceComponent().getBuyCandidateConfigs().stream()
                            .filter(constructionBuyCandidateConfig -> {
                                boolean canBuyInstanceOfPrototype = game.getIdleGameplayExport().getGameplayContext()
                                        .getConstructionManager()
                                        .canBuyInstanceOfPrototype(constructionBuyCandidateConfig, model.getPosition());
                                return canBuyInstanceOfPrototype;
                            })
                            .findFirst()
                            .ifPresent(constructionBuyCandidateConfig -> {
                                addProxyRunRecord(
                                        ProxyActionType.buyInstanceOfPrototype,
                                        constructionBuyCandidateConfig.getPrototypeId(),
                                        model.getPosition()
                                );
                                game.getIdleGameplayExport().getGameplayContext()
                                        .getConstructionManager()
                                        .buyInstanceOfPrototype(constructionBuyCandidateConfig, model.getPosition());
                            });
                });
        // try doUpgrade
        game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                .filter(model -> model.getUpgradeComponent().canUpgrade())
                .forEach(model -> {
                    addProxyRunRecord(
                            ProxyActionType.doUpgrade,
                            model.getPrototypeId(),
                            model.getPosition()
                    );
                    model.getUpgradeComponent().doUpgrade();
                });

        // try LogSaveCurrentResult
        if (game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(10) == 0)
        {
            addProxyRunRecordTypeLogSaveCurrentResult();
        }

    }
}
