package hundun.gdxgame.idleframe.model.construction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseAutoConstruction extends BaseConstruction {
    
    /**
     * 影响升级后下一级费用，详见具体公式
     */
    protected final double upgradeCostLevelUpArg = 1.1;
    



    
    protected int autoOutputProgress = 0;
    protected static final int AUTO_OUPUT_SECOND_COUNT = 1;
    protected static final int AUTO_OUPUT_LOGIC_FRAME_COUNT = AUTO_OUPUT_SECOND_COUNT * BasePlayScreen.LOGIC_FRAME_PER_SECOND;
    
    public BaseAutoConstruction(BaseIdleGame game, String id
            ) {
        super(game, id); 
    }
    
    @Override
    protected void printDebugInfoAfterConstructed() {
        super.printDebugInfoAfterConstructed();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LEVEL; i++) {
            builder.append(i).append("->").append(calculateModifiedUpgradeCost(1, i)).append(",");
        }
        Gdx.app.log(this.id, "getUpgradeCost=[" + builder.toString() + "]");
    }


    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        var upgradeCostRule = upgradeComponent.getUpgradeCostPack().getModifiedValues();
        game.getModelContext().getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        saveData.setLevel(saveData.getLevel() + 1);
        if (!levelComponent.isWorkingLevelChangable()) {
            saveData.setWorkingLevel(saveData.getLevel());
        }
        updateModifiedValues();
    }

    @Override
    public boolean canClickEffect() {
        return canUpgrade();
    }
    
    
    @Override
    public void onLogicFrame() {
        autoOutputProgress++;
        if (autoOutputProgress >= AUTO_OUPUT_LOGIC_FRAME_COUNT) {
            autoOutputProgress = 0;
            tryAutoOutputOnce();
        }
    }
    
    private void tryAutoOutputOnce() {
        if (!canOutput()) {
            Gdx.app.log(this.id, "canOutput");
            return;
        }
        Gdx.app.log(this.id, "AutoOutput");
        if (outputComponent.hasCost()) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputCostPack().getModifiedValues(), false);
        }
        game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputGainPack().getModifiedValues(), true);
    }
    

    @Override
    protected long calculateModifiedUpgradeCost(long baseValue, int level) {
        return (int)(
                baseValue
                * (1 + 2 * level)
                * Math.pow(upgradeCostLevelUpArg, level)
                );
    }
    
    @Override
    protected long calculateModifiedOutput(long baseValue, int level) {
        return baseValue * level;
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        return baseValue * level;
    }


    

}
