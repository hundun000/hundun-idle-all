package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import lombok.Getter;

public enum ResourceType {
    COIN("coin"),
    WOOD("wood"),
    HARD_WOOD("hard-wood"),
    HONEY("honey"),
    BEEWAX("beewax"),
    QUEEN_BEE("queen-bee"),
    BEE("bee"),
    WIN_THE_GAME("win"),
    ;
    @Getter
    private final String showName;

    private ResourceType(String showName) {
        this.showName = showName;
    }
    
}
