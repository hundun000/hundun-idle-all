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
    GOOD_HONEY("good-honey"),
    BERTTER_HONEY("better-honey"),
    BEST_HONEY("best-honey"),
    BEE("bee"),
    ;
    @Getter
    private final String showName;

    private ResourceType(String showName) {
        this.showName = showName;
    }
    
}
