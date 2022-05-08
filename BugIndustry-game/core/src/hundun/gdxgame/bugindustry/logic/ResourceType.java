package hundun.gdxgame.bugindustry.logic;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Arrays;
import java.util.List;

public class ResourceType {
    public static final String COIN = "ENUM_RESC@COIN";
    public static final String WOOD = "ENUM_RESC@WOOD";
    public static final String WOOD_BOARD = "ENUM_RESC@HARD_WOOD";
    public static final String HONEY = "ENUM_RESC@HONEY";
    public static final String BEEWAX = "ENUM_RESC@BEEWAX";
    public static final String QUEEN_BEE = "ENUM_RESC@QUEEN_BEE";
    public static final String BEE = "ENUM_RESC@BEE";
    public static final String WIN_TROPHY = "ENUM_RESC@TROPHY";
    ;
    public static final List<String> VALUES_FOR_SHOW_ORDER = Arrays.asList(COIN, BEE, WOOD, WOOD_BOARD, HONEY, BEEWAX, QUEEN_BEE);
    
}
