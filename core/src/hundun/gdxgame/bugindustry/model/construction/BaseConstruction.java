package hundun.gdxgame.bugindustry.model.construction;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.Getter;

public abstract class BaseConstruction {
    protected Random random = new Random();
    protected BugIndustryGame game;
    
    protected ConstructionOuputRule clickGatherOutputRule;
    protected List<ConstructionOuputRule> frameOutputRules;
    
    protected Map<ResourceType, Integer> upgradeCost;
    @Getter
    protected int level;
    @Getter
    protected String name;
    @Getter
    protected String gatherDescroption;
    
    public BaseConstruction(BugIndustryGame game) {
        this.game = game;
    }
    
    public boolean canClickGather() {
        if (clickGatherOutputRule == null) {
            return false;
        }
        return true;
    }
    
    public void clickGather() {
        if (!canClickGather()) {
            return;
        }
        boolean success = random.nextInt(100) < clickGatherOutputRule.getSuccessRate();
        if (success) {
            game.getModelContext().getStorageModel().addResourceNum(clickGatherOutputRule.getResourceType(), clickGatherOutputRule.getAmount());
        }
        
    }
    
    public boolean canUpgrade() {
        if (upgradeCost == null) {
            return false;
        }
        for (var entry : upgradeCost.entrySet()) {
            int own = game.getModelContext().getStorageModel().getResourceNum(entry.getKey());
            if (own <= entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    public void upgrade() {
        if (!canUpgrade()) {
            return;
        }
        for (var entry : upgradeCost.entrySet()) {
            game.getModelContext().getStorageModel().addResourceNum(entry.getKey(), -1 * entry.getValue());
        }
        this.level++;
    }
}
