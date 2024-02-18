package hundun.gdxgame.idleshare.gamelib.framework.model.buff;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.Map;

public interface IBuffPrototypeLoader {
    static IBuffPrototypeLoader emptyImpl() {
        return new IBuffPrototypeLoader() {
            @Override
            public Map<String, AbstractBuffPrototype> getProviderMap(Language language) {
                return new HashMap<>();
            }
        };
    }

    Map<String, AbstractBuffPrototype> getProviderMap(Language language);
}
