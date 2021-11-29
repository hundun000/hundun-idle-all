package hundun.gdxgame.bugindustry.model;

import hundun.gdxgame.bugindustry.model.construction.ConstructionId;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class GameDictionary {

    
    public String constructionIdToShowName(ConstructionId constructionId) {
        switch (constructionId) {
        case WOOD_GATHER_HOUSE:
            return "free wood";
        case WOOD_AUTO_CUTTER:
            return "feller";
        case BEE_GATHER_HOUSE:
            return "free bee";
        case SMALL_BEEHIVE:
            return "small beehive";
        case HONEY_SELL_HOUSE:
            return "honey selling";
        case HONEY_BUFF_PROVIDER:
            return "honey gene";
        default:
            return "[dic:" + constructionId.name() + "]";
        }
    }
}
