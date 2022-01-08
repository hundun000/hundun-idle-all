package hundun.gdxgame.idleframe.model.construction.base;

import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class PromotionComponent {
    
    public enum ConditionMeetStatus {
        MEET_NONE,
        MEET_LEVEL,
        MEET_ALL,
        ;
    }
    
    private final BaseConstruction construction;
    @Setter
    private int levelCondition;
    @Setter
    private String nextId;
    @Setter
    private ResourcePack promotionCostPack;
    
    public PromotionComponent(BaseConstruction parent) {
        super();
        this.construction = parent;
    }
    
    public ConditionMeetStatus canPromotion() {
        boolean meetLevel = isMeetLevel();
        boolean meetResource = isMeetResource();
        
        int code = 0;
        int pos = 0;
        code += (meetLevel ? 1 : 0) * 10^(pos++);
        code += (meetResource ? 1 : 0) * 10^(pos++);
        
        switch (code) {
            case 10:      
                return ConditionMeetStatus.MEET_LEVEL;
            case 11:      
                return ConditionMeetStatus.MEET_ALL;
            default:
                return ConditionMeetStatus.MEET_NONE;
        }
    }
    
    public boolean isMeetLevel() {
        return construction.getSaveData().getLevel() >= levelCondition;
    }
    
    public boolean isMeetResource() {
        return construction.game.getModelContext().getStorageManager().isEnough(promotionCostPack.getModifiedValues());
    }
    
}
