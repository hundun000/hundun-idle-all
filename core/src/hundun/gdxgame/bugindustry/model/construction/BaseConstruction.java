package hundun.gdxgame.bugindustry.model.construction;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.Getter;

public abstract class BaseConstruction {
    protected Random random = new Random();
    protected BugIndustryGame game;
    protected int autoOutputCount = 0;
    protected static final int AUTO_OUPUT_COST = 30;
    protected ConstructionOuputRule clickGatherOutputRule;
    protected List<ConstructionOuputRule> autoOutputRules;
    
    protected List<Map<ResourceType, Integer>> upgradeCostRules = new ArrayList<>();
    @Getter
    protected int level;
    @Getter
    protected String name;
    @Getter
    protected String buttonDescroption;
    @Getter
    protected String detailDescroption;
    
    public BaseConstruction(BugIndustryGame game) {
        this.game = game;
    }
    
    public boolean isClickGatherType() {
        if (clickGatherOutputRule == null) {
            return false;
        }
        return true;
    }
    
    public void onClick() {
        if (isClickGatherType()) {
            clickForGather();
        } else {
            clickForUpgrade();
        }
    }
    
    private void clickForGather() {
        if (!isClickGatherType()) {
            return;
        }
        boolean success = random.nextInt(100) < clickGatherOutputRule.getSuccessRate();
        if (success) {
            game.getModelContext().getStorageModel().addResourceNum(clickGatherOutputRule.getResourceType(), clickGatherOutputRule.getAmount());
        }
        
    }
    
    public boolean canClick() {
        if (isClickGatherType()) {
            return true;
        } else {
            return canUpgrade();
        }
    }
    
    private boolean canUpgrade() {
        if (level >= upgradeCostRules.size()) {
            return false;
        }
        var upgradeCostRule = upgradeCostRules.get(level);
        for (var entry : upgradeCostRule.entrySet()) {
            int own = game.getModelContext().getStorageModel().getResourceNum(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    public void autoOutputCountAdd() {
        autoOutputCount++;
        if (autoOutputCount >= AUTO_OUPUT_COST) {
            autoOutputCount = 0;
            autoOutputOnce();
        }
    }
    
    private void autoOutputOnce() {
        if (autoOutputRules == null) {
            return;
        }
        for (ConstructionOuputRule rule : autoOutputRules) {
            boolean success = random.nextInt(100) < rule.getSuccessRate();
            if (success) {
                int sumAmout = rule.getAmount() * level;
                game.getModelContext().getStorageModel().addResourceNum(rule.getResourceType(), sumAmout);
            }
        }
    }
    
    private void clickForUpgrade() {
        if (!canUpgrade()) {
            return;
        }
        var upgradeCostRule = upgradeCostRules.get(level);
        for (var entry : upgradeCostRule.entrySet()) {
            game.getModelContext().getStorageModel().addResourceNum(entry.getKey(), -1 * entry.getValue());
        }
        this.level++;
    }
}
