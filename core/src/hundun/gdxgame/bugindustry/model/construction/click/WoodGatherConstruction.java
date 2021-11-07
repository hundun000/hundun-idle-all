package hundun.gdxgame.bugindustry.model.construction.click;

import java.util.Arrays;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WoodGatherConstruction extends BaseClickGatherConstruction {

    public WoodGatherConstruction(BugIndustryGame game) {
        super(game, "FOREST_00");
        this.name = getClass().getSimpleName();
        this.detailDescroptionConstPart = "Free gain some wood.";
        this.baseOutputRules = Arrays.asList(
                new ConstructionOuputRule(ResourceType.WOOD, 100)
                );
    }

}
