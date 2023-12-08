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

public class IdleMushroomAchievementLoader implements IBuiltinAchievementsLoader {

    public static Map<String, Integer> achievementSortWeightMap = JavaFeatureForGwt.mapOf(
            MushroomAchievementId.STEP_1, 1,
            MushroomAchievementId.STEP_2, 2,
            MushroomAchievementId.STEP_3, 3,
            MushroomAchievementId.STEP_4, 4
            );

    IdleMushroomGameDictionary idleMushroomGameDictionary;
    public IdleMushroomAchievementLoader(IdleMushroomGameDictionary idleMushroomGameDictionary) {
        this.idleMushroomGameDictionary = idleMushroomGameDictionary;
    }

    @Override
    public Map<String, AbstractAchievement> getProviderMap(Language language) {
        Map<String, List<String>> textMap = new HashMap<>();
        switch (language)
        {
            case CN:
                textMap.put(MushroomAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                        "NO.1",
                        "拥有2个等级1的{PrototypeName}。",
                        "你完成了任务NO.1。",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(MushroomAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                        "NO.2",
                        "拥有2个等级2的{PrototypeName}。",
                        "你完成了任务NO.2。",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(MushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "{PrototypeName}达到等级2。",
                        "你完成了任务NO.3。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(MushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        "{PrototypeName}达到等级3。",
                        "你完成了任务NO.4。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                break;
            default:
                textMap.put(MushroomAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own two lv.1 {PrototypeName}.",
                        "You completed Quest NO.1.",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(MushroomAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                "NO.2",
                        "Own two lv.2 {PrototypeName}.",
                        "You completed Quest NO.2.",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(MushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "Into 2nd {PrototypeName}.",
                        "You completed Quest NO.3.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(MushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        "Into 3rd {PrototypeName}.",
                        "You completed Quest NO.4.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                break;
        }



        Map<String, AbstractAchievement> map = new HashMap<>();
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(2, 1)
                ),
                JavaFeatureForGwt.listOf(MushroomAchievementId.STEP_2),
                new ResourcePair(ResourceType.MUSHROOM, 50L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_2,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(2, 2)
                ),
                JavaFeatureForGwt.listOf(MushroomAchievementId.STEP_3),
                new ResourcePair(ResourceType.MUSHROOM, 100L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_3,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, 2)
                ),
                JavaFeatureForGwt.listOf(MushroomAchievementId.STEP_4),
                new ResourcePair(ResourceType.MUSHROOM, 200L)
        );
        OwnConstructionAchievement.Companion.quickAddOwnConstructionAchievement(
                map,
                MushroomAchievementId.STEP_4,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, 3)
                ),
                null,
                new ResourcePair(ResourceType.DNA_POINT, 1000L)
        );
        return map;
    }
}
