package hundun.gdxgame.bugindustry.model.construction;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.data.ConstructionSaveData;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.IBuffChangeListener;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConstruction implements ILogicFrameListener, IBuffChangeListener {
    protected final int MAX_LEVEL = 10;
    @Getter
    protected final ConstructionType type;
    protected Random random = new Random();
    protected final BugIndustryGame game;
    @Setter
    @Getter
    protected ConstructionSaveData saveData;
    @Getter
    private final String saveDataKey;
    @Getter
    protected String name;

    @Getter
    protected String detailDescroptionConstPart;
    
    protected List<ConstructionOuputRule> baseOutputRules;
    protected Map<ResourceType, Integer> modifiedOutputMap;
    protected String modifiedOutputDescription;
    
    protected Map<ResourceType, Integer> baseUpgradeCostMap;
    protected Map<ResourceType, Integer> modifiedUpgradeCostMap;
    protected String modifiedUpgradeCostDescription;
    
    public BaseConstruction(BugIndustryGame game, ConstructionType type, String saveDataKey) {
        this.game = game;
        this.type = type;
        this.saveData = new ConstructionSaveData();
        this.saveDataKey = saveDataKey;
        
        game.getModelContext().getBuffManager().registerListener(this);
    }
    
    public abstract void onClick();
    
    public abstract boolean canClick();
    
    public abstract String getButtonDescroption();
    
    public String getDetailDescroption() {
        return detailDescroptionConstPart + "\n" + getDetailDescroptionDynamicPart();
    }
    
    protected abstract String getDetailDescroptionDynamicPart();

    /**
     * 重新计算各个数值的加成后的结果
     */
    public abstract void updateModifiedValues();
    
    @Override
    public void onBuffChange() {
        updateModifiedValues();
    }
    
    protected boolean canUpgrade() {
        if (saveData.getLevel() >= MAX_LEVEL) {
            return false;
        }
        Map<ResourceType, Integer> upgradeCostRule = modifiedUpgradeCostMap;
        for (var entry : upgradeCostRule.entrySet()) {
            int own = game.getModelContext().getStorageModel().getResourceNum(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
