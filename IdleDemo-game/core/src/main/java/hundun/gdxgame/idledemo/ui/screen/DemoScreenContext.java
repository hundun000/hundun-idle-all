package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.ui.shared.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoScreenContext {
    protected IdleDemoGame game;
    DemoMenuScreen menuScreen;
    MainPlayScreen mainPlayScreen;
    WorldPlayScreen worldPlayScreen;
    DemoAchievementScreen achievementScreen;



    public static PlayScreenLayoutConst customLayoutConst(IdleDemoGame game) {
        PlayScreenLayoutConst layoutConst = new PlayScreenLayoutConst(game.getWidth(), game.getHeight());
        NinePatch ninePatch = new NinePatch(game.getTextureManager().getDefaultBoardNinePatchTexture(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(),
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize()
        );
        return layoutConst;
    }

    public DemoScreenContext(IdleDemoGame game) {
        this.game = game;
    }

    public void lazyInit() {
        this.menuScreen = new DemoMenuScreen(game);
        this.mainPlayScreen = new MainPlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);
        this.achievementScreen = new DemoAchievementScreen(game);

        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MENU, menuScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_COOKIE, mainPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_WORLD, worldPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_ACHIEVEMENT, achievementScreen);
    }

}
