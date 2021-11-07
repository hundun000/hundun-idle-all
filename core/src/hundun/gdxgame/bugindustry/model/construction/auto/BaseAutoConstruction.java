package hundun.gdxgame.bugindustry.model.construction.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.model.construction.ConstructionType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public abstract class BaseAutoConstruction extends BaseConstruction {
    
    /**
     * 影响升级后生产量
     */
    protected final double autoOuputSuccessRateLevelUpArg = 4.0;
    protected final int MAX_LEVEL = 10;
    /**
     * 影响升级后下一级费用
     */
    protected final double upgradeCostLevelUpArg = 4.0;
    
    protected Map<ResourceType, Integer> baseUpgradeCostMap;
    protected Map<ResourceType, Integer> currentUpgradeCostMapCache;
    protected String currentUpgradeCostDeccriptionCache;
    
    protected int autoOutputProgress = 0;
    protected static final int AUTO_OUPUT_MAX_PROGRESS = 30;
    
    public BaseAutoConstruction(BugIndustryGame game, String saveDataKey) {
        super(game, ConstructionType.AUTO_OUTPUT, saveDataKey);
    }
    
    @Override
    public void updateCurrentCache() {
        this.currentUpgradeCostMapCache = baseUpgradeCostMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), 
                        entry -> (int)(entry.getValue() * Math.pow(upgradeCostLevelUpArg, saveData.getLevel()))
                        )
                );
        this.currentUpgradeCostDeccriptionCache = 
                "UpgradeCost: "
                + currentUpgradeCostMapCache.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
        this.currentOutputMapCache = baseOutputRules.stream()
                .collect(Collectors.toMap(
                        rule -> rule.getResourceType(), 
                        rule -> rule.getAmount() * saveData.getLevel()
                        ));
        this.currentOutputDeccriptionCache = 
                "AutoOutput: "
                + currentOutputMapCache.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
    }

    @Override
    public void onClick() {
        if (!canUpgrade()) {
            return;
        }
        Map<ResourceType, Integer> upgradeCostRule = currentUpgradeCostMapCache;
        for (var entry : upgradeCostRule.entrySet()) {
            game.getModelContext().getStorageModel().addResourceNum(entry.getKey(), -1 * entry.getValue());
        }
        saveData.setLevel(saveData.getLevel() + 1);
        updateCurrentCache();
    }

    @Override
    public boolean canClick() {
        return canUpgrade();
    }
    
    @Override
    public String getButtonDescroption() {
        return "Upgrade(lv." + saveData.getLevel() + ")";
    }
    
    private boolean canUpgrade() {
        if (saveData.getLevel() >= MAX_LEVEL) {
            return false;
        }
        Map<ResourceType, Integer> upgradeCostRule = currentUpgradeCostMapCache;
        for (var entry : upgradeCostRule.entrySet()) {
            int own = game.getModelContext().getStorageModel().getResourceNum(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void onLogicFrame() {
        autoOutputProgress++;
        if (autoOutputProgress >= AUTO_OUPUT_MAX_PROGRESS) {
            autoOutputProgress = 0;
            autoOutputOnce();
        }
    }
    
    private void autoOutputOnce() {
        if (baseOutputRules == null) {
            return;
        }
        for (ConstructionOuputRule rule : baseOutputRules) {
            boolean success = true;
            if (success) {
                int sumAmout = rule.getAmount() * saveData.getLevel();
                game.getModelContext().getStorageModel().addResourceNum(rule.getResourceType(), sumAmout);
            }
        }
    }
    
    @Override
    protected String getDetailDescroptionDynamicPart() {
        return currentOutputDeccriptionCache + "\n" + currentUpgradeCostDeccriptionCache;
    }


}
