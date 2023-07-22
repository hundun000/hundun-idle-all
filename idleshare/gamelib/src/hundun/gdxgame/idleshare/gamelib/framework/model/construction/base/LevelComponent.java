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
    private boolean workingLevelChangable;
    public static final int DEFAULT_MIN_WORKING_LEVEL = 0;
    public int minWorkingLevel = DEFAULT_MIN_WORKING_LEVEL;
    public static final int DEFAULT_MAX_LEVEL = 5;
    public int maxLevel = DEFAULT_MAX_LEVEL;
    public LevelComponent(BaseConstruction construction, boolean workingLevelChangable) {
        super();
        this.construction = construction;
        this.workingLevelChangable = workingLevelChangable;
    }

    public String getWorkingLevelDescroption() {
        boolean reachMaxLevel = construction.getSaveData().getLevel() == this.maxLevel;
        return construction.descriptionPackage.getLevelDescroptionProvider().provide(construction.saveData.getLevel(), construction.saveData.getWorkingLevel(), reachMaxLevel);
    }

    public boolean canChangeWorkingLevel(int delta) {
        if (!workingLevelChangable) {
            return false;
        }
        int next = construction.saveData.getWorkingLevel() + delta;
        if (next > construction.saveData.getLevel() || next < minWorkingLevel) {
            return false;
        }
        return true;
    }

    public void changeWorkingLevel(int delta) {
        if (canChangeWorkingLevel(delta)) {
            construction.saveData.setWorkingLevel(construction.saveData.getWorkingLevel() + delta);
            construction.updateModifiedValues();
            construction.getGameplayContext().getFrontEnd().log(construction.name, "changeWorkingLevel delta = " + delta + ", success to " + construction.saveData.getWorkingLevel());
        } else {
            construction.getGameplayContext().getFrontEnd().log(construction.name, "changeWorkingLevel delta = " + delta + ", but cannot!");
        }
    }

    public boolean isReachMaxLevel()
    {
        return construction.saveData.getLevel() >= this.maxLevel;
    }

}
