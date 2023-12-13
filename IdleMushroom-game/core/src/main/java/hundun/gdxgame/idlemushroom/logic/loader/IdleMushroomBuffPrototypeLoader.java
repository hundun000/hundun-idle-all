package hundun.gdxgame.idlemushroom.logic.loader;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.AbstractBuffPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.IBuffPrototypeLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.OutputScaleBuffPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.OutputScaleBuffPrototype.OutputScaleOneConstructionConfig;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.Map;

public class IdleMushroomBuffPrototypeLoader implements IBuffPrototypeLoader {
    @Override
    public Map<String, AbstractBuffPrototype> getProviderMap(Language language) {
        return JavaFeatureForGwt.mapOf(
                IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                new OutputScaleBuffPrototype(
                        IdleMushroomBuffId.BUFF_MUSHROOM_OUTPUT_SCALE,
                        "蘑菇增产",
                        "每一级增产10倍",
                        OutputScaleOneConstructionConfig.builder()
                                .scaleGainArg(10.0f)
                                .build()
                )
        );
    }
}
