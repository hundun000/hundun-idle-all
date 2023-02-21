package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementUnlockCallback;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.AchievementPrototype;



/**
 * @author hundun
 * Created on 2021/11/12
 */
public class EventManager {
    List<IBuffChangeListener> buffChangeListeners = new ArrayList<>();
    List<IAchievementUnlockCallback> achievementUnlockListeners = new ArrayList<>();
    List<IOneFrameResourceChangeListener> oneFrameResourceChangeListeners = new ArrayList<>();

    IdleGameplayContext gameContext;


    public EventManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }
    
    public void registerListener(Object listener) {
        if (listener instanceof IBuffChangeListener && !buffChangeListeners.contains(listener)) {
            buffChangeListeners.add((IBuffChangeListener) listener);
        }
        if (listener instanceof IAchievementUnlockCallback && !achievementUnlockListeners.contains(listener)) {
            achievementUnlockListeners.add((IAchievementUnlockCallback) listener);
        }
        if (listener instanceof IOneFrameResourceChangeListener && !oneFrameResourceChangeListeners.contains(listener)) {
            oneFrameResourceChangeListeners.add((IOneFrameResourceChangeListener) listener);
        }
    }

    public void notifyBuffChange() {
        gameContext.getFrontEnd().log(this.getClass().getSimpleName(), "notifyBuffChange");
        for (IBuffChangeListener listener : buffChangeListeners) {
            listener.onBuffChange();
        }
    }

//    public void notifyResourceAmountChange(boolean fromLoad) {
//        Gdx.app.log(this.getClass().getSimpleName(), "notifyResourceAmountChange");
//        for (IAmountChangeEventListener listener : amountChangeEventListeners) {
//            listener.onResourceChange(fromLoad);
//        }
//    }

    public void notifyOneFrameResourceChange(Map<String, Long> changeMap) {
        //Gdx.app.log(this.getClass().getSimpleName(), "notifyOneFrameResourceChange");
        for (IOneFrameResourceChangeListener listener : oneFrameResourceChangeListeners) {
            listener.onResourceChange(changeMap);
        }
    }

    public void notifyAchievementUnlock(AchievementPrototype prototype) {
        gameContext.getFrontEnd().log(this.getClass().getSimpleName(), "notifyAchievementUnlock");
        for (IAchievementUnlockCallback listener : achievementUnlockListeners) {
            listener.onAchievementUnlock(prototype);
        }
    }
}
