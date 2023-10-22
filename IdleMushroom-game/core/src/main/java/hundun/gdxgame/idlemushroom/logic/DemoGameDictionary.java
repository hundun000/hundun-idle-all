package hundun.gdxgame.idlemushroom.logic;

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
            default:
                switch (constructionId) {

                    case DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
                    case DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
                    case DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER:
                        return "自动点击器";
                    case DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
                    case DemoConstructionPrototypeId.EPOCH_2_EMPTY_CELL:
                    case DemoConstructionPrototypeId.EPOCH_3_EMPTY_CELL:
                        return "空地";
                    case DemoConstructionPrototypeId.EPOCH_1_TREE:
                        return "灌木丛";
                    case DemoConstructionPrototypeId.EPOCH_2_TREE:
                        return "树";
                    case DemoConstructionPrototypeId.EPOCH_3_TREE:
                        return "森林";
                    case DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER:
                        return "自动出售器";
                    case DemoConstructionPrototypeId.EPOCH_COUNTER:
                        return "EPOCH_COUNTER";
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
                    case DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
                        return "EPOCH_1自动获得饼干";
                    case DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
                        return "EPOCH_2自动获得饼干";
                    case DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER:
                        return "EPOCH_3自动获得饼干";
                    case DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER:
                        return "自动出售饼干";
                    case DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
                        return "空位置";
                    case DemoConstructionPrototypeId.EPOCH_COUNTER:
                        return "the EPOCH_COUNTER";
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
