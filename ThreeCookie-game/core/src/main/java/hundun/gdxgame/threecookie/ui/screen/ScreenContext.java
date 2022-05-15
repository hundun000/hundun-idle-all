package hundun.gdxgame.threecookie.ui.screen;

import hundun.gdxgame.idleshare.starter.ui.screen.menu.MenuScreen;
import hundun.gdxgame.threecookie.IdleDemoGame;

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
