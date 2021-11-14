package hundun.gdxgame.bugindustry.model.construction;

import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseBuffConstruction extends BaseConstruction {

    /**
     * 影响升级后下一级费用
     */
    protected final double upgradeCostLevelUpArg = 2.0;
    
    private final BuffId buffId;
    
    public BaseBuffConstruction(BugIndustryGame game, BuffId buffId, ConstructionId id) {
        super(game, ConstructionType.BUFF, id);
        this.buffId = buffId;
    }

    @Override
    public void onLogicFrame() {
        // do nothing
    }

    @Override
    public void onClick() {
        if (!canUpgrade()) {
            return;
        }
        Map<ResourceType, Integer> upgradeCostRule = modifiedUpgradeCostMap;
        for (var entry : upgradeCostRule.entrySet()) {
            game.getModelContext().getStorageManager().addResourceNum(entry.getKey(), -1 * entry.getValue());
        }
        saveData.setLevel(saveData.getLevel() + 1);
        game.getModelContext().getBuffManager().addBuffAmout(buffId, 1);
        updateModifiedValues();
    }

    @Override
    public boolean canClick() {
        return canUpgrade();
    }

    @Override
    public String getButtonDescroption() {
        return "Enhance(lv." + saveData.getLevel() + ")";
    }

    @Override
    protected String getDetailDescroptionDynamicPart() {
        return modifiedUpgradeCostDescription;
    }

    @Override
    public void updateModifiedValues() {
        Gdx.app.log(this.getClass().getSimpleName(), "updateCurrentCache called");
        this.modifiedUpgradeCostMap = baseUpgradeCostMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), 
                        entry -> (int)(
                                entry.getValue() 
                                * Math.pow(upgradeCostLevelUpArg, saveData.getLevel())
                                )
                        )
                );
        this.modifiedUpgradeCostDescription = 
                "EnhanceCost: "
                + modifiedUpgradeCostMap.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
    }

}
