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
                return "provider0";
            case ConstructionId.COOKIE_AUTO_PROVIDER:
                return "provider1";
            case ConstructionId.COOKIE_SELLER:
                return "provider2";
            case ConstructionId.WIN_PROVIDER:
                return "provider3";

            default:
                return "[dic:" + constructionId + "]";
        }
    }
}
