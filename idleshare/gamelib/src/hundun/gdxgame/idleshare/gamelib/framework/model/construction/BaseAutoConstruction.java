package hundun.gdxgame.idleshare.gamelib.framework.model.construction;

import java.util.List;

import hundun.gdxgame.idleshare.gamelib.context.IdleGamePlayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseAutoConstruction extends BaseConstruction {

    protected int autoOutputProgress = 0;


    public BaseAutoConstruction(String id
            ) {
        super(id);
    }

    @Override
    protected void printDebugInfoAfterConstructed() {
        super.printDebugInfoAfterConstructed();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxLevel; i++) {
            builder.append(i).append("->").append(upgradeComponent.getCalculateCostFunction().apply(1L, i)).append(",");
        }
        gameContext.getFrontEnd().log(this.id, "getUpgradeCost=[" + builder.toString() + "]");
    }


    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        doUpgrade();
    }

    private void doUpgrade() {
        List<ResourcePair> upgradeCostRule = upgradeComponent.getUpgradeCostPack().getModifiedValues();
        gameContext.getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
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
        int logicFrameCountMax = outputComponent.getAutoOutputSecondCountMax() * gameContext.LOGIC_FRAME_PER_SECOND;
        if (autoOutputProgress >= logicFrameCountMax) {
            autoOutputProgress = 0;
            tryAutoOutputOnce();
        }
    }

    private void tryAutoOutputOnce() {
        if (!canOutput()) {
            gameContext.getFrontEnd().log(this.id, "canOutput");
            return;
        }
        gameContext.getFrontEnd().log(this.id, "AutoOutput");
        if (outputComponent.hasCost()) {
            gameContext.getStorageManager().modifyAllResourceNum(outputComponent.getOutputCostPack().getModifiedValues(), false);
        }
        if (outputComponent.getOutputGainPack() != null) {
            gameContext.getStorageManager().modifyAllResourceNum(outputComponent.getOutputGainPack().getModifiedValues(), true);
        }
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
