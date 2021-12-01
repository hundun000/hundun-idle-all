package hundun.gdxgame.idleframe.model.construction;
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

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.IAmountChangeEventListener;
import hundun.gdxgame.idleframe.ILogicFrameListener;
import hundun.gdxgame.idleframe.data.ConstructionSaveData;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;
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
    
//    public static final DescriptionPackage BUFF_DESCRIPTION_PACKAGE = new DescriptionPackage(
//            null, null, "EnhanceCost", "Enhance", 
//            "lv." + descriptionMaxLevelPlaceholder);
    
    public static final DescriptionPackage GATHER_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, "Gain", null, "Gather", 
            "");
    
    public static final DescriptionPackage WIN_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, "Gain", "Pay", "Win", 
            "");
    
    protected Random random = new Random();
    protected final BaseIdleGame game;
    @Setter
    @Getter
    protected ConstructionSaveData saveData;

    @Getter
    public String name;
    @Getter
    public String id;
    @Getter
    public String detailDescroptionConstPart;
    @Getter
    public boolean workingLevelChangable;

    @Getter
    public DescriptionPackage descriptionPackage;
    
    /**
     * 对于Click型，即为基础点击收益；对于Auto型，即为基础自动收益；
     */
    @Getter
    public ResourcePack outputGainPack;
    
    /**
     * output行为所需要支付的费用; 无费用时为null
     */
    @Getter
    public ResourcePack outputCostPack;

    
    /**
     * 升级所需要支付的费用; 无法升级时为null
     */
    @Getter
    public ResourcePack upgradeCostPack;
    
//    protected Map<ResourceType, Integer> baseUpgradeCostMap;
//    @Getter
//    protected Map<ResourceType, Long> modifiedUpgradeCostMap;
//    protected String modifiedUpgradeCostDescription;
//    @Getter
//    protected String upgradeCostDescriptionStart;
    

    public void updateDescription() {
        if (outputCostPack != null) {
            outputCostPack.setDescriptionStart(descriptionPackage.getOutputCostDescriptionStart());
        }
        if (outputGainPack != null) {
            outputGainPack.setDescriptionStart(descriptionPackage.getOutputGainDescriptionStart());
        }
        if (upgradeCostPack != null) {
            upgradeCostPack.setDescriptionStart(descriptionPackage.getUpgradeCostDescriptionStart());
        }
    }
    
    public BaseConstruction(BaseIdleGame game, String id) {
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
    
    protected abstract long calculateModifiedUpgradeCost(long baseValue, int level);
    protected abstract long calculateModifiedOutput(long baseValue, int level);
    protected abstract long calculateModifiedOutputCost(long baseValue, int level);

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
        if (upgradeCostPack != null) {
            upgradeCostPack.setModifiedValues(
                    upgradeCostPack.getBaseValues().stream()
                        .map(pair -> {
                                var newAmout = calculateModifiedUpgradeCost(pair.getAmount(), saveData.getWorkingLevel());
                                return new ResourcePair(pair.getType(), newAmout);
                            })
                        .collect(Collectors.toList())
            );
            this.upgradeCostPack.setModifiedValuesDescription(
                    upgradeCostPack.getModifiedValues().stream()
                    .map(pair -> pair.getType() + "x" + pair.getAmount())
                    .collect(Collectors.joining(", "))
                    + "; "
            );
        }
        // --------------
        if (outputGainPack != null) {
            outputGainPack.setModifiedValues(
                    outputGainPack.getBaseValues().stream()
                        .map(pair -> {
                                var newAmout = calculateModifiedOutput(pair.getAmount(), saveData.getWorkingLevel());
                                return new ResourcePair(pair.getType(), newAmout);
                            })
                        .collect(Collectors.toList())
            );
            this.outputGainPack.setModifiedValuesDescription(
                    outputGainPack.getModifiedValues().stream()
                    .map(pair -> pair.getType() + "x" + pair.getAmount())
                    .collect(Collectors.joining(", "))
                    + "; "
            );
        }
        // --------------
        if (outputCostPack != null) {
            outputCostPack.setModifiedValues(
                    outputCostPack.getBaseValues().stream()
                        .map(pair -> {
                                var newAmout = calculateModifiedOutputCost(pair.getAmount(), saveData.getWorkingLevel());
                                return new ResourcePair(pair.getType(), newAmout);
                            })
                        .collect(Collectors.toList())
            );
            this.outputCostPack.setModifiedValuesDescription(
                    outputCostPack.getModifiedValues().stream()
                    .map(pair -> pair.getType() + "x" + pair.getAmount())
                    .collect(Collectors.joining(", "))
                    + "; "
            );
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
        if (outputCostPack == null) {
            return true;
        }
        
        var compareTarget = outputCostPack.getModifiedValues();
        return game.getModelContext().getStorageManager().isEnough(compareTarget);
    }
    
    
    protected boolean canUpgrade() {
        if (saveData.getLevel() >= MAX_LEVEL || upgradeCostPack == null) {
            return false;
        }
        
        var compareTarget = upgradeCostPack.getModifiedValues();
        return game.getModelContext().getStorageManager().isEnough(compareTarget);
    }
    
    public String getSaveDataKey() {
        return id;
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
