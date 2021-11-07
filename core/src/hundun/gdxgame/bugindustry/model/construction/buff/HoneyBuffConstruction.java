package hundun.gdxgame.bugindustry.model.construction.buff;

import java.util.Arrays;
import java.util.Map;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class HoneyBuffConstruction extends BaseBuffConstruction {

    public HoneyBuffConstruction(BugIndustryGame game) {
        super(game, BuffId.BUFF_HONEY, "BEE_BUFF_0");
        this.name = getClass().getSimpleName();
        this.detailDescroptionConstPart = "speed up gain honey.";
        this.baseUpgradeCostMap = (
                Map.of(
                        ResourceType.GENE_POINT, 1
                        )
                );
    }

}
