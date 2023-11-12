package hundun.gdxgame.idlemushroom.logic;

import java.util.*;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoSaveHandler extends PairChildrenSaveHandler<RootSaveData, SystemSettingSaveData, GameplaySaveData> {
    String SINGLETON = "SINGLETON";
    public DemoSaveHandler(IFrontend frontEnd, ISaveTool<RootSaveData> saveTool) {
        super(frontEnd, RootSaveData.RootSaveExtension.INSTANCE, saveTool);

    }

    @Override
    protected RootSaveData genereateStarterRootSaveData() {
        Map<String, ConstructionSaveData> map = new HashMap<>();

        GridPosition uselessPosition = new GridPosition(0, 0);
        GridPosition providerPosition = new GridPosition(1, 1);
        List<GridPosition> worldGridPositions = new ArrayList<>();
        int size = 6;
        for (int i = - size / 2 ; i < size / 2; i++) {
            for (int j = - size / 2; j < size / 2; j++) {
                GridPosition emptyPosition = new GridPosition(i, j);
                if (!emptyPosition.equals(providerPosition)) {
                    worldGridPositions.add(emptyPosition);
                }
            }
        }
        map.put(
                IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM + "_" + SINGLETON,
                ConstructionSaveData.builder()
                        .prototypeId(IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM)
                        .level(1)
                        .workingLevel(1)
                        .position(uselessPosition)
                        .build()
        );
        map.put(
                IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER + "_" + SINGLETON,
                ConstructionSaveData.builder()
                        .prototypeId(IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER)
                        .level(1)
                        .workingLevel(0)
                        .position(uselessPosition)
                        .build()
        );
        map.put(
                IdleMushroomConstructionPrototypeId.EPOCH_COUNTER + "_" + SINGLETON,
                ConstructionSaveData.builder()
                        .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER)
                        .level(1)
                        .workingLevel(1)
                        .position(uselessPosition)
                        .build()
        );
        map.put(
                IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER)
                        .level(1)
                        .workingLevel(1)
                        .position(providerPosition)
                        .build()
        );


        worldGridPositions.forEach(it -> {
            double rand = Math.random();
            if (rand > 0.3) {
                map.put(
                        IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL + "_" + UUID.randomUUID(),
                        ConstructionSaveData.builder()
                                .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL)
                                .level(0)
                                .workingLevel(0)
                                .position(it)
                                .build()
                );
            } else {
                map.put(
                        IdleMushroomConstructionPrototypeId.EPOCH_1_TREE + "_" + UUID.randomUUID(),
                        ConstructionSaveData.builder()
                                .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_1_TREE)
                                .level(0)
                                .workingLevel(0)
                                .position(it)
                                .build()
                );
            }

        });

        Map<String, Long> ownResources = new HashMap<>();
        ownResources.put(ResourceType.MUSHROOM, 5000L);
        ownResources.put(ResourceType.DNA_POINT, 5000L);

        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(map)
                        .ownResources(ownResources)
                        .unlockedResourceTypes(new HashSet<>())
                        .achievementSaveDataMap(JavaFeatureForGwt.mapOf(
                                MushroomAchievementId.STEP_1,
                                new AchievementSaveData(AchievementState.RUNNING)
                        ))
                        .build())
                .systemSettingSaveData(SystemSettingSaveData.builder()
                        .language(Language.EN)
                        .build())
                .build();
    }


}
