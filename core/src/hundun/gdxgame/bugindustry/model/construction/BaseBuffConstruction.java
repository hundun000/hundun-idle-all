package hundun.gdxgame.bugindustry.model.construction;

import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseBuffConstruction extends BaseConstruction {

    /**
     * 影响升级后下一级费用
     */
    protected final double upgradeCostLevelUpArg = 2.0;
    
    private final BuffId buffId;
    
    public BaseBuffConstruction(BugIndustryGame game, BuffId buffId, ConstructionId id) {
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
        Map<ResourceType, Long> upgradeCostRule = modifiedUpgradeCostMap;
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
    protected long calculateModifiedUpgradeCost(int baseValue, int level) {
        return (int)(
                baseValue
                * Math.pow(upgradeCostLevelUpArg, level)
                );
    }

    @Override
    protected long calculateModifiedOutput(int baseValue, int level) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected long calculateModifiedOutputCost(int baseValue, int level) {
        throw new UnsupportedOperationException();
    }


}
