package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import lombok.Getter;
import lombok.Setter;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public class LevelComponent {

    private final BaseConstruction construction;

    @Setter
    @Getter
    private boolean typeWorkingLevelChangeable;
    public static final int DEFAULT_MIN_WORKING_LEVEL = 0;
    public int minWorkingLevel = DEFAULT_MIN_WORKING_LEVEL;
    public static final int DEFAULT_MAX_LEVEL = 5;
    public int maxLevel = DEFAULT_MAX_LEVEL;
    public LevelComponent(BaseConstruction construction, boolean typeWorkingLevelChangeable) {
        super();
        this.construction = construction;
        this.typeWorkingLevelChangeable = typeWorkingLevelChangeable;
    }

    public boolean canChangeWorkingLevel(int delta) {
        if (!typeWorkingLevelChangeable) {
            return false;
        }
        int next = construction.saveData.getWorkingLevel() + delta;
        return next <= construction.saveData.getLevel() && next >= minWorkingLevel;
    }

    public void changeWorkingLevel(int delta) {
        if (canChangeWorkingLevel(delta)) {
            construction.saveData.setWorkingLevel(construction.saveData.getWorkingLevel() + delta);
            construction.updateModifiedValues();
            construction.getGameplayContext().getFrontend().log(construction.getClass().getSimpleName(), "changeWorkingLevel delta = " + delta + ", success to " + construction.saveData.getWorkingLevel());
        } else {
            construction.getGameplayContext().getFrontend().log(construction.getClass().getSimpleName(), "changeWorkingLevel delta = " + delta + ", but cannot!");
        }
    }

    public boolean isReachMaxLevel()
    {
        return construction.saveData.getLevel() >= this.maxLevel;
    }

}
