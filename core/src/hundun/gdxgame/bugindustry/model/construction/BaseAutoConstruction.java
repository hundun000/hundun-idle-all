package hundun.gdxgame.bugindustry.model.construction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseAutoConstruction extends BaseConstruction {
    
    /**
     * 影响升级后生产量
     */
    protected final double autoOuputSuccessRateLevelUpArg = 4.0;
    
    /**
     * 影响升级后下一级费用
     */
    protected final double upgradeCostLevelUpArg = 4.0;
    

    
    protected int autoOutputProgress = 0;
    protected static final int AUTO_OUPUT_MAX_PROGRESS = 30;
    
    public BaseAutoConstruction(BugIndustryGame game, ConstructionId id) {
        super(game, ConstructionType.AUTO_OUTPUT, id);
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
                "UpgradeCost: "
                + modifiedUpgradeCostMap.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
        this.modifiedOutputMap = baseOutputRules.stream()
                .collect(Collectors.toMap(
                        rule -> rule.getResourceType(), 
                        rule -> {
                            int oldAmout = rule.getAmount() * saveData.getLevel();
                            int modifiedAmout = game.getModelContext().getBuffManager().modifyResourceGain(rule.getResourceType(), oldAmout);
                            return modifiedAmout;
                        }));
        this.modifiedOutputDescription = 
                "AutoOutput: "
                + modifiedOutputMap.entrySet().stream()
                .map(entry -> entry.getKey().getShowName() + "x" + entry.getValue())
                .collect(Collectors.joining(", "))
                + "; ";
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
        updateModifiedValues();
    }

    @Override
    public boolean canClick() {
        return canUpgrade();
    }
    
    @Override
    public String getButtonDescroption() {
        return "Upgrade(lv." + saveData.getLevel() + ")";
    }
    
    
    
    @Override
    public void onLogicFrame() {
        autoOutputProgress++;
        if (autoOutputProgress >= AUTO_OUPUT_MAX_PROGRESS) {
            autoOutputProgress = 0;
            autoOutputOnce();
        }
    }
    
    private void autoOutputOnce() {
        for (Entry<ResourceType, Integer> entry : modifiedOutputMap.entrySet()) {
            boolean success = true;
            if (success) {
                game.getModelContext().getStorageManager().addResourceNum(entry.getKey(), entry.getValue());
            }
        }
    }
    
    @Override
    protected String getDetailDescroptionDynamicPart() {
        return modifiedOutputDescription + "\n" + modifiedUpgradeCostDescription;
    }

    

}
