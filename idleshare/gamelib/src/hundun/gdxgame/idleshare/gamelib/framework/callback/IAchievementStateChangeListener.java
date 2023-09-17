package hundun.gdxgame.idleshare.gamelib.framework.callback;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementStateChangeListener {
    void onAchievementStateChange(AbstractAchievement achievement, AchievementState state);
}
