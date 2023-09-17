package hundun.gdxgame.idledemo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idledemo.logic.prototype.CookieAutoProviderPrototype;
import hundun.gdxgame.idledemo.logic.prototype.CookieAutoSellerPrototype;
import hundun.gdxgame.idledemo.logic.prototype.CookieClickProviderPrototype;
import hundun.gdxgame.idledemo.logic.prototype.DirtPrototype;
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
        result.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_CLICK_PROVIDER, new CookieClickProviderPrototype(language));
        result.put(DemoConstructionPrototypeId.COOKIE_AUTO_PROVIDER, new CookieAutoProviderPrototype(language));
        result.put(DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER, new CookieAutoSellerPrototype(language));
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
