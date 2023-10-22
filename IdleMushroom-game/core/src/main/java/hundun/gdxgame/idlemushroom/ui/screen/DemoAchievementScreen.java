package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.InputProcessor;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.logic.ResourceType;
import hundun.gdxgame.idlemushroom.ui.achievement.AllAchievementBoardVM;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;

public class DemoAchievementScreen extends BaseIdleMushroomScreen implements IAchievementStateChangeListener {

    AllAchievementBoardVM allAchievementBoardVM;



    public DemoAchievementScreen(IdleMushroomGame game) {
        super(game, DemoScreenId.SCREEN_ACHIEVEMENT, IdleMushroomPlayScreenLayoutConst.customLayoutConst(game));
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

        allAchievementBoardVM = new AllAchievementBoardVM(this);


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
