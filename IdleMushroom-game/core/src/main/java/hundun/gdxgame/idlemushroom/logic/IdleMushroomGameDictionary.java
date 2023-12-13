package hundun.gdxgame.idlemushroom.logic;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class IdleMushroomGameDictionary {



    public List<String> getMenuScreenTexts(Language language) {
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList("放置蘑菇", "新游戏", "继续游戏", "语言", "重启后生效");
            default:
                return JavaFeatureForGwt.arraysAsList("Idle Mushroom", "New Game", "Continue", "Language", "Take effect after restart");
        }
    }


    public Map<Language, String> getLanguageShowNameMap() {
        return JavaFeatureForGwt.mapOf(
                Language.CN, "中文",
                Language.EN, "English"
                );
    }


    public List<String> getAchievementTexts(Language language)
    {
        switch (language)
        {
            case CN:
                return JavaFeatureForGwt.listOf(
                        "任务：", "状态：", "回到游戏", "奖励内容：", "领取",
                        "无"
                );
            default:
                return JavaFeatureForGwt.listOf(
                        "Quest: ", "status: ", "back", "reward: ", "Get it",
                        "None"
                );
        }
    }


    public List<String> getStageSelectMaskBoardTexts(Language language)
    {
        return null;
    }

    public String achievementStatus(Language language, AchievementState state) {
        switch (language) {
            case CN:
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
            default:
                switch (state) {
                    case LOCKED:
                        return "locked";
                    case RUNNING:
                        return "in progress";
                    case COMPLETED:
                        return "completed";
                    default:
                        return "[dic lost]";
                }
        }
    }
}
