package hundun.gdxgame.bugindustry.model.construction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseAutoConstruction extends BaseConstruction {
    
    /**
     * 影响升级后生产量，详见具体公式
     */
    protected final double autoOuputSuccessRateLevelUpArg = 4.0;
    
    /**
     * 影响升级后下一级费用，详见具体公式
     */
    protected final double upgradeCostLevelUpArg = 2.0;
    

    
    protected int autoOutputProgress = 0;
    protected static final int AUTO_OUPUT_MAX_PROGRESS = 30;
    
    public BaseAutoConstruction(BugIndustryGame game, ConstructionId id) {
        super(game, ConstructionType.AUTO_OUTPUT, id);
    }
    
    @Override
    protected void printDebugInfoAfterConstructed() {
        super.printDebugInfoAfterConstructed();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LEVEL; i++) {
            builder.append(i).append("->").append(calculateModifiedUpgradeCost(1, i)).append(",");
        }
        Gdx.app.log(this.id.name(), "getUpgradeCost=[" + builder.toString() + "]");
    }


    @Override
    public void onClick() {
        if (!canUpgrade()) {
            return;
        }
        Map<ResourceType, Integer> upgradeCostRule = modifiedUpgradeCostMap;
        game.getModelContext().getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        saveData.setLevel(saveData.getLevel() + 1);
        updateModifiedValues();
    }

    @Override
    public boolean canClick() {
        return canUpgrade();
    }
    
    @Override
    public String getButtonDescroption() {
        String des;
        if (workingLevelChangable) {
            des = "Upgrade(" + saveData.getWorkingLevel() + "/" + saveData.getLevel() + ")";
        } else {
            des = "Upgrade(" + saveData.getWorkingLevel() + ")";
        }
        return des;
    }
    
    
    
    @Override
    public void onLogicFrame() {
        autoOutputProgress++;
        if (autoOutputProgress >= AUTO_OUPUT_MAX_PROGRESS) {
            autoOutputProgress = 0;
            tryAutoOutputOnce();
        }
    }
    
    private void tryAutoOutputOnce() {
        if (!canOutput()) {
            Gdx.app.log(this.id.name(), "canOutput");
            return;
        }
        Gdx.app.log(this.id.name(), "AutoOutput");
        if (modifiedOutputCostMap != null) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(modifiedOutputCostMap, false);
        }
        game.getModelContext().getStorageManager().modifyAllResourceNum(modifiedOutputGainMap, true);
    }
    
    @Override
    protected String getDetailDescroptionDynamicPart() {
        StringBuilder builder = new StringBuilder();
        if (modifiedOuputCostDescription != null) {
            builder.append("AutoCost: ").append(modifiedOuputCostDescription).append("\n");
        }
        builder.append("AutoGain: ").append(modifiedOutputGainDescription).append("\n");
        builder.append("UpgradeCost: ").append(modifiedUpgradeCostDescription).append("\n");
        return builder.toString();
    }

    @Override
    protected int calculateModifiedUpgradeCost(int baseValue, int level) {
        return (int)(
                baseValue
                * (1 + 2 * level)
                * Math.pow(upgradeCostLevelUpArg, level)
                );
    }
    
    @Override
    protected int calculateModifiedOutput(int baseValue, int level) {
        return baseValue * level;
    }

    @Override
    protected int calculateModifiedOutputCost(int baseValue, int level) {
        return baseValue * level;
    }

    

}
