package hundun.gdxgame.idleframe.model.construction.base;

import com.badlogic.gdx.Gdx;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public class LevelComponent {
    protected static final String descriptionMaxLevelPlaceholder = "{lv}";
    protected static final String descriptionWorkingLevelPlaceholder = "{workingLv}";
    
    private final BaseConstruction construction;


    private final boolean workingLevelChangable;
    // ------ replace-lombok ------
    public boolean isWorkingLevelChangable() {
        return workingLevelChangable;
    }
    
    public LevelComponent(BaseConstruction construction, boolean workingLevelChangable) {
        super();
        this.construction = construction;
        this.workingLevelChangable = workingLevelChangable;
    }
    
    public String getWorkingLevelDescroption() {
        return construction.descriptionPackage.getLevelDescroption()
                .replace(descriptionMaxLevelPlaceholder, String.valueOf(construction.saveData.getLevel()))
                .replace(descriptionWorkingLevelPlaceholder, String.valueOf(construction.saveData.getWorkingLevel()))
                ;
    }
    
    public boolean canChangeWorkingLevel(int delta) {
        if (!workingLevelChangable) {
            return false;
        }
        int next = construction.saveData.getWorkingLevel() + delta;
        if (next > construction.saveData.getLevel() || next < 0) {
            return false;
        }
        return true;
    }
    
    public void changeWorkingLevel(int delta) {
        if (canChangeWorkingLevel(delta)) {
            construction.saveData.setWorkingLevel(construction.saveData.getWorkingLevel() + delta);
            construction.updateModifiedValues();
            Gdx.app.log(construction.name, "changeWorkingLevel delta = " + delta + ", success to " + construction.saveData.getWorkingLevel());
        } else {
            Gdx.app.log(construction.name, "changeWorkingLevel delta = " + delta + ", but cannot!");
        }
    }
    
}
