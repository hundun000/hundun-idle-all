package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import lombok.Getter;

public enum ResourceType {
    COIN("coin"),
    WOOD("wood"),
    WOOD_BOARD("hard-wood"),
    HONEY("honey"),
    BEEWAX("beewax"),
    QUEEN_BEE("queen-bee"),
    BEE("bee"),
    WIN_TROPHY("win"),
    ;
    @Getter
    private final String showName;

    private ResourceType(String showName) {
        this.showName = showName;
    }
    
}
