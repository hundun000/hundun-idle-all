package hundun.gdxgame.idledemo.logic;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class DemoGameDictionary implements IGameDictionary {

    
    public String constructionPrototypeIdToShowName(Language language, String constructionId) {
        switch (language) {
            case CN:
                switch (constructionId) {

                    case ConstructionPrototypeId.COOKIE_AUTO_PROVIDER:
                        return "自动点击器";
                    default:
                        return "口口";
                }
            default:
                switch (constructionId) {
                    case ConstructionPrototypeId.COOKIE_AUTO_PROVIDER:
                        return "cliker";
                    default:
                        return "[dic lost]";
                }
        }
        
        
    }

    @Override
    public String constructionPrototypeIdToDetailDescroptionConstPart(Language language, String constructionId) {
        switch (language) {
            case CN:
                switch (constructionId) {
                    case ConstructionPrototypeId.COOKIE_AUTO_PROVIDER:
                        return "自动获得饼干";
                    default:
                        return "[dic lost]";
                }
            default:
                switch (constructionId) {
                    case ConstructionPrototypeId.COOKIE_AUTO_PROVIDER:
                        return "Auto gain some cookie";
                    default:
                        return "[dic lost]";
                }
        }
        

    }

    @Override
    public List<String> getMemuScreenTexts(Language language) {
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
                return JavaFeatureForGwt.arraysAsList("当前任务：", "已完成：", "回到游戏");
            default:
                return JavaFeatureForGwt.arraysAsList("Quest: ", "Completed: ", "back");
        }
    }

    @Override
    public List<String> getPlayScreenTexts(Language language)
    {
        switch (language)
        {
            case CN:
                return JavaFeatureForGwt.arraysAsList("购买", "购买费用");
            default:
                return JavaFeatureForGwt.arraysAsList("Build", "Build cost");
        }
    }

    @Override
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
