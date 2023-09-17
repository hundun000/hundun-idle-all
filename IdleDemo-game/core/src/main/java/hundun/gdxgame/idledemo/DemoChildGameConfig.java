package hundun.gdxgame.idledemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class DemoChildGameConfig extends ChildGameConfig {
    
    public DemoChildGameConfig() {

//        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
//        this.setConstructions(builtinConstructionsLoader.load());


        this.setConstructionConfig(
                ConstructionConfig.builder()
                        .emptyConstructionConfig(EmptyConstructionConfig.builder()
                                .prototypeId(DemoConstructionPrototypeId.EMPTY_CELL)
                                .buyCandidatePrototypeIds(JavaFeatureForGwt.listOf(
                                        DemoConstructionPrototypeId.COOKIE_AUTO_PROVIDER
                                ))
                                .build()
                        )
                        .singletonPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.SINGLETON_COOKIE_CLICK_PROVIDER,
                                DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER
                        ))
                        .worldPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.EMPTY_CELL,
                                DemoConstructionPrototypeId.COOKIE_AUTO_PROVIDER
                        ))
                        .build()
        );


        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>();
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(DemoScreenId.SCREEN_COOKIE, JavaFeatureForGwt.arraysAsList(
            ResourceType.COOKIE
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);

        this.setAreaShowEntityByOwnAmountConstructionPrototypeIds(new HashMap<>());

        Map<String, String> screenIdToFilePathMap = JavaFeatureForGwt.mapOf(
                DemoScreenId.SCREEN_COOKIE, "audio/Loop-Menu.wav",
                DemoScreenId.SCREEN_WORLD, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);

    }

    
}
