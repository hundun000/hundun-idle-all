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
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;
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
    protected RootSaveData genereateStarterRootSaveData() {
        Map<String, ConstructionSaveData> map = new HashMap<>();

        map.put(
                DemoConstructionPrototypeId.COOKIE_CLICK_PROVIDER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_CLICK_PROVIDER)
                        .level(0)
                        .workingLevel(0)
                        .position( new GridPosition(0, 0))
                        .build()
        );
        map.put(
                DemoConstructionPrototypeId.COOKIE_AUTO_PROVIDER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_AUTO_PROVIDER)
                        .level(1)
                        .workingLevel(1)
                        .position( new GridPosition(0, 1))
                        .build()
        );
        map.put(
                DemoConstructionPrototypeId.COOKIE_AUTO_SELLER + "_" + UUID.randomUUID(),
                ConstructionSaveData.builder()
                        .prototypeId(DemoConstructionPrototypeId.COOKIE_AUTO_SELLER)
                        .level(1)
                        .workingLevel(0)
                        .position( new GridPosition(1, 0))
                        .build()
        );
        List<GridPosition> dirtGridPositions = JavaFeatureForGwt.listOf(
                new GridPosition(-1, 0),
                new GridPosition(0, -1),
                new GridPosition(-1, -1)
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
