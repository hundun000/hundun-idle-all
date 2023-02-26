package hundun.gdxgame.idledemo.logic;

import java.util.HashMap;
import java.util.HashSet;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
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
                .systemSettingSaveData(SystemSettingSaveData.builder()
                        .language(Language.EN)
                        .build())
                .build();
    }


}
