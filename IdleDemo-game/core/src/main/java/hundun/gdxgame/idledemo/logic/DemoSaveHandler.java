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

        GridPosition USELESS_POSITON = new GridPosition(0, 0);

        return RootSaveData.builder()
                .gameplaySave(GameplaySaveData.builder()
                        .constructionSaveDataMap(
                                JavaFeatureForGwt.mapOf(
                                        ConstructionPrototypeId.COOKIE_CLICK_PROVIDER,
                                        ConstructionSaveData.builder()
                                                .prototypeId(ConstructionPrototypeId.COOKIE_CLICK_PROVIDER)
                                                .level(0)
                                                .workingLevel(0)
                                                .position(USELESS_POSITON)
                                                .build(),
                                        ConstructionPrototypeId.COOKIE_AUTO_PROVIDER,
                                        ConstructionSaveData.builder()
                                                .prototypeId(ConstructionPrototypeId.COOKIE_AUTO_PROVIDER)
                                                .level(1)
                                                .workingLevel(1)
                                                .position(USELESS_POSITON)
                                                .build(),
                                        ConstructionPrototypeId.COOKIE_AUTO_SELLER,
                                        ConstructionSaveData.builder()
                                                .prototypeId(ConstructionPrototypeId.COOKIE_AUTO_SELLER)
                                                .level(1)
                                                .workingLevel(0)
                                                .position(USELESS_POSITON)
                                                .build()
                                )

                        )
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
