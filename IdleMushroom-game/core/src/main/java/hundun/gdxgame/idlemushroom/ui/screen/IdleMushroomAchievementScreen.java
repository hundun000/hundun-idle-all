package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.InputProcessor;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomScreenId;
import hundun.gdxgame.idlemushroom.logic.id.ResourceType;
import hundun.gdxgame.idlemushroom.ui.achievement.AllAchievementBoardVM;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;

public class IdleMushroomAchievementScreen extends BaseIdleMushroomScreen implements IAchievementStateChangeListener {

    AllAchievementBoardVM allAchievementBoardVM;



    public IdleMushroomAchievementScreen(IdleMushroomGame game) {
        super(game, IdleMushroomScreenId.SCREEN_ACHIEVEMENT, game.getIdleMushroomPlayScreenLayoutConst());
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
        buffInfoBoard.lazyInit(IdleMushroomBuffId.VALUES_FOR_SHOW_ORDER);
    }

    @Override
    public void onAchievementStateChange(AbstractAchievementPrototype achievement, AchievementState state) {
        allAchievementBoardVM.updateData();
    }

    @Override
    public void onLogicFrame() {

    }
}
