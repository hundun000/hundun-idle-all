package hundun.gdxgame.bugindustry.model.construction;

import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseClickGatherConstruction extends BaseConstruction {

    public BaseClickGatherConstruction(BugIndustryGame game, ConstructionId id) {
        super(game, ConstructionType.CLICK_GATHER, id);
        
    }
    
    @Override
    public void onClick() {
        for (ConstructionOuputRule rule : baseOutputRules) {
            boolean success = true;
            if (success) {
                int sumAmout = rule.getAmount() * 1;
                game.getModelContext().getStorageManager().addResourceNum(rule.getResourceType(), sumAmout);
            }
        }
    }
    
    @Override
    public boolean canClick() {
        return true;
    }
    
    @Override
    public String getButtonDescroption() {
        return "Gather";
    }
    
    @Override
    public void onLogicFrame() {
        // do nothing
    }
    
    @Override
    public void updateModifiedValues() {
        Gdx.app.log(this.getClass().getSimpleName(), "updateCurrentCache called");
        this.modifiedOutputMap = baseOutputRules.stream()
                .collect(Collectors.toMap(
                        rule -> rule.getResourceType(), 
                        rule -> rule.getAmount() * 1
                        ));
        this.modifiedOutputDescription = 
                "AutoOutput: "
                + modifiedOutputMap.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
    }
    
    @Override
    protected String getDetailDescroptionDynamicPart() {
        return modifiedOutputDescription;
    }

}
