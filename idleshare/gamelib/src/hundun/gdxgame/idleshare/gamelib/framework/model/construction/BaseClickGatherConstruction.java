package hundun.gdxgame.idleshare.gamelib.framework.model.construction;

import hundun.gdxgame.idleshare.gamelib.context.IdleGamePlayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseClickGatherConstruction extends BaseConstruction {

    public BaseClickGatherConstruction(String id
            ) {
        super(id);
    }

    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        doGather();
    }
    
    private void doGather() {
        if (outputComponent.hasCost()) {
            gameContext.getStorageManager().modifyAllResourceNum(outputComponent.getOutputCostPack().getModifiedValues(), false);
        }
        gameContext.getStorageManager().modifyAllResourceNum(outputComponent.getOutputGainPack().getModifiedValues(), true);
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
    protected long calculateModifiedOutput(long baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        return baseValue;
    }


}
