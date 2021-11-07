package hundun.gdxgame.bugindustry.model.construction;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.data.ConstructionSaveData;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConstruction implements ILogicFrameListener {
    
    @Getter
    protected final ConstructionType type;
    protected Random random = new Random();
    protected final BugIndustryGame game;
    @Setter
    @Getter
    protected ConstructionSaveData saveData;
    @Getter
    private final String saveDataKey;
    @Getter
    protected String name;

    @Getter
    protected String detailDescroptionConstPart;
    
    protected List<ConstructionOuputRule> baseOutputRules;
    protected Map<ResourceType, Integer> currentOutputMapCache;
    protected String currentOutputDeccriptionCache;
    
    public BaseConstruction(BugIndustryGame game, ConstructionType type, String saveDataKey) {
        this.game = game;
        this.type = type;
        this.saveData = new ConstructionSaveData();
        this.saveDataKey = saveDataKey;
    }
    
    public abstract void onClick();
    
    public abstract boolean canClick();
    
    public abstract String getButtonDescroption();
    
    public String getDetailDescroption() {
        return detailDescroptionConstPart + "\n" + getDetailDescroptionDynamicPart();
    }
    
    protected abstract String getDetailDescroptionDynamicPart();

    public abstract void updateCurrentCache();

}
