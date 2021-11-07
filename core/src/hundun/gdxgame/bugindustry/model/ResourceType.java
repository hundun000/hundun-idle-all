package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import lombok.Getter;

public enum ResourceType {
    WOOD("wood"),
    HONEY("[GOLD]honey"),
    WORKER_BEE("woker-bee"),
    GENE_POINT("gene-point"),
    ;
    @Getter
    private final String showName;

    private ResourceType(String showName) {
        this.showName = showName;
    }
    
}
