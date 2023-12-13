package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import java.util.List;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Setter;
import lombok.Getter;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public abstract class OutputComponent {
    protected final BaseConstruction construction;

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

    protected static final int DEFAULT_AUTO_OUTPUT_SECOND_MAX = 1;
    @Getter
    @Setter
    protected int autoOutputSecondCountMax = DEFAULT_AUTO_OUTPUT_SECOND_MAX;

    @Getter
    @Setter
    protected boolean typeClickOutput;

    public OutputComponent(BaseConstruction construction) {
        this.construction = construction;
    }

    public void updateModifiedValues() {
     // --------------
        if (outputGainPack != null) {
            outputGainPack.setModifiedValues(
                    outputGainPack.getBaseValues().stream()
                        .map(pair -> {
                            long newAmount = this.calculateModifiedOutputGain(
                                    pair.getAmount(),
                                    construction.saveData.getWorkingLevel(),
                                    construction.saveData.getProficiency()
                            );
                            newAmount = construction.getGameplayContext().getBuffManager()
                                    .modifyOutputGain(construction.prototypeId, pair.getType(), newAmount);
                            return new ResourcePair(pair.getType(), newAmount);
                        })
                        .collect(Collectors.toList())
            );
            outputGainPack.setPreviewNextLevelModifiedValues(
                    outputGainPack.getBaseValues().stream()
                            .map(pair -> {
                                long newAmount = this.calculateModifiedOutputGain(
                                        pair.getAmount(),
                                        construction.saveData.getWorkingLevel() + 1,
                                        construction.saveData.getProficiency()
                                );
                                newAmount = construction.getGameplayContext().getBuffManager()
                                        .modifyOutputCost(construction.prototypeId, pair.getType(), newAmount);
                                return new ResourcePair(pair.getType(), newAmount);
                            })
                            .collect(Collectors.toList())
            );
        }
        // --------------
        if (outputCostPack != null) {
            outputCostPack.setModifiedValues(
                    outputCostPack.getBaseValues().stream()
                        .map(pair -> {
                            long newAmount = this.calculateModifiedOutputCost(
                                    pair.getAmount(),
                                    construction.saveData.getWorkingLevel(),
                                    construction.saveData.getProficiency()
                            );
                            newAmount = construction.getGameplayContext().getBuffManager()
                                    .modifyOutputCost(construction.prototypeId, pair.getType(), newAmount);
                            return new ResourcePair(pair.getType(), newAmount);
                        })
                        .collect(Collectors.toList())
            );
            outputCostPack.setPreviewNextLevelModifiedValues(
                    outputCostPack.getBaseValues().stream()
                            .map(pair -> {
                                long newAmount = this.calculateModifiedOutputCost(
                                        pair.getAmount(),
                                        construction.saveData.getWorkingLevel() + 1,
                                        construction.saveData.getProficiency()
                                );
                                newAmount = construction.getGameplayContext().getBuffManager()
                                        .modifyOutputCost(construction.prototypeId, pair.getType(), newAmount);
                                return new ResourcePair(pair.getType(), newAmount);
                            })
                            .collect(Collectors.toList())
            );
        }
    }

    public boolean hasCost() {
        return outputCostPack != null;
    }

    public boolean canOutput() {
        if (!hasCost()) {
            return true;
        }

        List<ResourcePair> compareTarget = outputCostPack.getModifiedValues();
        return construction.getGameplayContext().getStorageManager().isEnough(compareTarget);
    }

    public void doOutput()
    {
        if (this.hasCost())
        {
            construction.getGameplayContext().getStorageManager().modifyAllResourceNum(this.outputCostPack.getModifiedValues(), false);
        }
        if (this.outputGainPack != null)
        {
            construction.getGameplayContext().getStorageManager().modifyAllResourceNum(this.outputGainPack.getModifiedValues(), true);
        }
    }

    public abstract void onSubLogicFrame();
    public abstract long calculateModifiedOutputGain(long baseValue, int level, int proficiency);
    public abstract long calculateModifiedOutputCost(long baseValue, int level, int proficiency);
}
