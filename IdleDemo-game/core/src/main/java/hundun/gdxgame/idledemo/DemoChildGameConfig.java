package hundun.gdxgame.idledemo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.BuffId;
import hundun.gdxgame.idledemo.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.ConstructionId;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.ui.screen.DemoMenuScreen;
import hundun.gdxgame.idledemo.ui.screen.DemoPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.AchievementPrototype;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class DemoChildGameConfig extends ChildGameConfig {
    
    public DemoChildGameConfig() {

//        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
//        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_COOKIE, JavaFeatureForGwt.arraysAsList(
                ConstructionId.COOKIE_CLICK_PROVIDER
        ));
        areaShownConstructionIds.put(GameArea.AREA_BUILDING, JavaFeatureForGwt.arraysAsList(
                ConstructionId.COOKIE_AUTO_PROVIDER,
                ConstructionId.COOKIE_SELLER
        ));
        areaShownConstructionIds.put(GameArea.AREA_WIN, JavaFeatureForGwt.arraysAsList(
                ConstructionId.WIN_PROVIDER
        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.AREA_COOKIE, JavaFeatureForGwt.arraysAsList(
            ResourceType.COIN
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_COOKIE, JavaFeatureForGwt.arraysAsList(
            ResourceType.COOKIE
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        Map<String, String> screenIdToFilePathMap = JavaFeatureForGwt.mapOf(
                DemoMenuScreen.class.getSimpleName(), "audio/Loop-Menu.wav",
                DemoPlayScreen.class.getSimpleName(), "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaFeatureForGwt.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaFeatureForGwt.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }

    
}
