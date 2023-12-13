package hundun.gdxgame.idlemushroom.logic.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.prototype.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.IBuiltinConstructionsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;


public class IdleMushroomConstructionsLoader implements IBuiltinConstructionsLoader {

    @Override
    public Map<String, AbstractConstructionPrototype> getProviderMap(Language language) {
        Map<String, AbstractConstructionPrototype> result = new HashMap<>();
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL, new DirtPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL, new DirtPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL, new DirtPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_1_TREE, new TreePrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_1_TREE,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_2_TREE, new TreePrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_2_TREE,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_3_TREE, new TreePrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_3_TREE,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER, new AutoProviderPrototype(
                IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER,
                language
        ));
        result.put(IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER, new AutoSellerPrototype(language));
        result.put(IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM, new MainMushroomPrototype(language));
        result.put(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new EpochCounterPrototype(language));
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
