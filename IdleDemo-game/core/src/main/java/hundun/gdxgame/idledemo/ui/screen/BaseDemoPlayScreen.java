package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.Gdx;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.sub.FirstRunningAchievementBoardVM;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.sub.AchievementMaskBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDemoPlayScreen extends BaseIdleScreen<DemoIdleGame, RootSaveData>
        implements IGameAreaChangeListener, IAchievementBoardCallback, IAchievementStateChangeListener
{

    protected FirstRunningAchievementBoardVM<DemoIdleGame, RootSaveData> firstRunningAchievementBoardVM;
    protected AchievementMaskBoard achievementMaskBoard;
    DemoIdleGame demoIdleGame;
    protected List<AbstractAchievement> showAchievementMaskBoardQueue = new ArrayList<>();

    public BaseDemoPlayScreen(DemoIdleGame game, String screenId) {
        super(game, screenId, DemoScreenContext.customLayoutConst(game));
        this.demoIdleGame = game;
    }





    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        gameAreaChangeListeners.add(this);
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();


        firstRunningAchievementBoardVM = new FirstRunningAchievementBoardVM<>(this);
        leftSideGroup.add(firstRunningAchievementBoardVM)
                .width(getLayoutConst().FIRST_LOCKED_ACHIEVEMENT_BOARD_WIDTH)
                .height(getLayoutConst().FIRST_LOCKED_ACHIEVEMENT_BOARD_HEIGHT)
                .left()
                .top()
        ;
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        firstRunningAchievementBoardVM.updateData();
    }

    public abstract void onCellClicked(HexCellVM vm);

    @Override
    protected void onLogicFrame() {
        super.onLogicFrame();

        if (showAchievementMaskBoardQueue.size() > 0) {
            AbstractAchievement achievement = showAchievementMaskBoardQueue.remove(0);

            game.getFrontend().log(this.getClass().getSimpleName(), "onAchievementUnlock called");
            achievementMaskBoard.setAchievementPrototype(achievement);
            achievementMaskBoard.setVisible(true);
            Gdx.input.setInputProcessor(popupUiStage);
            logicFrameHelper.setLogicFramePause(true);
        }
    }

    @Override
    public void hideAchievementMaskBoard() {
        game.getFrontend().log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(provideDefaultInputProcessor());
        logicFrameHelper.setLogicFramePause(false);
    }

    @Override
    public void showAchievementMaskBoard(AbstractAchievement achievement) {
        if (this.hidden) {
            return;
        }
        showAchievementMaskBoardQueue.add(achievement);
    }

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();


        achievementMaskBoard = new AchievementMaskBoard(
                this,
                game.getIdleGameplayExport()
                        .getGameplayContext()
                        .getGameDictionary()
                        .getAchievementTexts(game.getIdleGameplayExport().getLanguage())
        );
        popupUiStage.addActor(achievementMaskBoard);


    }

    @Override
    public void onAchievementStateChange(AbstractAchievement achievement, AchievementState state) {
        if (state == AchievementState.COMPLETED) {
            showAchievementMaskBoard(achievement);
            firstRunningAchievementBoardVM.updateData();
        } else if (state == AchievementState.RUNNING) {
            firstRunningAchievementBoardVM.updateData();
        }
    }
}
