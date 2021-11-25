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
            return "hand gather";
        case WOOD_AUTO_CUTTER:
            return "feller";
        case BEE_GATHER_HOUSE:
            return "hand gather";
        case SMALL_BEEHIVE:
            return "feller";
        case HONEY_SELL_HOUSE:
            return "honey selling";
        case HONEY_BUFF_PROVIDER:
            return "honey gene";
        default:
            return "[dic:" + constructionId.name() + "]";
        }
    }
}
