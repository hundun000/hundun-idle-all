package hundun.gdxgame.idledemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.ConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.ui.screen.DemoMenuScreen;
import hundun.gdxgame.idledemo.ui.screen.CookiePlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class DemoChildGameConfig extends ChildGameConfig {
    
    public DemoChildGameConfig() {

//        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
//        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaControlableConstructionVMPrototypeIds = new HashMap<>();
        areaControlableConstructionVMPrototypeIds.put(GameArea.AREA_COOKIE, JavaFeatureForGwt.arraysAsList(
                ConstructionPrototypeId.COOKIE_CLICK_PROVIDER,
                ConstructionPrototypeId.COOKIE_AUTO_PROVIDER,
                ConstructionPrototypeId.COOKIE_AUTO_SELLER
        ));
        this.setAreaControlableConstructionVMPrototypeIds(areaControlableConstructionVMPrototypeIds);

        Map<String, List<String>> areaControlableConstructionPrototypeVMPrototypeIds = new HashMap<>();
        this.setAreaControlableConstructionPrototypeVMPrototypeIds(areaControlableConstructionPrototypeVMPrototypeIds);


        
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

        this.setAreaShowEntityByOwnAmountConstructionPrototypeIds(new HashMap<>());

        Map<String, String> screenIdToFilePathMap = JavaFeatureForGwt.mapOf(
                DemoMenuScreen.class.getSimpleName(), "audio/Loop-Menu.wav",
                CookiePlayScreen.class.getSimpleName(), "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);

        this.setAchievementPrototypeIds(new ArrayList<>());
    }

    
}
