package hundun.gdxgame.idlemushroom.logic.loader;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomAchievementId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.AbstractBuffPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.IBuffPrototypeLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.OutputScaleBuffPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.OutputScaleBuffPrototype.OutputScaleOneConstructionConfig;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdleMushroomBuffPrototypeLoader implements IBuffPrototypeLoader {
    @Override
    public Map<String, AbstractBuffPrototype> getProviderMap(Language language) {
        Map<String, List<String>> textMap = new HashMap<>();
        switch (language)
        {
            case CN:
                textMap.put(IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE, JavaFeatureForGwt.listOf(
                        "蘑菇增产",
                        "蘑菇增产" + "：\n"
                        + "•每一级使生产倍率变为{0}倍。",
                        "等级{0}"
                ));
                break;
            default:
                textMap.put(IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE, JavaFeatureForGwt.listOf(
                        "Mushroom-boost",
                        "Mushroom-boost" + "：\n"
                        + "•Each level makes the mushroom production multiply by {0} .",
                        "lv: {0}"
                ));
                break;
        }



        return JavaFeatureForGwt.mapOf(
                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                quickOutputScaleBuffPrototype(textMap, IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                        "2")
        );
    }


    private static OutputScaleBuffPrototype quickOutputScaleBuffPrototype(
            Map<String, List<String>> textMap,
            String id,
            String arg) {
        OutputScaleOneConstructionConfig config = OutputScaleOneConstructionConfig.builder()
                .scaleGainArg(Float.parseFloat(arg))
                .build();
        Map<String, OutputScaleOneConstructionConfig> allConstructionConfigMap = new HashMap<>();
        allConstructionConfigMap.put(IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, config);
        allConstructionConfigMap.put(IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER, config);
        allConstructionConfigMap.put(IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER, config);
        return new OutputScaleBuffPrototype(
                id,
                textMap.get(id).get(0),
                JavaFeatureForGwt.stringFormat(textMap.get(id).get(1), arg),
                textMap.get(id).get(2),
                allConstructionConfigMap
        );
    }
}
