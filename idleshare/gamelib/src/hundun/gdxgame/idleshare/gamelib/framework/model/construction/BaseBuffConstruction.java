package hundun.gdxgame.idleshare.gamelib.framework.model.construction;

import java.util.List;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseBuffConstruction extends BaseConstruction {

    private final String buffId;

    public BaseBuffConstruction(String id, String buffId) {
        super(id);
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
        doEnhanceBuff();
    }
    
    private void doEnhanceBuff() {
        List<ResourcePair> upgradeCostRule = upgradeComponent.getUpgradeCostPack().getModifiedValues();
        gameContext.getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        saveData.setLevel(saveData.getLevel() + 1);
        gameContext.getBuffManager().addBuffAmout(buffId, 1);
        updateModifiedValues();
    }

    @Override
    public boolean canClickEffect() {
        return canUpgrade();
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
