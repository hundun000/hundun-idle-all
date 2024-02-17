package hundun.gdxgame.idlemushroom.logic.loader;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.IdleMushroomExtraGameplayExport;
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

    public static Map<String, Integer> achievementSortWeightMap = new HashMap<>();
    static {
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_1, 1);
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_2, 2);
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_3, 3);
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_4, 4);
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_5, 5);
        achievementSortWeightMap.put(IdleMushroomAchievementId.STEP_6, 6);
    }


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
                        JavaFeatureForGwt.stringFormat("{PrototypeName}达到等级%s。", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_0),
                        "你完成了任务NO.2。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                        "NO.3",
                        "拥有2个等级2的{PrototypeName}。",
                        "你完成了任务NO.3。",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        JavaFeatureForGwt.stringFormat("{PrototypeName}达到等级%s。", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_1),
                        "你完成了任务NO.4。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_5, JavaFeatureForGwt.listOf(
                        "NO.5",
                        JavaFeatureForGwt.stringFormat("{PrototypeName}达到等级%s。", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_2),
                        "你完成了任务NO.5。",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_6, JavaFeatureForGwt.listOf(
                        "NO.6",
                        JavaFeatureForGwt.stringFormat("{PrototypeName}达到等级%s。", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_MAX_LEVEL),
                        "你完成了任务NO.6。",
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
                        JavaFeatureForGwt.stringFormat("Into lv.%s {PrototypeName}.", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_0),
                        "You completed Quest NO.2.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_3, JavaFeatureForGwt.listOf(
                "NO.3",
                        "Own two lv.2 {PrototypeName}.",
                        "You completed Quest NO.3.",
                        IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_4, JavaFeatureForGwt.listOf(
                        "NO.4",
                        JavaFeatureForGwt.stringFormat("Into lv.%s {PrototypeName}.", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_1),
                        "You completed Quest NO.4.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_5, JavaFeatureForGwt.listOf(
                        "NO.5",
                        JavaFeatureForGwt.stringFormat("Into lv.%s {PrototypeName}.", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_2),
                        "You completed Quest NO.5.",
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER
                ));
                textMap.put(IdleMushroomAchievementId.STEP_6, JavaFeatureForGwt.listOf(
                        "NO.6",
                        JavaFeatureForGwt.stringFormat("Into lv.%s {PrototypeName}.", IdleMushroomExtraGameplayExport.EPOCH_COUNTER_MAX_LEVEL),
                        "You completed Quest NO.6.",
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
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_0)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_3),
                new ResourcePair(ResourceType.MUSHROOM, 200L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_3,
                textMap,
                JavaFeatureForGwt.mapOf(
                        EPOCH_ANY_MUSHROOM_AUTO_PROVIDER, new SimpleEntry<>(2, 2)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_4),
                new ResourcePair(ResourceType.MUSHROOM, 400L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_4,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_1)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_5),
                new ResourcePair(ResourceType.MUSHROOM, 1600L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_5,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, IdleMushroomExtraGameplayExport.EPOCH_COUNTER_SPECIAL_LEVEL_2)
                ),
                JavaFeatureForGwt.listOf(IdleMushroomAchievementId.STEP_6),
                new ResourcePair(ResourceType.MUSHROOM, 6400L)
        );
        OwnConstructionAchievementPrototype.Companion.quickAddOwnConstructionAchievement(
                map,
                IdleMushroomAchievementId.STEP_6,
                textMap,
                JavaFeatureForGwt.mapOf(
                        IdleMushroomConstructionPrototypeId.EPOCH_COUNTER, new SimpleEntry<>(1, IdleMushroomExtraGameplayExport.EPOCH_COUNTER_MAX_LEVEL)
                ),
                null,
                new ResourcePair(ResourceType.MUSHROOM, 1000000L)
        );
        return map;
    }
}
