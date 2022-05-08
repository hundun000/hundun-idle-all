package hundun.gdxgame.bugindustry.ui.screen;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.idlestarter.ui.screen.menu.MenuScreen;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Data
public class ScreenContext {
    MenuScreen<BugIndustryGame> menuScreen;
    PlayScreen gameBeeScreen;
}
