package hundun.gdxgame.idleshare.framework.callback;

import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementUnlockCallback {
    void hideAchievementMaskBoard();
    void onAchievementUnlock(AchievementPrototype prototype);
}
