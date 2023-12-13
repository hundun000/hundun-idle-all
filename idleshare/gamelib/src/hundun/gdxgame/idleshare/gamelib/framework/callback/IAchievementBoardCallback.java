package hundun.gdxgame.idleshare.gamelib.framework.callback;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementBoardCallback {
    void hideAchievementMaskBoard();
    void showAchievementMaskBoard(AbstractAchievementPrototype prototype);
}
