package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.sub.FirstLockedAchievementBoardVM;
import hundun.gdxgame.idledemo.ui.world.HexCellVM;
import hundun.gdxgame.idledemo.ui.sub.AchievementMaskBoard;
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IAchievementUnlockCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;

import java.util.Map;

public abstract class BaseDemoPlayScreen extends BaseIdleScreen<DemoIdleGame, RootSaveData>
        implements IGameAreaChangeListener, IAchievementUnlockCallback
{

    protected FirstLockedAchievementBoardVM<DemoIdleGame, RootSaveData> firstLockedAchievementBoardVM;
    protected AchievementMaskBoard achievementMaskBoard;

    public BaseDemoPlayScreen(DemoIdleGame game) {
        super(game, customLayoutConst(game));
    }

    private static PlayScreenLayoutConst customLayoutConst(DemoIdleGame game) {
        PlayScreenLayoutConst layoutConst = new PlayScreenLayoutConst(game.getWidth(), game.getHeight());
        NinePatch ninePatch = new NinePatch(game.getTextureManager().getDefaultBoardNinePatchTexture(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize()
        );
        layoutConst.simpleBoardBackground = new NinePatchDrawable(ninePatch);
        layoutConst.simpleBoardBackgroundMiddle = new TextureRegionDrawable(game.getTextureManager().getDefaultBoardNinePatchMiddle());
        return layoutConst;
    }

    Map<String, String> areaToScreenIdMap = JavaFeatureForGwt.mapOf(
            GameArea.AREA_COOKIE, CookiePlayScreen.class.getSimpleName(),
            GameArea.AREA_FOREST, WorldPlayScreen.class.getSimpleName()
    );

    @Override
    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();

        gameAreaChangeListeners.add(this);
        gameAreaChangeListeners.add(firstLockedAchievementBoardVM);
    }

    @Override
    protected void lazyInitUiRootContext() {
        super.lazyInitUiRootContext();

        Table rightSideGroup = new Table();

        firstLockedAchievementBoardVM = new FirstLockedAchievementBoardVM<>(this);
        rightSideGroup.add(firstLockedAchievementBoardVM)
                .width(getLayoutConst().FIRST_LOCKED_ACHIEVEMENT_BOARD_WIDTH)
                .height(getLayoutConst().FIRST_LOCKED_ACHIEVEMENT_BOARD_HEIGHT)
                .row();

        gameAreaControlBoard = new GameAreaControlBoard<>(this);
        rightSideGroup.add(gameAreaControlBoard).expand().right();

        uiRootTable.add(rightSideGroup).expand().right().row();
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        String lastScreen = areaToScreenIdMap.get(last);
        String currentScreen = areaToScreenIdMap.get(current);

        if (lastScreen != null && !currentScreen.equals(lastScreen))
        {
            game.getScreenManager().pushScreen(currentScreen, null);
            game.getAudioPlayManager().intoScreen(currentScreen);
        }
    }

    public abstract void onDeskClicked(HexCellVM vm);

    @Override
    public void hideAchievementMaskBoard() {
        game.getFrontend().log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(provideDefaultInputProcessor());
        logicFrameHelper.setLogicFramePause(false);
    }

    @Override
    public void showAchievementMaskBoard(AbstractAchievement prototype) {
        if (this.hidden) {
            return;
        }
        game.getFrontend().log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        logicFrameHelper.setLogicFramePause(true);
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
}
