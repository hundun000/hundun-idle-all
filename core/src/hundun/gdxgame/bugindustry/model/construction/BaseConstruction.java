package hundun.gdxgame.bugindustry.model.construction;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.data.ConstructionSaveData;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConstruction implements ILogicFrameListener, IAmountChangeEventListener {
    
    protected int MAX_LEVEL = 99;
    @Setter
    @Getter
    protected int MAX_DRAW_NUM = 5;
    private static final String descriptionMaxLevelPlaceholder = "{lv}";
    private static final String descriptionWorkingLevelPlaceholder = "{workingLv}";
    
    public static final DescriptionPackage WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade", 
            descriptionWorkingLevelPlaceholder + "/" + descriptionMaxLevelPlaceholder);
    
    public static final DescriptionPackage MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade", 
            "lv." + descriptionMaxLevelPlaceholder);
    
    public static final DescriptionPackage SELLING_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "Sell", "Gain", "UpgradeCost", "Upgrade", 
            descriptionWorkingLevelPlaceholder + "/" + descriptionMaxLevelPlaceholder);
    
    public static final DescriptionPackage BUFF_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, "EnhanceCost", "Enhance", 
            "lv." + descriptionMaxLevelPlaceholder);
    
    public static final DescriptionPackage GATHER_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, null, "Gather", 
            "");
    
    public static final DescriptionPackage WIN_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, "Pay", "Win", 
            "");
    
    protected Random random = new Random();
    protected final BugIndustryGame game;
    @Setter
    @Getter
    protected ConstructionSaveData saveData;

    @Getter
    protected String name;
    @Getter
    protected ConstructionId id;
    @Getter
    protected String detailDescroptionConstPart;
    @Getter
    protected boolean workingLevelChangable;
    @Setter
    @Getter
    protected DescriptionPackage descriptionPackage;
    
    /**
     * 对于Click型，即为基础点击收益；对于Auto型，即为基础自动收益；
     */
    protected List<ConstructionOuputRule> baseOutputGainRules;
    @Getter
    protected Map<ResourceType, Long> modifiedOutputGainMap;
    protected String modifiedOutputGainDescription;
//    @Getter
//    protected String outputGainDescriptionStart;
    
    /**
     * output行为所需要支付的费用; 无费用时为null
     */
    protected Map<ResourceType, Integer> baseOutputCostMap;
    @Getter
    protected Map<ResourceType, Long> modifiedOutputCostMap;
    protected String modifiedOuputCostDescription;
//    @Getter
//    protected String outputCostDescriptionStart;
    
    /**
     * 升级所需要支付的费用; 无发升级时为null
     */
    protected Map<ResourceType, Integer> baseUpgradeCostMap;
    @Getter
    protected Map<ResourceType, Long> modifiedUpgradeCostMap;
    protected String modifiedUpgradeCostDescription;
//    @Getter
//    protected String upgradeCostDescriptionStart;
    
    
    
    public BaseConstruction(BugIndustryGame game, ConstructionId id) {
        this.game = game;
        this.saveData = new ConstructionSaveData();
        this.id = id;
        
        game.getEventManager().registerListener(this);
        if(game.debugMode) {
            printDebugInfoAfterConstructed();
        }
    }
    
    public abstract void onClick();
    
    public abstract boolean canClickEffect();
    
    public String getButtonDescroption() {
        return descriptionPackage.getButtonDescroption();
    }
    
    protected abstract long calculateModifiedUpgradeCost(int baseValue, int level);
    protected abstract long calculateModifiedOutput(int baseValue, int level);
    protected abstract long calculateModifiedOutputCost(int baseValue, int level);

    public String getWorkingLevelDescroption() {
        return descriptionPackage.levelDescroption
                .replace(descriptionMaxLevelPlaceholder, String.valueOf(saveData.getLevel()))
                .replace(descriptionWorkingLevelPlaceholder, String.valueOf(saveData.getWorkingLevel()))
                ;
    }
    
    /**
     * 重新计算各个数值的加成后的结果
     */
    public void updateModifiedValues() {
        Gdx.app.log(this.name, "updateCurrentCache called");
        // --------------
        if (this.baseUpgradeCostMap != null) {
            this.modifiedUpgradeCostMap = baseUpgradeCostMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey(), 
                            entry -> calculateModifiedUpgradeCost(entry.getValue(), saveData.getLevel())
                            )
                    );
            this.modifiedUpgradeCostDescription = 
                    modifiedUpgradeCostMap.entrySet().stream()
                    .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                    .collect(Collectors.joining(", "))
                    + "; ";
        }
        // --------------
        if (this.baseOutputGainRules != null) {
            this.modifiedOutputGainMap = baseOutputGainRules.stream()
                    .collect(Collectors.toMap(
                            rule -> rule.getResourceType(), 
                            rule -> calculateModifiedOutput(rule.getAmount(), saveData.getWorkingLevel())
                            )
                    );
            this.modifiedOutputGainDescription = 
                    modifiedOutputGainMap.entrySet().stream()
                    .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                    .collect(Collectors.joining(", "))
                    + "; ";
        }
        // --------------
        if (this.baseOutputCostMap != null) {
            this.modifiedOutputCostMap = baseOutputCostMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey(), 
                            entry -> calculateModifiedOutputCost(entry.getValue(), saveData.getWorkingLevel())
                            ));
            this.modifiedOuputCostDescription = 
                    modifiedOutputCostMap.entrySet().stream()
                    .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                    .collect(Collectors.joining(", "))
                    + "; ";
        }
        
    };
    
    @Override
    public void onBuffChange(boolean fromLoad) {
        updateModifiedValues();
    }
    
    
    protected void printDebugInfoAfterConstructed() {
        // default do nothing
    }
    
    protected boolean canOutput() {
        if (modifiedOutputCostMap == null) {
            return true;
        }
        
        Map<ResourceType, Long> ouputCostRule = modifiedOutputCostMap;
        for (var entry : ouputCostRule.entrySet()) {
            long own = game.getModelContext().getStorageManager().getResourceNum(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    
    protected boolean canUpgrade() {
        if (saveData.getLevel() >= MAX_LEVEL || modifiedUpgradeCostMap == null) {
            return false;
        }
        Map<ResourceType, Long> upgradeCostRule = modifiedUpgradeCostMap;
        for (var entry : upgradeCostRule.entrySet()) {
            long own = game.getModelContext().getStorageManager().getResourceNum(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    public String getSaveDataKey() {
        return id.name();
    }
    
    public boolean canChangeWorkingLevel(int delta) {
        if (!workingLevelChangable) {
            return false;
        }
        int next = saveData.getWorkingLevel() + delta;
        if (next > saveData.getLevel() || next < 0) {
            return false;
        }
        return true;
    }
    
    public void changeWorkingLevel(int delta) {
        if (canChangeWorkingLevel(delta)) {
            saveData.setWorkingLevel(saveData.getWorkingLevel() + delta);
            updateModifiedValues();
            Gdx.app.log(this.name, "changeWorkingLevel delta = " + delta + ", success to " + saveData.getWorkingLevel());
        } else {
            Gdx.app.log(this.name, "changeWorkingLevel delta = " + delta + ", but cannot!");
        }
    }
}
