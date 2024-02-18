package hundun.gdxgame.idleshare.gamelib.framework.callback;

import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;

/**
 * @author hundun
 * Created on 2022/01/21
 */
public interface IAchievementStateChangeListener {
    void onAchievementStateChange(AbstractAchievementPrototype achievement, AchievementState state);
}
