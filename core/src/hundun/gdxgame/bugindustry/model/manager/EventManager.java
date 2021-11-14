package hundun.gdxgame.bugindustry.model.manager;

import java.util.ArrayList;
import java.util.List;

import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class EventManager {
    List<IAmountChangeEventListener> listeners = new ArrayList<>();
    
    public void registerListener(IAmountChangeEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void notifyBuffChange(boolean fromLoad) {
        for (IAmountChangeEventListener listener : listeners) {
            listener.onBuffChange(fromLoad);
        }
    }

    public void notifyResourceAmountChange() {
        for (IAmountChangeEventListener listener : listeners) {
            listener.onResourceChange();
        }
    }
}
