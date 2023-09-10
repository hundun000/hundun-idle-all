package hundun.gdxgame.idleshare.gamelib.framework.callback;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementUnlockCallback {
    void hideAchievementMaskBoard();
    void showAchievementMaskBoard(AbstractAchievement prototype);
}
