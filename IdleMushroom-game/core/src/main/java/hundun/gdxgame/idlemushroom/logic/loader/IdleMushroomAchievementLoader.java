package hundun.gdxgame.idlemushroom.logic.loader;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.IdleMushroomGameDictionary;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomAchievementId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.IAchievementPrototypeLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.OwnConstructionAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdleMushroomAchievementLoader implements IAchievementPrototypeLoader {

    public static Map<String, Integer> achievementSortWeightMap = JavaFeatureForGwt.mapOf(
            IdleMushroomAchievementId.STEP_1, 1,
            IdleMushroomAchievementId.STEP_2, 2,
            IdleMushroomAchievementId.STEP_3, 3,
            IdleMushroomAchievementId.STEP_4, 4
            );

    IdleMushroomGameDictionary idleMushroomGameDictionary;
    public IdleMushroomAchievementLoader(IdleMushroomGameDictionary idleMushroomGameDictionary) {
        this.idleMushroomGameDictionary = idleMushroomGameDictionary;
    }

    @Override
    public Map<String, AbstractAchievementPrototype> getProviderMap(Language language) {
        Map<String, List<String>> textMap = new HashMap<>();
        switch (language)
        {
            case CN:
                textMap.put(IdleMushroomAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                        "NO.1",
                        "拥有2个等级1的{PrototypeName}。",
                        "你完成了任务NO.1。",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                        "NO.2",
                        "拥有2个等级2的{PrototypeName}。",
                        "你完成了任务NO.2。",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "{PrototypeName}达到等级2。",
                        "你完成了任务NO.3。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        "{PrototypeName}达到等级3。",
                        "你完成了任务NO.4。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                break;
            default:
                textMap.put(IdleMushroomAchievementId.STEP_1, JavaFeatureForGwt.listOf(
                "NO.1",
                        "Own two lv.1 {PrototypeName}.",
                        "You completed Quest NO.1.",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_2, JavaFeatureForGwt.listOf(
                "NO.2",
                        "Own two lv.2 {PrototypeName}.",
                        "You completed Quest NO.2.",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "Into 2nd {PrototypeName}.",
                        "You completed Quest NO.3.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        "Into 3rd {PrototypeName}.",
                        "You completed Quest NO.4.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                break;
        }



        Map<String, AbstractAchievementPrototype> map = new HashMap<>();
        final String EPOCH_ANY_MUSHROOM_AUTO_PROVIDER = IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                + "|" + IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER
                + "|" + IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER ;;
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_1,
                textMap,
                JavaFeatureForGwt.mapOf(
                        EPOCH_ANY_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(2, 1)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_2),
                new ResourcePair(ResourceType.MUSHROOM, 50L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_2,
                textMap,
                JavaFeatureForGwt.mapOf(
                        EPOCH_ANY_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(2, 2)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_3),
                new ResourcePair(ResourceType.MUSHROOM, 100L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_3,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, 2)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_4),
                new ResourcePair(ResourceType.MUSHROOM, 200L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_4,
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
