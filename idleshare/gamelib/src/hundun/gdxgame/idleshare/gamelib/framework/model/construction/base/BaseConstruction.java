package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Map;
import java.util.Random;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConstruction implements IBuffChangeListener, ITileNode<Void> {


    protected static final int DEFAULT_MAX_DRAW_NUM = 5;
    @Getter
    @Setter
    protected int maxDrawNum = DEFAULT_MAX_DRAW_NUM;

    

    protected Random random = new Random();
    @Getter
    protected IdleGameplayContext gameplayContext;

    /**
     * NotNull
     */
    @Getter
    @Setter
    protected ConstructionSaveData saveData;

    @Getter
    protected String id;
    @Getter
    protected String prototypeId;

    @Getter
    protected DescriptionPackage descriptionPackage;


    /**
     * NotNull
     */
    @Getter
    @Setter
    protected UpgradeComponent upgradeComponent;


    /**
     * NotNull
     */
    @Getter
    @Setter
    protected OutputComponent outputComponent;

    /**
     * NotNull
     */
    @Getter
    @Setter
    protected LevelComponent levelComponent;
    /**
     * NotNull
     */
    @Getter
    @Setter
    protected ProficiencyComponent proficiencyComponent;

    /**
     * NotNull
     */
    @Getter
    @Setter
    protected ExistenceComponent existenceComponent;

    @Getter
    @Setter
    protected boolean allowPositionOverwrite = false;

    Map<TileNeighborDirection, ITileNode<Void>> neighbors;

    @Override
    public GridPosition getPosition() {
        return saveData.getPosition();
    }

    @Override
    public void setPosition(GridPosition position) {
        saveData.setPosition(position);
    }

    @Override
    public Map<TileNeighborDirection, ITileNode<Void>> getNeighbors() {
        return neighbors;
    }

    @Override
    public void setNeighbors(Map<TileNeighborDirection, ITileNode<Void>> neighbors) {
        this.neighbors = neighbors;
    }

    public void lazyInitDescription(IdleGameplayContext gameContext, Language language) {
        this.gameplayContext = gameContext;
        updateModifiedValues();
    }

    public BaseConstruction(String prototypeId, String id) {
        this.prototypeId = prototypeId;
        this.id = id;
        this.saveData = new ConstructionSaveData();
        this.saveData.setPrototypeId(prototypeId);
    }

    /**
     * 重新计算各个数值的加成后的结果
     */
    public void updateModifiedValues() {
        //gameplayContext.getFrontend().log(this.getClass().getSimpleName(), "updateCurrentCache called");
        // --------------
        upgradeComponent.updateModifiedValues();
        outputComponent.updateModifiedValues();
        existenceComponent.updateModifiedValues();
    }

    @Override
    public void onBuffChange(Map<String, Integer> changeMap) {
        updateModifiedValues();
    }

    protected void printDebugInfoAfterConstructed() {
        // default do nothing
    }

    public void onSubLogicFrame()
    {
        outputComponent.onSubLogicFrame();
        proficiencyComponent.onSubLogicFrame();
    }


}
