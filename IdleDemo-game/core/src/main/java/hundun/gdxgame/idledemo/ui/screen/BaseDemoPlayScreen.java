package hundun.gdxgame.idledemo.ui.screen;

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
import hundun.gdxgame.idleshare.core.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;

import java.util.Map;

public abstract class BaseDemoPlayScreen extends BaseIdlePlayScreen<DemoIdleGame, RootSaveData> implements IGameAreaChangeListener {

    protected FirstLockedAchievementBoardVM<DemoIdleGame, RootSaveData> firstLockedAchievementBoardVM;

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
        rightSideGroup.add(firstLockedAchievementBoardVM).expandX().height(200).row();

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
}
