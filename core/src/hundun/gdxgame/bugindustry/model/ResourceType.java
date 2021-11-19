package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import lombok.Getter;

public enum ResourceType {
    COIN("coin"),
    WOOD("wood"),
    HONEY("[GOLD]honey"),
    BEE("woker-bee"),
    GENE_POINT("gene-point"),
    ;
    @Getter
    private final String showName;

    private ResourceType(String showName) {
        this.showName = showName;
    }
    
}
