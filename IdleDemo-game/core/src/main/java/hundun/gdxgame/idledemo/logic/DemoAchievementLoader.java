package hundun.gdxgame.idledemo.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.IBuiltinAchievementsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.OwnConstructionAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoAchievementLoader implements IBuiltinAchievementsLoader {
    @Override
    public Map<String, AbstractAchievement> getProviderMap(Language language) {
        Map<String, List<String>> textMap = new HashMap<>();
        switch (language)
        {
            case CN:
                textMap.put(DemoAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "拥有2级AUTO_PROVIDER。",
                        "你完成了任务NO.1。"
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "拥有2级AUTO_SELLER。",
                            "你完成了任务NO.2。"
                ));
                break;
            default:
                textMap.put(DemoAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own lv.2 AUTO_PROVIDER.",
                        "You completed Quest NO.1."
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "Own lv.2 AUTO_SELLER.",
                            "You completed Quest NO.2."
                ));
                break;
        }



        Map<String, AbstractAchievement> map = new HashMap<>();
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                DemoAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        ConstructionPrototypeId.COOKIE_AUTO_PROVIDER, new SimpleEntry<>(1, 2)
                )
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                DemoAchievementId.STEP_2,
                textMap,
                JavaFeatureForGwt.mapOf(
                        ConstructionPrototypeId.COOKIE_AUTO_SELLER, new SimpleEntry<>(1, 2)
                )
        );
        return map;
    }
}
