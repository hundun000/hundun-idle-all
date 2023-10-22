package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.achievement.AllAchievementBoardVM;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;

public class DemoAchievementScreen extends BaseIdleMushroomScreen implements IAchievementStateChangeListener {

    AllAchievementBoardVM<IdleMushroomGame, RootSaveData> allAchievementBoardVM;



    public DemoAchievementScreen(IdleMushroomGame game) {
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

        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
    }

    @Override
    public void onAchievementStateChange(AbstractAchievement achievement, AchievementState state) {
        allAchievementBoardVM.updateData();
    }
}
