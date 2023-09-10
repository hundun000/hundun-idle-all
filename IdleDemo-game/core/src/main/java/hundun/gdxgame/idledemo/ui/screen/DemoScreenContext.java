package hundun.gdxgame.idledemo.ui.screen;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class DemoScreenContext extends AbstractIdleScreenContext<DemoIdleGame, RootSaveData> {

    DemoMenuScreen menuScreen;
    CookiePlayScreen cookiePlayScreen;
    WorldPlayScreen worldPlayScreen;
    
    public DemoScreenContext(DemoIdleGame game) {
        super(game);
    }

    @Override
    public void lazyInit() {
        this.menuScreen = new DemoMenuScreen(game);
        this.cookiePlayScreen = new CookiePlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);

        game.getScreenManager().addScreen(menuScreen.getClass().getSimpleName(), menuScreen);
        game.getScreenManager().addScreen(cookiePlayScreen.getClass().getSimpleName(), cookiePlayScreen);
        game.getScreenManager().addScreen(worldPlayScreen.getClass().getSimpleName(), worldPlayScreen);
    }

}
