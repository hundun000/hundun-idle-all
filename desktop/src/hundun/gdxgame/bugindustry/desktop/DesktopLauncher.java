package hundun.gdxgame.bugindustry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hundun.gdxgame.bugindustry.BugIndustryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    BugIndustryGame game = new BugIndustryGame();
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.LOGIC_WIDTH * BugIndustryGame.desktopScale);
		config.height = (int) (game.LOGIC_HEIGHT * BugIndustryGame.desktopScale);
		new LwjglApplication(game, config);
	}
}
