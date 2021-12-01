package hundun.gdxgame.bugindustry.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.construction.BuffId;
import hundun.gdxgame.bugindustry.model.resource.ResourceType;
import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {
    
    BugIndustryGame game;
    
    @Setter
    @Getter
    Map<BuffId, Integer> buffAmounts = new HashMap<>();
    
    public BuffManager(BugIndustryGame game) {
        this.game = game;
    }
    
    
    
    public int getBuffAmoutOrDefault(BuffId id) {
        return buffAmounts.getOrDefault(id, 0);
    }
    
    public void addBuffAmout(BuffId id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        game.getEventManager().notifyBuffChange(false);
    }

    
    public int modifyResourceGain(ResourceType resourceType, int oldAmout) {
        int newAmout = oldAmout;
        for (var entry : buffAmounts.entrySet()) {
            BuffId buffId = entry.getKey();
            double buffAmount = entry.getValue();
            switch (buffId) {
                case BUFF_HONEY:
                    if (resourceType == ResourceType.HONEY) {
                        newAmout *= 1 + (0.5 * buffAmount);
                    }
                    break;
    
                default:
                    break;
            }
        }
        return newAmout;
    }

}
