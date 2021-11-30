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
            case WOOD_KEEPING:
                return "plate tree";
            case WOOD_BOARD_MAKER:
                return "board maker";
            case WIN_THE_GAME:
                return "win goal";
                
            case BEE_GATHER_HOUSE:
                return "free bee";
            case SMALL_BEEHIVE:
                return "small beehive";
            case MID_BEEHIVE:
                return "mid beehive";
            case BIG_BEEHIVE:
                return "big beehive";
            case QUEEN_BEEHIVE:
                return "queen beehive"; 
            
            case WOOD_SELL_HOUSE:
                return "sell wood";
            case WOOD_BOARD_SELL_HOUSE:
                return "sell board";
            case BEE_SELL_HOUSE:
                return "sell bee";    
            case HONEY_SELL_HOUSE:
                return "sell honey";
            case BEEWAX_SELL_HOUSE:
                return "sell beewax";
    
            default:
                return "[dic:" + constructionId.name() + "]";
        }
    }
}
