package hundun.gdxgame.idledemo.logic;

import java.util.HashMap;
import java.util.HashSet;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.StarterSaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoSaveHandler extends StarterSaveHandler<RootSaveData, Void, GameplaySaveData> {

    public DemoSaveHandler(IFrontend frontEnd, ISaveTool<RootSaveData> saveTool) {
        super(frontEnd, RootSaveData.Factory.INSTANCE, saveTool);

    }

    @Override
    protected RootSaveData genereateStarterRootSaveData() {
        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(
                                JavaFeatureForGwt.mapOf(
                                        ConstructionId.COOKIE_SELLER, 
                                        ConstructionSaveData.builder()
                                                .level(1)
                                                .workingLevel(0)
                                                .build()))
                        .ownResoueces(new HashMap<>())
                        .unlockedResourceTypes(new HashSet<>())
                        .unlockedAchievementNames(new HashSet<>())
                        .build())
                .build();
    }

}
