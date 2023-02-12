package hundun.gdxgame.idleshare.framework.model.construction.base;

import java.util.List;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.framework.model.resource.ResourcePair;
import lombok.Setter;
import lombok.Getter;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public class OutputComponent {
    private final BaseConstruction construction;

    /**
     * 对于Click型，即为基础点击收益；对于Auto型，即为基础自动收益；
     */
    @Getter
    @Setter
    protected ResourcePack outputGainPack;


    /**
     * output行为所需要支付的费用; 无费用时为null
     */
    @Getter
    @Setter
    protected ResourcePack outputCostPack;


    private static final int DEFAULT_AUTO_OUPUT_SECOND_MAX = 1;
    @Getter
    @Setter
    private int autoOutputSecondCountMax = DEFAULT_AUTO_OUPUT_SECOND_MAX;

    public OutputComponent(BaseConstruction construction) {
        this.construction = construction;
    }

    public void lazyInitDescription() {
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
                                long newAmout = construction.calculateModifiedOutput(pair.getAmount(), construction.saveData.getWorkingLevel());
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
                                long newAmout = construction.calculateModifiedOutputCost(pair.getAmount(), construction.saveData.getWorkingLevel());
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

        List<ResourcePair> compareTarget = outputCostPack.getModifiedValues();
        return construction.game.getManagerContext().getStorageManager().isEnough(compareTarget);
    }
}
