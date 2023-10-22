package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class UpgradeComponent {

    public static enum UpgradeState {
        NO_UPGRADE,
        HAS_NEXT_UPGRADE,
        REACHED_MAX_UPGRADE_NO_TRANSFER,
        REACHED_MAX_UPGRADE_HAS_TRANSFER;
    }

    private final BaseConstruction construction;

    @Getter
    @Setter
    private ResourcePack upgradeCostPack;
    @Getter
    @Setter
    private ResourcePack transformCostPack;
    @Getter
    @Setter
    private String transformConstructionPrototypeId;

    @Getter
    private UpgradeState upgradeState;


    /**
     * 影响升级后下一级费用，详见具体公式
     */
    private static final double upgradeCostLevelUpArg = 1.05;
    private static final BiFunction<Long, Integer, Long> DEFAULT_CALCULATE_COST_FUNCTION = (baseValue, level) -> {
        return (long) (
                baseValue
                        * (1 + 1 * level)
                        * Math.pow(upgradeCostLevelUpArg, level)
        );
    };

    @Getter
    private BiFunction<Long, Integer, Long> calculateCostFunction = DEFAULT_CALCULATE_COST_FUNCTION;

    public void setCalculateCostFunction(BiFunction<Long, Integer, Long> calculateCostFunction) {
        this.calculateCostFunction = calculateCostFunction;
    }

    public UpgradeComponent(BaseConstruction construction) {
        super();
        this.construction = construction;
        // default value
        upgradeState = UpgradeState.NO_UPGRADE;
    }

    public void lazyInitDescription() {
        if (upgradeCostPack != null) {
            upgradeState = UpgradeState.HAS_NEXT_UPGRADE;
            upgradeCostPack.setDescriptionStart(construction.descriptionPackage.getUpgradeCostDescriptionStart());
        }
        if (transformCostPack != null) {
            transformCostPack.setDescriptionStart(construction.descriptionPackage.getTransformCostDescriptionStart());
        }
    }

    public void updateModifiedValues() {
        Boolean reachMaxLevel = construction.levelComponent.isReachMaxLevel();
        if (upgradeCostPack != null) {
            if (reachMaxLevel) {
                this.upgradeCostPack.setModifiedValues(null);
                this.upgradeCostPack.setModifiedValuesDescription(null);
                if (transformCostPack != null) {
                    upgradeState = UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER;

                    this.transformCostPack.setModifiedValues(transformCostPack.getBaseValues());
                    this.transformCostPack.setModifiedValuesDescription(ResourcePack.toDescription(this.transformCostPack.getModifiedValues()));
                } else {
                    upgradeState = UpgradeState.REACHED_MAX_UPGRADE_NO_TRANSFER;
                }
            } else {
                this.upgradeCostPack.setModifiedValues(
                        upgradeCostPack.getBaseValues().stream()
                                .map(pair -> {
                                    long newAmout = calculateCostFunction.apply(pair.getAmount(), construction.saveData.getLevel());
                                    return new ResourcePair(pair.getType(), newAmout);
                                })
                                .collect(Collectors.toList())
                );
                this.upgradeCostPack.setModifiedValuesDescription(ResourcePack.toDescription(this.upgradeCostPack.getModifiedValues()));
            }
        }
    }

    public boolean canUpgrade() {
        if (construction.levelComponent.isReachMaxLevel() || upgradeCostPack == null) {
            return false;
        }
        if (!construction.proficiencyComponent.isEnoughProficiencyForUpgrade()) {
            return false;
        }

        List<ResourcePair> compareTarget = upgradeCostPack.getModifiedValues();
        return construction.getGameplayContext().getStorageManager().isEnough(compareTarget);
    }

    public void doUpgrade()
    {
        List<ResourcePair> upgradeCostRule = this.upgradeCostPack.getModifiedValues();
        construction.gameplayContext.getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        construction.saveData.setLevel((construction.saveData.getLevel() + 1));
        if (!construction.levelComponent.isTypeWorkingLevelChangeable())
        {
            construction.saveData.setWorkingLevel((construction.saveData.getLevel()));
        }
        construction.proficiencyComponent.afterUpgrade();
        construction.updateModifiedValues();
        construction.gameplayContext.getEventManager().notifyConstructionCollectionChange();
    }

    public Boolean canTransfer()
    {
        if (!construction.levelComponent.isReachMaxLevel() || transformCostPack == null)
        {
            return false;
        }
        if (!construction.proficiencyComponent.isMaxProficiency())
        {
            return false;
        }

        List<ResourcePair> compareTarget = transformCostPack.getModifiedValues();
        return construction.gameplayContext.getStorageManager().isEnough(compareTarget);
    }

    public void doTransform()
    {
        if (construction.upgradeComponent.transformCostPack != null)
        {
            construction.gameplayContext.getStorageManager().modifyAllResourceNum(construction.upgradeComponent.transformCostPack.getModifiedValues(), false);
        }
        construction.gameplayContext.getConstructionManager().addToRemoveQueue(construction);
        construction.gameplayContext.getConstructionManager().addToCreateQueue(construction.upgradeComponent.transformConstructionPrototypeId, construction.getPosition());
    }
}