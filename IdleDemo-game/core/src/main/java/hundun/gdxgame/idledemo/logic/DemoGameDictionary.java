package hundun.gdxgame.idledemo.logic;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class DemoGameDictionary {


    public List<String> getMenuScreenTexts(Language language) {
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList("Idle样例", "新游戏", "继续游戏", "语言", "重启后生效");
            default:
                return JavaFeatureForGwt.arraysAsList("IdleDemo", "New Game", "Continue", "Language", "Take effect after restart");
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
                        "当前任务：", "已完成：", "回到游戏", "奖励内容：", "领取",
                        "无"
                );
            default:
                return JavaFeatureForGwt.listOf(
                        "Quest: ", "Completed: ", "back", "reward: ", "Get it",
                        "None"
                );
        }
    }


    public List<String> getStageSelectMaskBoardTexts(Language language)
    {
        switch (language)
        {
            case CN:
                return JavaFeatureForGwt.arraysAsList("返回", "关卡1", "关卡2", "关卡3");
            default:
                return JavaFeatureForGwt.arraysAsList("Back", "Stage1", "Stage2", "Stage3");
        }
    }
}
