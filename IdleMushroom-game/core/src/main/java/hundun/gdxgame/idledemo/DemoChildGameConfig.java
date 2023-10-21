package hundun.gdxgame.idledemo;

import java.util.ArrayList;
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

        this.setConstructionConfig(
                ConstructionConfig.builder()
                        .mainClickerConstructionPrototypeId(DemoConstructionPrototypeId.MAIN_MUSHROOM)
                        .emptyConstructionConfig(EmptyConstructionConfig.builder()
                                .prototypeId(DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL)
                                .buyCandidatePrototypeIds(JavaFeatureForGwt.listOf(
                                        DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                                ))
                                .build()
                        )
                        .singletonPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER
                        ))
                        .worldPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                                DemoConstructionPrototypeId.EPOCH_1_TREE,
                                DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                        ))
                        .build()
        );
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>();
        this.setAreaEntityEffectConfigMap(JavaFeatureForGwt.mapOf(
                DemoScreenId.SCREEN_MAIN,
                AreaEntityEffectConfig.builder()
                        .changeAmountResourceIds(JavaFeatureForGwt.listOf(ResourceType.MUSHROOM))
                        .ownAmountConstructionPrototypeIds(new ArrayList<>(0))
                        .ownAmountResourceIds(new ArrayList<>(0))
                        .build()
        ));

        Map<String, String> screenIdToFilePathMap = JavaFeatureForGwt.mapOf(
                DemoScreenId.SCREEN_MAIN, "audio/Loop-Menu.wav",
                DemoScreenId.SCREEN_WORLD, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);

    }

    
}
