package hundun.gdxgame.idledemo.ui.screen;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idlestarter.ui.screen.menu.MenuScreen;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class ScreenContext {
    MenuScreen<IdleDemoGame> menuScreen;
    PlayScreen gameBeeScreen;
}
