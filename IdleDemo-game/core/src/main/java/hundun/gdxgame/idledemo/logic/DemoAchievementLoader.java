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
                        "拥有两个小森林。",
                        "你完成了任务NO.1。"
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "拥有两个小工厂。",
                            "你完成了任务NO.2。"
                ));
                break;
            default:
                textMap.put(DemoAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own two small forests.",
                        "You completed Quest NO.1.\nThank you for your tireless efforts to protect our planet and make it a better place for future generations."
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "Own two small factories.",
                            "You completed Quest NO.2.\nWe are thrilled to see the positive impact on our local economy with more jobs and increased business opportunities. Thank you to all who have contributed to our economic success."
                ));
                break;
        }



        Map<String, AbstractAchievement> map = new HashMap<>();
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                DemoAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        ConstructionPrototypeId.COOKIE_AUTO_SELLER, new SimpleEntry<>(1, 2)
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
