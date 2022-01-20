package hundun.gdxgame.bugindustry.logic;

import hundun.gdxgame.idleframe.util.IGameDictionary;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class BugGameDictionary implements IGameDictionary {

    
    public String constructionIdToShowName(String constructionId) {
        switch (constructionId) {
        
            case ConstructionId.WOOD_GATHER_HOUSE:
                return "free wood";
            case ConstructionId.WOOD_KEEPING:
                return "plate tree";
            case ConstructionId.WOOD_BOARD_MAKER:
                return "board maker";
            case ConstructionId.WIN_THE_GAME:
                return "win goal";
                
            case ConstructionId.BEE_GATHER_HOUSE:
                return "free bee";
            case ConstructionId.SMALL_BEEHIVE:
                return "small beehive";
            case ConstructionId.MID_BEEHIVE:
                return "mid beehive";
            case ConstructionId.BIG_BEEHIVE:
                return "big beehive";
            case ConstructionId.QUEEN_BEEHIVE:
                return "queen beehive"; 
            
            case ConstructionId.WOOD_SELL_HOUSE:
                return "sell wood";
            case ConstructionId.WOOD_BOARD_SELL_HOUSE:
                return "sell board";
            case ConstructionId.BEE_SELL_HOUSE:
                return "sell bee";    
            case ConstructionId.HONEY_SELL_HOUSE:
                return "sell honey";
            case ConstructionId.BEEWAX_SELL_HOUSE:
                return "sell beewax";
    
            default:
                return "[dic:" + constructionId + "]";
        }
    }
}
