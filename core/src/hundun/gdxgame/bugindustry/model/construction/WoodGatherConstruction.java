package hundun.gdxgame.bugindustry.model.construction;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WoodGatherConstruction extends BaseConstruction {

    public WoodGatherConstruction(BugIndustryGame game) {
        super(game);
        this.gatherDescroption = "Gather wood";
        this.clickGatherOutputRule = new ConstructionOuputRule(100, ResourceType.WOOD, 100);
    }

}
