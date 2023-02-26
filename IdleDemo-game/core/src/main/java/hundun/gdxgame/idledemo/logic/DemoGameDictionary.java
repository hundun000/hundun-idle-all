package hundun.gdxgame.idledemo.logic;

import java.util.List;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class DemoGameDictionary implements IGameDictionary {

    
    public String constructionIdToShowName(Language language, String constructionId) {
        switch (language) {
            case CN:
                switch (constructionId) {
                    case ConstructionId.COOKIE_CLICK_PROVIDER:
                        return "大饼干";
                    case ConstructionId.COOKIE_AUTO_PROVIDER:
                        return "自动点击器";
                    case ConstructionId.COOKIE_SELLER:
                        return "自动销售器";
                    case ConstructionId.WIN_PROVIDER:
                        return "奖杯购买处";
                    default:
                        return "口口";
                }
            default:
                switch (constructionId) {
                    case ConstructionId.COOKIE_CLICK_PROVIDER:
                        return "main cookie";
                    case ConstructionId.COOKIE_AUTO_PROVIDER:
                        return "cliker";
                    case ConstructionId.COOKIE_SELLER:
                        return "seller";
                    case ConstructionId.WIN_PROVIDER:
                        return "win";
                    default:
                        return "[dic lost]";
                }
        }
        
        
    }

    @Override
    public String constructionIdToDetailDescroptionConstPart(Language language, String constructionId) {
        switch (language) {
            case CN:
                switch (constructionId) { 
                    case ConstructionId.COOKIE_CLICK_PROVIDER:
                        return "戳一戳，获得饼干";
                    case ConstructionId.COOKIE_AUTO_PROVIDER:
                        return "自动获得饼干";
                    case ConstructionId.COOKIE_SELLER:
                        return "自动获得饼干";
                    case ConstructionId.WIN_PROVIDER:
                        return "购买一个奖杯以赢得胜利";
                    default:
                        return "[dic lost]";
                }
            default:
                switch (constructionId) {
                    case ConstructionId.COOKIE_CLICK_PROVIDER:
                        return "Click gain some cookie";
                    case ConstructionId.COOKIE_AUTO_PROVIDER:
                        return "Auto gain some cookie";
                    case ConstructionId.COOKIE_SELLER:
                        return "Auto sell cookies";
                    case ConstructionId.WIN_PROVIDER:
                        return "Get a trophy and win the game";
                    default:
                        return "[dic lost]";
                }
        }
        

    }

    @Override
    public List<String> getMemuScreenTexts(Language language) {
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList("Idle样例", "新游戏", "继续游戏");
            default:
                return JavaFeatureForGwt.arraysAsList("Demo游戏", "新游戏", "继续游戏");
        }
    }
}
