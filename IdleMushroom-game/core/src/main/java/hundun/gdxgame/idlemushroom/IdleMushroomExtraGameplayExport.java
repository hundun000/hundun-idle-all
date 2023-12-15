package hundun.gdxgame.idlemushroom;

import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.IGameStartListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.BuffEpochConfig;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.ConstructionEpochConfig;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class IdleMushroomExtraGameplayExport implements IGameStartListener {
    IdleMushroomGame game;
    private final Map<Integer, RootEpochConfig> epochConfigMap;
    @Getter
    RootEpochConfig currentRootEpochConfig;
    @Getter
    @Null
    RootEpochConfig nextRootEpochConfig;
    // for quick ref
    @Getter
    BaseConstruction epochCounterConstruction;
    // for quick ref
    @Getter
    BaseConstruction mainClickerConstruction;
    IdleMushroomExtraGameplayExport(IdleMushroomGame game) {
        this.game = game;
        this.epochConfigMap = JavaFeatureForGwt.mapOf(
                1, RootEpochConfig.builder()
                        .enlargementLevel(1)
                        .maxLevel(5)
                        .buffEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                                BuffEpochConfig.builder()
                                        .buffLevel(0)
                                        .build()
                        ))
                        .build(),
                2, RootEpochConfig.builder()
                        .enlargementLevel(1)
                        .maxLevel(7)
                        .buffEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                                BuffEpochConfig.builder()
                                        .buffLevel(1)
                                        .build()
                        ))
                        .build(),
                3, RootEpochConfig.builder()
                        .enlargementLevel(2)
                        .maxLevel(10)
                        .buffEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                                BuffEpochConfig.builder()
                                        .buffLevel(2)
                                        .build()
                        ))
                        .constructionEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL)
                                        .build(),
                                IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL)
                                        .build(),
                                IdleMushroomConstructionPrototypeId.EPOCH_1_TREE,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_2_TREE)
                                        .build()
                        ))
                        .build(),
                4, RootEpochConfig.builder()
                        .enlargementLevel(2)
                        .maxLevel(12)
                        .buffEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                                BuffEpochConfig.builder()
                                        .buffLevel(3)
                                        .build()
                        ))
                        .build(),
                5, RootEpochConfig.builder()
                        .enlargementLevel(3)
                        .maxLevel(15)
                        .buffEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                                BuffEpochConfig.builder()
                                        .buffLevel(4)
                                        .build()
                        ))
                        .constructionEpochConfigMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL)
                                        .build(),
                                IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL)
                                        .build(),
                                IdleMushroomConstructionPrototypeId.EPOCH_2_TREE,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(IdleMushroomConstructionPrototypeId.EPOCH_3_TREE)
                                        .build()
                        ))
                        .build()
        );
    }


    public void doChangeEpoch(int currentEpochLevel) {
        @Null RootEpochConfig lastRootEpochConfig = epochConfigMap.get(currentEpochLevel - 1);
        this.currentRootEpochConfig = epochConfigMap.get(currentEpochLevel);
        this.nextRootEpochConfig = epochConfigMap.get(currentEpochLevel + 1);

        game.getScreenContext().getMainPlayScreen().setMainClickerWithScale();
        // ------ UpdateBuff ------
        Map<String, Integer> deltaMap = currentRootEpochConfig.buffEpochConfigMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                it -> it.getKey(),
                                it -> {
                                    if (lastRootEpochConfig == null) {
                                        return it.getValue().getBuffLevel();
                                    } else {
                                        BuffEpochConfig lastConfig = lastRootEpochConfig.getBuffEpochConfigMap().get(it.getKey());
                                        if (lastConfig == null) {
                                            return it.getValue().getBuffLevel();
                                        } else {
                                            return it.getValue().getBuffLevel() - lastConfig.getBuffLevel();
                                        }
                                    }
                                }));
        deltaMap.entrySet().removeIf(it -> it.getValue() == 0);
        if (!deltaMap.isEmpty()) {
            game.getIdleGameplayExport().getGameplayContext().getBuffManager().modifyBuffLevel(deltaMap);
        }
        // ------ UpdateMaxLevel ------
        boolean needUpdateMaxLevel = lastRootEpochConfig == null || lastRootEpochConfig.getMaxLevel() != currentRootEpochConfig.getMaxLevel();
        if (needUpdateMaxLevel) {
            game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                    .forEach(it -> {
                        it.getLevelComponent().maxLevel = currentRootEpochConfig.getMaxLevel();
                    });
        }
        // ------ UpdateEveryConstruction ------
        boolean needUpdateEveryConstruction =
                (lastRootEpochConfig == null
                        || lastRootEpochConfig.getEnlargementLevel() != currentRootEpochConfig.getEnlargementLevel())
                && currentRootEpochConfig.getConstructionEpochConfigMap() != null;
        if (needUpdateEveryConstruction) {
            game.getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                    .forEach(it -> {
                        ConstructionEpochConfig constructionEpochConfig = currentRootEpochConfig.getConstructionEpochConfigMap()
                                .get(it.getPrototypeId());
                        if (constructionEpochConfig != null) {
                            game.getIdleGameplayExport().getGameplayContext().getConstructionManager().addToRemoveQueue(it);
                            if (constructionEpochConfig.getTransformToPrototypeId() != null) {
                                game.getIdleGameplayExport().getGameplayContext().getConstructionManager().addToCreateQueue(
                                        constructionEpochConfig.getTransformToPrototypeId(),
                                        it.getPosition()
                                );
                            }
                        }
                    });
        }
        // ------ UpdateSingleton ------
        while (mainClickerConstruction.getSaveData().getLevel() < currentRootEpochConfig.getEnlargementLevel()) {
            mainClickerConstruction.getUpgradeComponent().doUpgrade();
        }

    }

    public void lazyInitStage2() {
        game.getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
    }

    @Override
    public void onGameStart() {
        this.epochCounterConstruction = game.getIdleGameplayExport().getGameplayContext()
                .getConstructionManager()
                .getSingletonConstructionInstancesOrEmpty()
                .stream()
                .filter(it -> it.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER))
                .findAny()
                .orElseThrow(() -> new RuntimeException("bad onGameStart"));
        this.mainClickerConstruction = game.getIdleGameplayExport().getGameplayContext().getConstructionManager()
                .getSingletonConstructionInstancesOrEmpty()
                .stream()
                .filter(it -> it.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM))
                .findAny()
                .orElseThrow(() -> new RuntimeException("bad onGameStart"));
        epochCounterConstruction.getLevelComponent().maxLevel = epochConfigMap.keySet().stream()
                .mapToInt(it -> it)
                .max()
                .orElseThrow(() -> new RuntimeException("bad config"));
        mainClickerConstruction.getLevelComponent().maxLevel = epochConfigMap.values().stream()
                .mapToInt(it -> it.getEnlargementLevel())
                .max()
                .orElseThrow(() -> new RuntimeException("bad config"));
        doChangeEpoch(1);
    }
}
