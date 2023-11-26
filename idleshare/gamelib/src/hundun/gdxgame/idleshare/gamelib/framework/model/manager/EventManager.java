package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.starter.listerner.IGameStartListener;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.INotificationBoardCallerAndCallback;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;


/**
 * @author hundun
 * Created on 2021/11/12
 */
public class EventManager {
    List<IGameStartListener> gameStartListeners = new ArrayList<>();
    List<IBuffChangeListener> buffChangeListeners = new ArrayList<>();
    List<IAchievementStateChangeListener> achievementStateChangeListeners = new ArrayList<>();
    List<INotificationBoardCallerAndCallback> notificationBoardCallerAndCallbacks = new ArrayList<>();
    List<IOneFrameResourceChangeListener> oneFrameResourceChangeListeners = new ArrayList<>();
    List<IConstructionCollectionListener> constructionCollectionListeners = new ArrayList<>();
    IdleGameplayContext gameContext;


    public EventManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
    }
    
    public void registerListener(Object listener) {
        if (listener instanceof IGameStartListener && !gameStartListeners.contains(listener)) {
            gameStartListeners.add((IGameStartListener) listener);
        }
        if (listener instanceof IBuffChangeListener && !buffChangeListeners.contains(listener)) {
            buffChangeListeners.add((IBuffChangeListener) listener);
        }
        if (listener instanceof IAchievementStateChangeListener && !achievementStateChangeListeners.contains(listener)) {
            achievementStateChangeListeners.add((IAchievementStateChangeListener) listener);
        }
        if (listener instanceof INotificationBoardCallerAndCallback && !notificationBoardCallerAndCallbacks.contains(listener))
        {
            notificationBoardCallerAndCallbacks.add((INotificationBoardCallerAndCallback)listener);
        }
        if (listener instanceof IOneFrameResourceChangeListener && !oneFrameResourceChangeListeners.contains(listener)) {
            oneFrameResourceChangeListeners.add((IOneFrameResourceChangeListener) listener);
        }
        if (listener instanceof IConstructionCollectionListener && !constructionCollectionListeners.contains(listener))
        {
            constructionCollectionListeners.add((IConstructionCollectionListener)listener);
        }
    }

    public void unregisterListener(Object listener)
    {
        if (listener instanceof IGameStartListener) {
            gameStartListeners.remove((IGameStartListener) listener);
        }
        if (listener instanceof IBuffChangeListener)
        {
            buffChangeListeners.remove((IBuffChangeListener)listener);
        }
        if (listener instanceof IAchievementBoardCallback)
        {
            achievementStateChangeListeners.remove((IAchievementStateChangeListener)listener);
        }
        if (listener instanceof INotificationBoardCallerAndCallback)
        {
            notificationBoardCallerAndCallbacks.remove((INotificationBoardCallerAndCallback)listener);
        }
        if (listener instanceof IOneFrameResourceChangeListener)
        {
            oneFrameResourceChangeListeners.remove((IOneFrameResourceChangeListener)listener);
        }
        if (listener instanceof IConstructionCollectionListener)
        {
            constructionCollectionListeners.remove((IConstructionCollectionListener)listener);
        }
    }

    public void notifyGameStart() {
        gameContext.getFrontEnd().log(this.getClass().getSimpleName(), "notifyGameStart");
        for (IGameStartListener listener : gameStartListeners) {
            listener.onGameStart();
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

    public void notifyOneFrameResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        //Gdx.app.log(this.getClass().getSimpleName(), "notifyOneFrameResourceChange");
        for (IOneFrameResourceChangeListener listener : oneFrameResourceChangeListeners) {
            listener.onResourceChange(changeMap, deltaHistoryMap);
        }
    }

    public void notifyAchievementComplete(AbstractAchievement achievement, AchievementState state) {
        gameContext.getFrontEnd().log(this.getClass().getSimpleName(), "notifyAchievementUnlock");
        for (IAchievementStateChangeListener listener : achievementStateChangeListeners) {
            listener.onAchievementStateChange(achievement, state);
        }
    }

    public void notifyNotification(String data)
    {
        gameContext.getFrontEnd().log(this.getClass().getSimpleName(), "notifyNotification");
        for (INotificationBoardCallerAndCallback listener : notificationBoardCallerAndCallbacks)
        {
            listener.showNotificationMaskBoard(data);
        }
    }

    public void notifyConstructionCollectionChange()
    {
        //Gdx.app.log(this.getClass().getSimpleName(), "notifyOneFrameResourceChange");
        for (IConstructionCollectionListener listener : constructionCollectionListeners)
        {
            listener.onConstructionCollectionChange();
        }
    }
}
