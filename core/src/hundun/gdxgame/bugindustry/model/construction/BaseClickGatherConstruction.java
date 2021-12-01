package hundun.gdxgame.bugindustry.model.construction;

import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;

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
        game.getModelContext().getStorageManager().modifyAllResourceNum(outputGainPack.getModifiedValues(), true);
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
    protected long calculateModifiedUpgradeCost(long baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutput(long baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        return baseValue;
    }


}
