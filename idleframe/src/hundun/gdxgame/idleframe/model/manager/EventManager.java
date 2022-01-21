package hundun.gdxgame.idleframe.model.manager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleframe.listener.IAchievementUnlockListener;
import hundun.gdxgame.idleframe.listener.IAmountChangeEventListener;
import hundun.gdxgame.idleframe.model.AchievementPrototype;



/**
 * @author hundun
 * Created on 2021/11/12
 */
public class EventManager {
    List<IAmountChangeEventListener> amountChangeEventListeners = new ArrayList<>();
    List<IAchievementUnlockListener> achievementUnlockListeners = new ArrayList<>();
    
    public void registerListener(Object listener) {
        if (listener instanceof IAmountChangeEventListener && !amountChangeEventListeners.contains(listener)) {
            amountChangeEventListeners.add((IAmountChangeEventListener) listener);
        }
        if (listener instanceof IAchievementUnlockListener && !achievementUnlockListeners.contains(listener)) {
            achievementUnlockListeners.add((IAchievementUnlockListener) listener);
        }
    }
    
    public void notifyBuffChange(boolean fromLoad) {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyBuffChange");
        for (IAmountChangeEventListener listener : amountChangeEventListeners) {
            listener.onBuffChange(fromLoad);
        }
    }

    public void notifyResourceAmountChange(boolean fromLoad) {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyResourceAmountChange");
        for (IAmountChangeEventListener listener : amountChangeEventListeners) {
            listener.onResourceChange(fromLoad);
        }
    }

    public void notifyAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyResourceAmountChange");
        for (IAchievementUnlockListener listener : achievementUnlockListeners) {
            listener.onAchievementUnlock(prototype);
        }
    }
}
