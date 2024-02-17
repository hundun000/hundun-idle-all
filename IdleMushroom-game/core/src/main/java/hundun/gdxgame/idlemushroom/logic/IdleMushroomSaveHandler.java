package hundun.gdxgame.idlemushroom.logic;

import java.util.*;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomAchievementId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.TileNodeUtils;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class IdleMushroomSaveHandler extends PairChildrenSaveHandler<RootSaveData, SystemSettingSaveData, GameplaySaveData> {
    static final String SINGLETON = "SINGLETON";

    int worldSize = 8;
    double treeTileRate = 0.25;

    public IdleMushroomSaveHandler(IFrontend frontEnd, ISaveTool<RootSaveData> saveTool) {
        super(frontEnd, RootSaveData.RootSaveExtension.INSTANCE, saveTool);

    }

    @Override
    protected RootSaveData generateStarterRootSaveData() {
        Map<String, ConstructionSaveData> map = new HashMap<>();

        GridPosition uselessPosition = new GridPosition(0, 0);
        List<GridPosition> worldGridPositions = new ArrayList<>();
        for (int i = -worldSize / 2; i < worldSize / 2; i++) {
            for (int j = -worldSize / 2; j < worldSize / 2; j++) {
                GridPosition emptyPosition = new GridPosition(i, j);
                worldGridPositions.add(emptyPosition);
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

        final int targetTreeCount = (int) (worldGridPositions.size() * treeTileRate);
        Set<GridPosition> treePositionResult = new HashSet<>();
        while (treePositionResult.size() < targetTreeCount) {
            Collections.shuffle(worldGridPositions);
            worldGridPositions.stream()
                    .filter(worldGridPosition -> {
                        long neighborTreeCount = TileNodeUtils.values.stream()
                                .filter(neighbor -> {
                                    GridPosition position = TileNodeUtils.tileNeighborPosition(worldGridPosition, neighbor);
                                    return treePositionResult.contains(position);
                                })
                                .count();
                        return neighborTreeCount < 3;
                    })
                    .findFirst()
                    .ifPresent(worldGridPosition -> {
                        worldGridPositions.remove(worldGridPosition);
                        treePositionResult.add(worldGridPosition);
                    });
        }

        treePositionResult.forEach(it -> {
            map.put(
                    IdleMushroomConstructionPrototypeId.EPOCH_1_TREE + "_" + UUID.randomUUID(),
                    ConstructionSaveData.builder()
                            .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_1_TREE)
                            .level(0)
                            .workingLevel(0)
                            .position(it)
                            .build()
            );
        });

        worldGridPositions.forEach(it -> {
            map.put(
                    IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL + "_" + UUID.randomUUID(),
                    ConstructionSaveData.builder()
                            .prototypeId(IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL)
                            .level(0)
                            .workingLevel(0)
                            .position(it)
                            .build()
            );
        });

        Map<String, Long> ownResources = new HashMap<>();
        ownResources.put(ResourceType.MUSHROOM, 200L);
        ownResources.put(ResourceType.DNA_POINT, 0L);

        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(map)
                        .ownResources(ownResources)
                        .unlockedResourceTypes(new HashSet<>(JavaFeatureForGwt.listOf(
                                ResourceType.MUSHROOM,
                                ResourceType.DNA_POINT
                        )))
                        .achievementSaveDataMap(JavaFeatureForGwt.mapOf(
                                IdleMushroomAchievementId.STEP_1,
                                new AchievementSaveData(AchievementState.RUNNING)
                        ))
                        .buffSaveDataMap(new HashMap<>())
                        .build())
                .systemSettingSaveData(SystemSettingSaveData.builder()
                        .language(Language.EN)
                        .build())
                .build();
    }


}
