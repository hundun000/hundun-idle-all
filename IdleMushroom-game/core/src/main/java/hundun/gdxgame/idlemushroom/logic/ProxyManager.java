package hundun.gdxgame.idlemushroom.logic;

import com.badlogic.gdx.Gdx;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.HistoryManager.ProxyActionType;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyManager {

    IdleMushroomGame game;
    private final IProxyManagerCallback proxyManagerCallback;



    ProxyConfig config;
    @Setter
    @Getter
    ProxyState proxyState;
    public enum ProxyState {
        RUNNING,
        PAUSE,
        STOP,
    }

    public interface IProxyManagerCallback {
        default void onProxyCauseExit(IdleMushroomGame game) {};
    }

    public ProxyManager(IdleMushroomGame game, IProxyManagerCallback proxyManagerCallback) {
        this.game = game;
        this.proxyManagerCallback = proxyManagerCallback;
    }

    public void lazyInit(ProxyConfig config) {
        this.config = config;
        this.proxyState = config.starterProxyState;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyConfig {
        Integer stopConditionSecondCount;
        Map<String, Integer> stopConditionConstructionLevelMap;
        Integer autoSaveDeltaSecond;
        ProxyState starterProxyState;

        public static ProxyConfig devInstance() {
            return ProxyConfig.builder()
                    .stopConditionSecondCount(null)
                    .stopConditionConstructionLevelMap(JavaFeatureForGwt.mapOf(
                            IdleMushroomConstructionPrototypeId.EPOCH_COUNTER,
                            5
                    ))
                    .autoSaveDeltaSecond(null)
                    .starterProxyState(ProxyState.RUNNING)
                    .build();
        }

        public static ProxyConfig releaseInstance() {
            return ProxyConfig.builder()
                    .autoSaveDeltaSecond(10)
                    .starterProxyState(ProxyState.PAUSE)
                    .build();
        }
    }


    public void tryAutoAction() {
        // skip some frame
        if (!game.getIdleGameplayExport().getGameplayContext().getIdleFrontend().modLogicFrameSecondZero(1))
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
                                game.getHistoryManager().addProxyRunRecord(
                                        ProxyActionType.buyInstanceOfPrototype,
                                        constructionBuyCandidateConfig.getPrototypeId(),
                                        model.getPosition()
                                );
                                game.getIdleGameplayExport().getGameplayContext()
                                        .getConstructionManager()
                                        .buyInstanceOfPrototype(constructionBuyCandidateConfig, model.getPosition());
                            });
                });
        // try doUpgrade World
        game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                .filter(model -> model.getUpgradeComponent().canUpgrade())
                .forEach(model -> {
                    proxyDoUpgrade(model);
                });
        // ------- try autoSeller ------
        final BaseConstruction autoSellerConstruction = game.getIdleMushroomExtraGameplayExport().getAutoSellerConstruction();
        if (autoSellerConstruction.getUpgradeComponent().canUpgrade()) {
            proxyDoUpgrade(autoSellerConstruction);
        }
        proxyTryWorkingLevel(autoSellerConstruction);
        // ------- try epoch ------
        final BaseConstruction epochCounterConstruction = game.getIdleMushroomExtraGameplayExport().getEpochCounterConstruction();
        if (epochCounterConstruction.getUpgradeComponent().canUpgrade()) {
            proxyDoUpgrade(epochCounterConstruction);
        }
    }

    private void proxyTryWorkingLevel(BaseConstruction model) {
        int beforeLevel = model.getSaveData().getWorkingLevel();
        // firstly, set max workingLevel
        while (model.getLevelComponent().canChangeWorkingLevel(1)) {
            model.getLevelComponent().changeWorkingLevel(1);
        }
        // secondly, minus until can output
        while (!model.getOutputComponent().canOutput() && model.getLevelComponent().canChangeWorkingLevel(-1)) {
            model.getLevelComponent().changeWorkingLevel(-1);
        }
        int afterLevel = model.getSaveData().getWorkingLevel();
        if (beforeLevel != afterLevel) {
            game.getHistoryManager().addProxyRunRecord(
                    ProxyActionType.changeWorkingLevel,
                    model.getPrototypeId(),
                    "beforeLevel=" + beforeLevel,
                    "afterLevel=" + afterLevel
            );
        }
    }

    private void proxyDoUpgrade(BaseConstruction model) {

        int beforeLevel = model.getSaveData().getLevel();
        model.getUpgradeComponent().doUpgrade();
        if (model.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER)) {
            game.getIdleMushroomExtraGameplayExport().doChangeEpoch(model.getSaveData().getLevel());
        }
        int afterLevel = model.getSaveData().getLevel();
        game.getHistoryManager().addProxyRunRecord(
                ProxyActionType.doUpgrade,
                model.getPrototypeId(),
                model.getPosition(),
                "beforeLevel=" + beforeLevel,
                "afterLevel=" + afterLevel
        );
    }

    private boolean checkStopCondition() {
        if (config.stopConditionSecondCount != null && game.getLogicFrameHelper().getClockCount() > game.getLogicFrameHelper().secondToFrameNum(config.getStopConditionSecondCount())) {
            return true;
        }
        if (config.stopConditionConstructionLevelMap != null) {
            for (String targetPrototypeId : config.stopConditionConstructionLevelMap.keySet()) {
                final int targetLevel = config.stopConditionConstructionLevelMap.get(targetPrototypeId);
                List<BaseConstruction> constructions = game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getAllConstructionInstances().stream()
                        .filter(it -> it.getPrototypeId().equals(targetPrototypeId))
                        .collect(Collectors.toList());
                for (BaseConstruction construction : constructions) {
                    if (construction.getSaveData().getLevel() >= targetLevel) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void onLogicFrame() {

        if (config.getAutoSaveDeltaSecond() != null) {
            if (game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(config.getAutoSaveDeltaSecond()) == 0)
            {
                RootSaveData lastSaveCurrentResult = game.getSaveHandler().gameSaveCurrent();
                game.getHistoryManager().addProxyRunRecordTypeLogSaveCurrentResult(lastSaveCurrentResult);
            }
        }
        if (checkStopCondition()) {
            proxyState = ProxyState.STOP;
        }

        if (proxyState == ProxyState.RUNNING) {
            tryAutoAction();
        } else if (proxyState == ProxyState.STOP) {
            game.getHistoryManager().addProxyRunRecord(
                    ProxyActionType.proxyCauseExit
            );
            proxyManagerCallback.onProxyCauseExit(game);
            Gdx.app.exit();
        }

    }
}
