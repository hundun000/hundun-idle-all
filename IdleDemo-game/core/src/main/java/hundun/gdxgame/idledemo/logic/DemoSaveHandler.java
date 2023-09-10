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
                ConstructionPrototypeId.COOKIE_CLICK_PROVIDER + "_" + UUID.randomUUID().toString(),
                ConstructionSaveData.builder()
                        .prototypeId(ConstructionPrototypeId.COOKIE_CLICK_PROVIDER)
                        .level(0)
                        .workingLevel(0)
                        .position( new GridPosition(0, 0))
                        .build()
        );
        map.put(
                ConstructionPrototypeId.COOKIE_AUTO_PROVIDER + "_" + UUID.randomUUID().toString(),
                ConstructionSaveData.builder()
                        .prototypeId(ConstructionPrototypeId.COOKIE_AUTO_PROVIDER)
                        .level(1)
                        .workingLevel(1)
                        .position( new GridPosition(0, 1))
                        .build()
        );
        map.put(
                ConstructionPrototypeId.COOKIE_AUTO_SELLER + "_" + UUID.randomUUID().toString(),
                ConstructionSaveData.builder()
                        .prototypeId(ConstructionPrototypeId.COOKIE_AUTO_SELLER)
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
                    ConstructionPrototypeId.DIRT + "_" + UUID.randomUUID().toString(),
                    ConstructionSaveData.builder()
                            .prototypeId(ConstructionPrototypeId.DIRT)
                            .level(0)
                            .workingLevel(0)
                            .position(it)
                            .build()
            );
        });
        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(map)
                        .ownResoueces(new HashMap<>())
                        .unlockedResourceTypes(new HashSet<>())
                        .unlockedAchievementIds(new HashSet<>())
                        .build())
                .systemSettingSaveData(SystemSettingSaveData.builder()
                        .language(Language.EN)
                        .build())
                .build();
    }


}
