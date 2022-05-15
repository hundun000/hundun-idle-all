package hundun.gdxgame.idleframe.model.construction;

import java.util.List;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseBuffConstruction extends BaseConstruction {

    /**
     * 影响升级后下一级费用
     */
    protected final double upgradeCostLevelUpArg = 2.0;

    private final String buffId;

    public BaseBuffConstruction(BaseIdleGame game, String id, String buffId) {
        super(game, id);
        this.buffId = buffId;
    }

    @Override
    public void onLogicFrame() {
        // do nothing
    }

    @Override
    public void onClick() {
        if (!canUpgrade()) {
            return;
        }
        List<ResourcePair> upgradeCostRule = upgradeComponent.getUpgradeCostPack().getModifiedValues();
        game.getModelContext().getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        saveData.setLevel(saveData.getLevel() + 1);
        game.getModelContext().getBuffManager().addBuffAmout(buffId, 1);
        updateModifiedValues();
    }

    @Override
    public boolean canClickEffect() {
        return canUpgrade();
    }


    @Override
    protected long calculateModifiedUpgradeCost(long baseValue, int level) {
        return (int)(
                baseValue
                * Math.pow(upgradeCostLevelUpArg, level)
                );
    }

    @Override
    protected long calculateModifiedOutput(long baseValue, int level) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        throw new UnsupportedOperationException();
    }


}
