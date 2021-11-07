package hundun.gdxgame.bugindustry.model.construction;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class BeeGatherConstruction extends BaseConstruction {

    public BeeGatherConstruction(BugIndustryGame game) {
        super(game);
        this.name = getClass().getSimpleName();
        this.buttonDescroption = "Gather";
        this.detailDescroption = "Gather 1 worker-bee";
        this.clickGatherOutputRule = new ConstructionOuputRule(ResourceType.WORKER_BEE, 1, 100);
    }

}
