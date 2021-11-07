package hundun.gdxgame.bugindustry.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;

import hundun.gdxgame.bugindustry.model.construction.buff.BuffId;
import hundun.gdxgame.bugindustry.ui.IBuffChangeListener;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {
    
    List<IBuffChangeListener> listeners = new ArrayList<>();
    
    @Setter
    @Getter
    Map<BuffId, Integer> buffAmounts = new HashMap<>();
    
    public BuffManager() {
        
    }
    
    public void registerListener(IBuffChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void notifyListeners() {
        for (IBuffChangeListener listener : listeners) {
            listener.onBuffChange();
        }
    }
    
    public int getBuffAmoutOrDefault(BuffId id) {
        return buffAmounts.getOrDefault(id, 0);
    }
    
    public void addBuffAmout(BuffId id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        notifyListeners();
    }

    
    public int modifyResourceGain(ResourceType resourceType, int oldAmout) {
        int newAmout = oldAmout;
        for (var entry : buffAmounts.entrySet()) {
            BuffId buffId = entry.getKey();
            double buffAmount = entry.getValue();
            switch (buffId) {
                case BUFF_HONEY:
                    if (resourceType == ResourceType.HONEY) {
                        newAmout *= (0.5 * buffAmount);
                    }
                    break;
    
                default:
                    break;
            }
        }
        return newAmout;
    }

}
