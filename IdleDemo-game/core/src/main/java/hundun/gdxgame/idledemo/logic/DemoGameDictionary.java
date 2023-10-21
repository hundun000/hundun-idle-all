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
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER:
                        return "点击器";
                    case DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER:
                        return "高级自动点击器";
                    case DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER:
                        return "自动点击器";
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER:
                        return "自动出售器";
                    default:
                        return "口口";
                }
            default:
                switch (constructionId) {
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_MAIN_CLICKER:
                        return "Clicker";
                    case DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER:
                        return "ComplexAutoClicker";
                    case DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER:
                        return "SimpleAutoClicker";
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER:
                        return "AutoSeller";
                    default:
                        return "[dic lost]";
                }
        }
        
        
    }

    @Override
    public String constructionPrototypeIdToDetailDescriptionConstPart(Language language, String constructionId) {
        switch (language) {
            case CN:
                switch (constructionId) {
                    case DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER:
                    case DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER:
                        return "自动获得饼干";
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER:
                        return "自动出售饼干";
                    case DemoConstructionPrototypeId.EMPTY_CELL:
                        return "空位置";
                    default:
                        return "[dic lost]";
                }
            default:
                switch (constructionId) {
                    case DemoConstructionPrototypeId.COOKIE_COMPLEX_AUTO_PROVIDER:
                    case DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER:
                        return "Auto gain some cookie";
                    case DemoConstructionPrototypeId.SINGLETON_COOKIE_AUTO_SELLER:
                        return "Auto sell some cookie";
                    case DemoConstructionPrototypeId.EMPTY_CELL:
                        return "Empty";
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
