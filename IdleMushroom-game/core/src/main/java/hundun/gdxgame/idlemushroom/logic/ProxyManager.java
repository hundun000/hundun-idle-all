package hundun.gdxgame.idlemushroom.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.HistoryManager.ProxyActionType;
import lombok.*;

public class ProxyManager {

    IdleMushroomGame game;


    public Json jsonTool;

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
        this.proxyState = config.starterProxyState;

        jsonTool.setOutputType(OutputType.json);
        jsonTool.setTypeName(null);
        jsonTool.setUsePrototypes(false);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProxyConfig {
        Integer maxSecondCount;
        Integer autoSaveDeltaSecond;
        ProxyState starterProxyState;
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
        // try doUpgrade
        game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                .filter(model -> model.getUpgradeComponent().canUpgrade())
                .forEach(model -> {
                    game.getHistoryManager().addProxyRunRecord(
                            ProxyActionType.doUpgrade,
                            model.getPrototypeId(),
                            model.getPosition()
                    );
                    model.getUpgradeComponent().doUpgrade();
                });
    }




    public void onLogicFrame() {

        if (config.getAutoSaveDeltaSecond() != null) {
            if (game.getLogicFrameHelper().getClockCount() % game.getLogicFrameHelper().secondToFrameNum(config.getAutoSaveDeltaSecond()) == 0)
            {
                RootSaveData lastSaveCurrentResult = game.getSaveHandler().gameSaveCurrent();
                game.getHistoryManager().addProxyRunRecordTypeLogSaveCurrentResult(lastSaveCurrentResult);
            }
        }
        if (config.maxSecondCount != null && game.getLogicFrameHelper().getClockCount() > game.getLogicFrameHelper().secondToFrameNum(config.getMaxSecondCount())) {
            proxyState = ProxyState.STOP;
        }

        if (proxyState == ProxyState.RUNNING) {
            tryAutoAction();
        } else if (proxyState == ProxyState.STOP) {
            Gdx.app.exit();
        }

    }
}
