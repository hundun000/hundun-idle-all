package hundun.gdxgame.bugindustry.desktop;
/**
 * @author hundun
 * Created on 2022/04/08
 */

import hundun.gdxgame.idleframe.BaseIdleGame;

public class DesktopExitHookTask extends Thread {

    BaseIdleGame game;

    public DesktopExitHookTask(BaseIdleGame game) {
        super();
        this.game = game;
    }
    
    @Override
    public void run() {
        game.getSaveTool().save(game.getModelContext());
    }

}
