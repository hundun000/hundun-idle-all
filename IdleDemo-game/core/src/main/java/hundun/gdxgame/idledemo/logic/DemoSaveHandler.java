package hundun.gdxgame.idledemo.logic;

import java.util.*;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoSaveHandler extends PairChildrenSaveHandler<RootSaveData, SystemSettingSaveData, GameplaySaveData> {

    public DemoSaveHandler(IFrontend frontEnd, ISaveTool<RootSaveData> saveTool) {
        super(frontEnd, RootSaveData.RootSaveExtension.INSTANCE, saveTool);

    }

    @Override
    protected RootSaveData generateStarterRootSaveData() {
        Map<String, ConstructionSaveData> map = new HashMap<>();

        map.put(
                DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER)
                        .level(0)
                        .workingLevel(0)
                        .position( new GridPosition(999, 999))
                        .build()
        );
        map.put(
                DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER)
                        .level(1)
                        .workingLevel(1)
                        .position( new GridPosition(0, 1))
                        .build()
        );
        map.put(
                DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER)
                        .level(1)
                        .workingLevel(0)
                        .position( new GridPosition(999, 999))
                        .build()
        );
        List<GridPosition> dirtGridPositions = JavaFeatureForGwt.listOf(
                new GridPosition(4, 5),
                new GridPosition(4, 4),
                new GridPosition(4, 6),
                new GridPosition(5, 5),
                new GridPosition(5, 4),
                new GridPosition(5, 6),
                new GridPosition(6, 5),
                new GridPosition(6, 4),
                new GridPosition(6, 6)
        );
        dirtGridPositions.forEach(it -> {
            map.put(
                    DemoConstructionPrototypeId.EMPTY_CELL + "_" + UUID.randomUUID(),
                    ConstructionSaveData.builder()
                            .prototypeId(DemoConstructionPrototypeId.EMPTY_CELL)
                            .level(0)
                            .workingLevel(0)
                            .position(it)
                            .build()
            );
        });

        Map<String, Long> ownResources = new HashMap<>();
        ownResources.put(ResourceType.COIN, 500L);

        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(map)
                        .ownResources(ownResources)
                        .unlockedResourceTypes(new HashSet<>())
                        .achievementSaveDataMap(JavaFeatureForGwt.mapOf(
                                DemoAchievementId.STEP_1,
                                new AchievementSaveData(AchievementState.RUNNING)
                        ))
                        .build())
                .systemSettingSaveData(SystemSettingSaveData.builder()
                        .language(Language.EN)
                        .build())
                .build();
    }


}
