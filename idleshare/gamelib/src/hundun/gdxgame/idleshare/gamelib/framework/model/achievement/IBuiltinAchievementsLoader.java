package hundun.gdxgame.idleshare.gamelib.framework.model.achievement;

import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.Map;

public interface IBuiltinAchievementsLoader {
    Map<String, AbstractAchievement> getProviderMap(Language language);
}