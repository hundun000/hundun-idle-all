package hundun.gdxgame.idleshare.gamelib.framework.callback;

import hundun.gdxgame.idleshare.gamelib.framework.model.AchievementPrototype;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementUnlockCallback {
    void hideAchievementMaskBoard();
    void onAchievementUnlock(AchievementPrototype prototype);
}
