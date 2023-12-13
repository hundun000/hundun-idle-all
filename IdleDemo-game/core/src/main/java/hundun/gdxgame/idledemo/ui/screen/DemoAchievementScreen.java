package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.achievement.AllAchievementBoardVM;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;

public class DemoAchievementScreen extends BaseIdleDemoScreen implements IAchievementStateChangeListener {

    AllAchievementBoardVM<DemoIdleGame, RootSaveData> allAchievementBoardVM;



    public DemoAchievementScreen(DemoIdleGame game) {
        super(game, DemoScreenId.SCREEN_ACHIEVEMENT, DemoScreenContext.customLayoutConst(game));
    }

    @Override
    protected void updateUIForShow() {

    }

    @Override
    protected InputProcessor provideDefaultInputProcessor() {
        return uiStage;
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        allAchievementBoardVM = new AllAchievementBoardVM<>(this);


        middleGroup.add(allAchievementBoardVM).expand().row();

    }

    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

    }

    @Override
    public void onAchievementStateChange(AbstractAchievementPrototype achievement, AchievementState state) {
        allAchievementBoardVM.updateData();
    }
}
