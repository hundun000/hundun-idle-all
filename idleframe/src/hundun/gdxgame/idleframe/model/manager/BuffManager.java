package hundun.gdxgame.idleframe.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;


import hundun.gdxgame.idleframe.BaseIdleGame;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {
    
    BaseIdleGame game;
    
    @Setter
    @Getter
    Map<String, Integer> buffAmounts = new HashMap<>();
    
    public BuffManager(BaseIdleGame game) {
        this.game = game;
    }
    
    
    
    public int getBuffAmoutOrDefault(String id) {
        return buffAmounts.getOrDefault(id, 0);
    }
    
    public void addBuffAmout(String id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        game.getEventManager().notifyBuffChange(false);
    }

    
    public int modifyResourceGain(String resourceType, int oldAmout) {
        int newAmout = oldAmout;
        for (var entry : buffAmounts.entrySet()) {
            String buffId = entry.getKey();
            double buffAmount = entry.getValue();
//            switch (buffId) {
//                case BUFF_HONEY:
//                    if (resourceType == ResourceType.HONEY) {
//                        newAmout *= 1 + (0.5 * buffAmount);
//                    }
//                    break;
//    
//                default:
//                    break;
//            }
        }
        return newAmout;
    }

}
