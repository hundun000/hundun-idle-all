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
        super(game, id);
    }
    
    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        game.getModelContext().getStorageManager().modifyAllResourceNum(modifiedOutputGainMap, true);
    }
    
    @Override
    public boolean canClickEffect() {
        return canOutput();
    }
    
    
    @Override
    public void onLogicFrame() {
        // do nothing
    }


    @Override
    protected long calculateModifiedUpgradeCost(int baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutput(int baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutputCost(int baseValue, int level) {
        return baseValue;
    }


}
