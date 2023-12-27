package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.Gdx;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.ProxyManager.ProxyState;
import hundun.gdxgame.idlemushroom.ui.achievement.AchievementPopupBoard;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.world.HexCellVM;
import hundun.gdxgame.idlemushroom.ui.main.FirstRunningAchievementBoardVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementStateChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievementPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseIdleMushroomPlayScreen extends BaseIdleMushroomScreen
        implements IGameAreaChangeListener,
        IAchievementBoardCallback,
        IAchievementStateChangeListener
{

    protected FirstRunningAchievementBoardVM firstRunningAchievementBoardVM;
    protected AchievementPopupBoard achievementPopupBoard;
    IdleMushroomGame idleMushroomGame;
    protected List<AbstractAchievementPrototype> showAchievementMaskBoardQueue = new ArrayList<>();
    @Getter
    protected IdleMushroomPlayScreenLayoutConst idleMushroomPlayScreenLayoutConst;
    public BaseIdleMushroomPlayScreen(IdleMushroomGame game, String screenId) {
        super(game, screenId, game.getIdleMushroomPlayScreenLayoutConst());
        this.idleMushroomPlayScreenLayoutConst = game.getIdleMushroomPlayScreenLayoutConst();
        this.idleMushroomGame = game;
    }





    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        gameAreaChangeListeners.add(this);
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();


        firstRunningAchievementBoardVM = new FirstRunningAchievementBoardVM(this);
        leftSideGroup.add(firstRunningAchievementBoardVM)
                .width(getLayoutConst().FIRST_LOCKED_ACHIEVEMENT_BOARD_WIDTH)
                .growY()
        ;
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        firstRunningAchievementBoardVM.updateData();
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
        if (this.hidden || game.getProxyManager().getProxyState() == ProxyState.RUNNING) {
            return;
        }
        showAchievementMaskBoardQueue.add(achievement);
    }

    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        super.lazyInitBackUiAndPopupUiContent();


        achievementPopupBoard = new AchievementPopupBoard(
                this,
                game.getIdleMushroomGameDictionary()
                        .getAchievementTexts(game.getIdleGameplayExport().getLanguage())
        );
        popupUiStage.addActor(achievementPopupBoard);


    }

    @Override
    public void onAchievementStateChange(AbstractAchievementPrototype achievement, AchievementState state) {
        if (state == AchievementState.COMPLETED) {
            showAchievementMaskBoard(achievement);
            firstRunningAchievementBoardVM.updateData();
        } else if (state == AchievementState.RUNNING) {
            firstRunningAchievementBoardVM.updateData();
        }
    }
}
