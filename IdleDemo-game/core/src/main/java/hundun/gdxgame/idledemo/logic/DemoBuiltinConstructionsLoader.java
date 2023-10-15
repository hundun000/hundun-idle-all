package hundun.gdxgame.idledemo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idledemo.logic.prototype.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.*;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.IBuiltinConstructionsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;


public class DemoBuiltinConstructionsLoader implements IBuiltinConstructionsLoader {

    @Override
    public Map<String, AbstractConstructionPrototype> getProviderMap(Language language) {
        Map<String, AbstractConstructionPrototype> result = new HashMap<>();
        result.put(DemoConstructionPrototypeId.EMPTY_CELL, new DirtPrototype(language));
        result.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER, new CookieMainClickerPrototype(language));
        result.put(DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER, new CookieSimpleAutoProviderPrototype(language));
        result.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER, new CookieSingletonAutoSellerPrototype(language));
        result.put(DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER, new CookieComplexAutoProviderPrototype(language));
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
