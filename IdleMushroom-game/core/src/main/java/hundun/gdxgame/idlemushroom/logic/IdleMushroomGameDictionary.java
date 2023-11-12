package hundun.gdxgame.idlemushroom.logic;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class IdleMushroomGameDictionary implements IGameDictionary {

    
    public String constructionPrototypeIdToShowName(Language language, String constructionId) {
        switch (language) {
            default:
                switch (constructionId) {

                    case IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
                    case IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
                    case IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER:
                        return "蘑菇地块";
                    case IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
                    case IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL:
                    case IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL:
                        return "空地块";
                    case IdleMushroomConstructionPrototypeId.EPOCH_1_TREE:
                    case IdleMushroomConstructionPrototypeId.EPOCH_2_TREE:
                    case IdleMushroomConstructionPrototypeId.EPOCH_3_TREE:
                        return "树木地块";
                    case IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER:
                        return "科研中心";
                    case IdleMushroomConstructionPrototypeId.EPOCH_COUNTER:
                        return "巨大化";
                    default:
                        return "[dic lost]";
                }
        }
        
        
    }

    @Override
    public String constructionPrototypeIdToDetailDescriptionConstPart(Language language, String constructionId) {
        switch (language) {
            default:
                switch (constructionId) {
                    case IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
                    case IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
                    case IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER:
                        return constructionPrototypeIdToShowName(language, constructionId) + "：\n" +
                                "•定时生产蘑菇资源。\n" +
                                "•成长度满时可以升级，升级后成长度重置。\n" +
                                "•成长速度与周围地块有关，树木有利于成长，存在其他蘑菇地块不利于成长。";
                    case IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
                        return constructionPrototypeIdToShowName(language, constructionId) + "：\n" +
                                "•可转变为其他地块。";
                    case IdleMushroomConstructionPrototypeId.EPOCH_1_TREE:
                    case IdleMushroomConstructionPrototypeId.EPOCH_2_TREE:
                    case IdleMushroomConstructionPrototypeId.EPOCH_3_TREE:
                        return constructionPrototypeIdToShowName(language, constructionId) + "：\n" +
                                "•利于周围蘑菇地块的成长。";
                    case IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER:
                        return constructionPrototypeIdToShowName(language, constructionId) + "：\n" +
                                "•定时消耗蘑菇资源，生产基因点数。\n" +
                                "•可以调整启用的等级。";
                    case IdleMushroomConstructionPrototypeId.EPOCH_COUNTER:
                        return constructionPrototypeIdToShowName(language, constructionId) + "：\n" +
                                "•可消耗基因点数，进行一次巨大化。\n" +
                                "•巨大化时你将失去所有原有蘑菇地块（因为他们相对而言已经太小了）。";
                    default:
                        return "[dic lost]";
                }
        }
        

    }

    @Override
    public List<String> getMenuScreenTexts(Language language) {
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList("Idle样例", "新游戏", "继续游戏", "语言", "重启后生效");
            default:
                return JavaFeatureForGwt.arraysAsList("IdleDemo", "New Game", "Continue", "Language", "Take effect after restart");
        }
    }

    @Override
    public Map<Language, String> getLanguageShowNameMap() {
        return JavaFeatureForGwt.mapOf(
                Language.CN, "中文",
                Language.EN, "English"
                );
    }

    @Override
    public List<String> getAchievementTexts(Language language)
    {
        switch (language)
        {
            case CN:
                return JavaFeatureForGwt.listOf(
                        "当前任务：", "状态：", "回到游戏", "奖励内容：", "领取",
                        "无"
                );
            default:
                return JavaFeatureForGwt.listOf(
                        "Quest: ", "status: ", "back", "reward: ", "Get it",
                        "None"
                );
        }
    }

    @Override
    public List<String> getStageSelectMaskBoardTexts(Language language)
    {
        return null;
    }

    public String achievementStatus(Language language, AchievementState state) {
        switch (language) {
            default:
                switch (state) {
                    case LOCKED:
                        return "未解锁";
                    case RUNNING:
                        return "进行中";
                    case COMPLETED:
                        return "已完成";
                    default:
                        return "[dic lost]";
                }
        }
    }
}
