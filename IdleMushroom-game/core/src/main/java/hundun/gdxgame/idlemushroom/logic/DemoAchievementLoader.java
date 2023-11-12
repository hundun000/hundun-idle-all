package hundun.gdxgame.idlemushroom.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.IBuiltinAchievementsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.OwnConstructionAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
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
            default:
                textMap.put(MushroomAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own lv.2 AUTO_PROVIDER.",
                        "You completed Quest NO.1."
                ));
                textMap.put(MushroomAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                    "NO.2",
                            "Own lv.2 AUTO_SELLER.",
                            "You completed Quest NO.2."
                ));
                textMap.put(MushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "Own lv.2 AUTO_SELLER.",
                        "You completed Quest NO.2."
                ));
                textMap.put(MushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        "Own lv.2 AUTO_SELLER.",
                        "You completed Quest NO.2."
                ));
                break;
        }



        Map<String, AbstractAchievement> map = new HashMap<>();
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(1, 2)
                ),
                JavaFeatureForGwt.listOf(MushroomAchievementId.STEP_2, MushroomAchievementId.STEP_3, MushroomAchievementId.STEP_4),
                new ResourcePair(ResourceType.DNA_POINT, 1000L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_2,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER, new SimpleEntry<>(1, 2)
                ),
                null,
                new ResourcePair(ResourceType.DNA_POINT, 1000L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_3,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER, new SimpleEntry<>(1, 2)
                ),
                null,
                new ResourcePair(ResourceType.DNA_POINT, 1000L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_4,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER, new SimpleEntry<>(1, 2)
                ),
                null,
                new ResourcePair(ResourceType.DNA_POINT, 1000L)
        );
        return map;
    }
}
