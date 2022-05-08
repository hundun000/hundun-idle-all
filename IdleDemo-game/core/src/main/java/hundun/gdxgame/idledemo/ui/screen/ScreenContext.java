package hundun.gdxgame.idledemo.ui.screen;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idlestarter.ui.screen.menu.MenuScreen;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class ScreenContext {
    MenuScreen<IdleDemoGame> menuScreen;
    PlayScreen gameBeeScreen;
    
    // ------ replace-lombok ------
    public MenuScreen<IdleDemoGame> getMenuScreen() {
        return menuScreen;
    }
    public void setMenuScreen(MenuScreen<IdleDemoGame> menuScreen) {
        this.menuScreen = menuScreen;
    }
    public PlayScreen getGameBeeScreen() {
        return gameBeeScreen;
    }
    public void setGameBeeScreen(PlayScreen gameBeeScreen) {
        this.gameBeeScreen = gameBeeScreen;
    }
    
    
}
