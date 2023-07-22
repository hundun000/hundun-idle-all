package hundun.gdxgame.idledemo.logic;

import hundun.gdxgame.idleshare.gamelib.framework.model.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.IBuiltinAchievementsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.HashMap;
import java.util.Map;

public class IdleForestAchievementLoader implements IBuiltinAchievementsLoader {
    @Override
    public Map<String, AbstractAchievement> getProviderMap(Language language) {
        return new HashMap<>();
    }
}
