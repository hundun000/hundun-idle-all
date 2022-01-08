package hundun.gdxgame.idleframe.model.construction.base;

import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class OutputComponent {
    private final BaseConstruction construction;
    
    /**
     * 对于Click型，即为基础点击收益；对于Auto型，即为基础自动收益；
     */
    @Nullable
    @Setter
    @Getter
    protected ResourcePack outputGainPack;
    
    /**
     * output行为所需要支付的费用; 无费用时为null
     */
    @Nullable
    @Setter
    @Getter
    protected ResourcePack outputCostPack;
    
    public OutputComponent(BaseConstruction construction) {
        this.construction = construction;
    }
    
    public void updateDescription() {
        if (outputCostPack != null) {
            outputCostPack.setDescriptionStart(construction.descriptionPackage.getOutputCostDescriptionStart());
        }
        if (outputGainPack != null) {
            outputGainPack.setDescriptionStart(construction.descriptionPackage.getOutputGainDescriptionStart());
        }
    }
    
    public void updateModifiedValues() {
     // --------------
        if (outputGainPack != null) {
            outputGainPack.setModifiedValues(
                    outputGainPack.getBaseValues().stream()
                        .map(pair -> {
                                var newAmout = construction.calculateModifiedOutput(pair.getAmount(), construction.saveData.getWorkingLevel());
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
                                var newAmout = construction.calculateModifiedOutputCost(pair.getAmount(), construction.saveData.getWorkingLevel());
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
    }
    
    public boolean hasCost() {
        return outputCostPack != null;
    }
    
    protected boolean canOutput() {
        if (!hasCost()) {
            return true;
        }
        
        var compareTarget = outputCostPack.getModifiedValues();
        return construction.game.getModelContext().getStorageManager().isEnough(compareTarget);
    }
}
