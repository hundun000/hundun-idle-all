package hundun.gdxgame.idlemushroom;

import java.util.ArrayList;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.logic.ResourceType;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class IdleMushroomChildGameConfig extends ChildGameConfig {
    
    public IdleMushroomChildGameConfig() {

        this.setConstructionConfig(
                ConstructionConfig.builder()
                        .mainClickerConstructionPrototypeId(IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM)
                        .singletonPrototypeIds(JavaFeatureForGwt.listOf(
                                IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM,
                                IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER,
                                IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                        ))
                        .worldPrototypeIds(JavaFeatureForGwt.listOf(
                                IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                                IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL,
                                IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL,
                                IdleMushroomConstructionPrototypeId.EPOCH_1_TREE,
                                IdleMushroomConstructionPrototypeId.EPOCH_2_TREE,
                                IdleMushroomConstructionPrototypeId.EPOCH_3_TREE,
                                IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                                IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                                IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER
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
