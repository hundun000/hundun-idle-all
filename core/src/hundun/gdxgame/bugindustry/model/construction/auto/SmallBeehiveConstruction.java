package hundun.gdxgame.bugindustry.model.construction.auto;

import java.util.Arrays;
import java.util.Map;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/06
 */
public class SmallBeehiveConstruction extends BaseAutoConstruction {
    
    public SmallBeehiveConstruction(BugIndustryGame game) {
        super(game, "BEE_01");
        this.name = getClass().getSimpleName();
        this.detailDescroptionConstPart = "Auto gain some honey";
        this.baseOutputRules = Arrays.asList(
                new ConstructionOuputRule(ResourceType.HONEY, 1)
                );
        this.baseUpgradeCostMap = (
                Map.of(
                        ResourceType.WOOD, 300, 
                        ResourceType.WORKER_BEE, 3
                        )
                );
    }
    
    @Override
    public void onClick() {
        super.onClick();
    }
}
