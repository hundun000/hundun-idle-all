package hundun.gdxgame.idledemo.logic;

import hundun.gdxgame.idleframe.util.IGameDictionary;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class GameDictionary implements IGameDictionary {

    
    public String constructionIdToShowName(String constructionId) {
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
                return "[dic:" + constructionId + "]";
        }
    }
}
