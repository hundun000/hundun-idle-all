package hundun.gdxgame.idledemo.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.IAchievementPrototypeLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.OwnConstructionAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoAchievementLoader implements IAchievementPrototypeLoader {
    @Override
    public Map<String, AbstractAchievementPrototype> getProviderMap(Language language) {
        Map<String, List<String>> textMap = new HashMap<>();
        switch (language)
        {
            case CN:
                textMap.put(DemoAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "拥有2级{PrototypeName}。",
                        "你完成了任务NO.1。",
                        DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "拥有2级{PrototypeName}。",
                            "你完成了任务NO.2。",
                        DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER
                ));
                break;
            default:
                textMap.put(DemoAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own lv.2 {PrototypeName}.",
                        "You completed Quest NO.1.",
                        DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER
                ));
                textMap.put(DemoAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                        "Own lv.2 {PrototypeName}.",
                        "You completed Quest NO.2.",
                        DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER
                ));
                break;
        }



        Map<String, AbstractAchievementPrototype> map = new HashMap<>();
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                DemoAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER, new SimpleEntry<>(1, 2)
                ),
                JavaFeatureForGwt.listOf(DemoAchievementId.STEP_2),
                new ResourcePair(ResourceType.COIN, 1000L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                DemoAchievementId.STEP_2,
                textMap,
                JavaFeatureForGwt.mapOf(
                        DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER, new SimpleEntry<>(1, 2)
                ),
                null,
                new ResourcePair(ResourceType.COIN, 1000L)
        );
        return map;
    }
}
