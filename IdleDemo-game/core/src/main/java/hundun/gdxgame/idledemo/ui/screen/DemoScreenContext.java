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
    DemoPlayScreen playScreen;
    
    
    public DemoScreenContext(DemoIdleGame game) {
        super(game);
    }

    @Override
    public void lazyInit() {
        this.menuScreen = new DemoMenuScreen(game);
        this.playScreen = new DemoPlayScreen(game);
        
        game.getScreenManager().addScreen(menuScreen.getClass().getSimpleName(), menuScreen);
        game.getScreenManager().addScreen(playScreen.getClass().getSimpleName(), playScreen);
    }

}
