package hundun.gdxgame.idledemo;

import java.util.ArrayList;
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
                        .singletonPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER,
                                DemoConstructionPrototypeId.EPOCH_COUNTER
                        ))
                        .worldPrototypeIds(JavaFeatureForGwt.listOf(
                                DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                                DemoConstructionPrototypeId.EPOCH_1_TREE,
                                DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                                DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER
                        ))
                        .build()
        );
        
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
