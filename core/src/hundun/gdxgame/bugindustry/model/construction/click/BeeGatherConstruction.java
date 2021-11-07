package hundun.gdxgame.bugindustry.model.construction.click;

import java.util.Arrays;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class BeeGatherConstruction extends BaseClickGatherConstruction {

    public BeeGatherConstruction(BugIndustryGame game) {
        super(game, "BEE_00");
        this.name = getClass().getSimpleName();
        this.detailDescroptionConstPart = "Free gain worker-bee";
        this.baseOutputRules = Arrays.asList(
                new ConstructionOuputRule(ResourceType.WORKER_BEE, 1)
                );
        updateCurrentCache();
    }
}
