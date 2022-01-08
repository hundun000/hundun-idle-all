package hundun.gdxgame.idleframe.model.construction.base;

import java.util.stream.Collectors;

import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class UpgradeComponent {
    private final BaseConstruction construction;

    @Setter
    @Getter
    private ResourcePack upgradeCostPack;
    
    public UpgradeComponent(BaseConstruction construction) {
        super();
        this.construction = construction;
    }
    
    public void updateDescription() {
        if (upgradeCostPack != null) {
            upgradeCostPack.setDescriptionStart(construction.descriptionPackage.getUpgradeCostDescriptionStart());
        }
    }
    
    public void updateModifiedValues() {
        if (upgradeCostPack != null) {
            upgradeCostPack.setModifiedValues(
                    upgradeCostPack.getBaseValues().stream()
                        .map(pair -> {
                                var newAmout = construction.calculateModifiedUpgradeCost(pair.getAmount(), construction.saveData.getWorkingLevel());
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
    }
    
    protected boolean canUpgrade() {
        if (construction.saveData.getLevel() >= construction.MAX_LEVEL || upgradeCostPack == null) {
            return false;
        }
        
        var compareTarget = upgradeCostPack.getModifiedValues();
        return construction.game.getModelContext().getStorageManager().isEnough(compareTarget);
    }
    
}
