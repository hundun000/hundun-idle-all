package hundun.gdxgame.idledemo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idledemo.logic.prototype.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.IBuiltinConstructionsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;


public class DemoBuiltinConstructionsLoader implements IBuiltinConstructionsLoader {

    @Override
    public Map<String, AbstractConstructionPrototype> getProviderMap(Language language) {
        RootEpochConfig epochConfig1 = IdleMushroomGame.epochConfigMap.get(1);
        RootEpochConfig epochConfig2 = IdleMushroomGame.epochConfigMap.get(2);
        RootEpochConfig epochConfig3 = IdleMushroomGame.epochConfigMap.get(3);
        Map<String, AbstractConstructionPrototype> result = new HashMap<>();
        result.put(DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL, new DirtPrototype(language));
        result.put(DemoConstructionPrototypeId.EPOCH_1_TREE, new TreePrototype(
                DemoConstructionPrototypeId.EPOCH_1_TREE,
                language
        ));
        result.put(DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                epochConfig1,
                language
        ));
        result.put(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                epochConfig2,
                language
        ));
        result.put(DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER,
                epochConfig3,
                language
        ));
        result.put(DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER, new AutoSellerPrototype(language));
        result.put(DemoConstructionPrototypeId.MAIN_MUSHROOM, new MainMushroomPrototype(language));
        result.put(DemoConstructionPrototypeId.EPOCH_COUNTER, new EpochCounterPrototype(language));
        return result;
    }
    
    public static ResourcePack toPack(Map<String, Integer> map) {
        ResourcePack pack = new ResourcePack();
        List<ResourcePair> pairs = new ArrayList<>(map.size());
        map.entrySet().forEach(entry -> pairs.add(new ResourcePair(entry.getKey(), (long)entry.getValue())));
        pack.setBaseValues(pairs);
        return pack;
    }
    
    

}
