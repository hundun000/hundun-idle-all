package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.Gdx;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.achievement.AchievementPopupBoard;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDemoPlayScreen extends BaseIdleDemoScreen
        implements IGameAreaChangeListener, IAchievementBoardCallback, IAchievementStateChangeListener
{


    protected AchievementPopupBoard achievementPopupBoard;
    IdleDemoGame idleDemoGame;
    protected List<AbstractAchievementPrototype> showAchievementMaskBoardQueue = new ArrayList<>();

    public BaseDemoPlayScreen(IdleDemoGame game, String screenId) {
        super(game, screenId, DemoScreenContext.customLayoutConst(game));
        this.idleDemoGame = game;
    }





    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        gameAreaChangeListeners.add(this);
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();


    }

    @Override
    public void onGameAreaChange(String last, String current) {

    }

    public abstract void onCellClicked(HexCellVM vm);

    @Override
    public void onLogicFrame() {

        if (showAchievementMaskBoardQueue.size() > 0) {
            AbstractAchievementPrototype achievement = showAchievementMaskBoardQueue.remove(0);

            game.getFrontend().log(this.getClass().getSimpleName(), "onAchievementUnlock called");
            achievementPopupBoard.setAchievementPrototype(achievement);
            achievementPopupBoard.setVisible(true);
            Gdx.input.setInputProcessor(popupUiStage);
            game.getLogicFrameHelper().setLogicFramePause(true);
        }
    }

    @Override
    public void hideAchievementMaskBoard() {
        game.getFrontend().log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementPopupBoard.setVisible(false);
        Gdx.input.setInputProcessor(provideDefaultInputProcessor());
        game.getLogicFrameHelper().setLogicFramePause(false);
    }

    @Override
    public void showAchievementMaskBoard(AbstractAchievementPrototype achievement) {
        if (this.hidden) {
            return;
        }
        showAchievementMaskBoardQueue.add(achievement);
    }

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();


        achievementPopupBoard = new AchievementPopupBoard(
                this,
                game.getDemoGameDictionary()
                        .getAchievementTexts(game.getIdleGameplayExport().getLanguage())
        );
        popupUiStage.addActor(achievementPopupBoard);


    }

    @Override
    public void onAchievementStateChange(AbstractAchievementPrototype achievement, AchievementState state) {

    }
}
