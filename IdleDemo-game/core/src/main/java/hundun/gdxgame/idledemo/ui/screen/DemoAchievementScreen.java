package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.sub.AllAchievementBoardVM;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import lombok.Setter;

public class DemoAchievementScreen extends BaseIdleScreen<DemoIdleGame, RootSaveData> {

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


        leftSideGroup.add(allAchievementBoardVM).expand().row();

    }

    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
    }
}
