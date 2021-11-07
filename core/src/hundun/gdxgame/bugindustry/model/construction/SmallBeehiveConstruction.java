package hundun.gdxgame.bugindustry.model.construction;

import java.util.Arrays;
import java.util.Map;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/06
 */
public class SmallBeehiveConstruction extends BaseConstruction {
    
    public SmallBeehiveConstruction(BugIndustryGame game) {
        super(game);
        this.name = getClass().getSimpleName();
        this.detailDescroption = "output 1 honey per frame";
        this.autoOutputRules = Arrays.asList(
                new ConstructionOuputRule(ResourceType.HONEY, 1, 100)
                );
        this.upgradeCostRules.add(
                Map.of(
                        ResourceType.WOOD, 300, 
                        ResourceType.WORKER_BEE, 3
                        )
                );
        this.upgradeCostRules.add(
                Map.of(
                        ResourceType.WOOD, 800, 
                        ResourceType.WORKER_BEE, 8
                        )
                );
    }
    
    @Override
    public void onClick() {
        super.onClick();
    }
}
