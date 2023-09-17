package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.sub.AllAchievementBoardVM;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import lombok.Setter;

public class DemoAchievementScreen extends BaseIdleScreen<DemoIdleGame, RootSaveData> {

    AllAchievementBoardVM<DemoIdleGame, RootSaveData> allAchievementBoardVM;

    Button backButton;

    @Setter
    String lastScreenId;

    public DemoAchievementScreen(DemoIdleGame game) {
        super(game, DemoScreenContext.customLayoutConst(game));
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
        backButton = new TextButton("back", game.getMainSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreenManager().pushScreen(lastScreenId, null);
                game.getAudioPlayManager().intoScreen(lastScreenId);
            }
        });

        uiRootTable.add(allAchievementBoardVM).expand().row();
        uiRootTable.add(backButton);
    }

    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
    }
}
