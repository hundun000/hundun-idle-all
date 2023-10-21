package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoScreenContext extends AbstractIdleScreenContext<IdleMushroomGame, RootSaveData> {

    DemoMenuScreen menuScreen;
    MainPlayScreen mainPlayScreen;
    WorldPlayScreen worldPlayScreen;
    DemoAchievementScreen achievementScreen;



    public static PlayScreenLayoutConst customLayoutConst(IdleMushroomGame game) {
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

    public DemoScreenContext(IdleMushroomGame game) {
        super(game);
    }

    @Override
    public void lazyInit() {
        this.menuScreen = new DemoMenuScreen(game);
        this.mainPlayScreen = new MainPlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);
        this.achievementScreen = new DemoAchievementScreen(game);

        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MENU, menuScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MAIN, mainPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_WORLD, worldPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_ACHIEVEMENT, achievementScreen);
    }

}
