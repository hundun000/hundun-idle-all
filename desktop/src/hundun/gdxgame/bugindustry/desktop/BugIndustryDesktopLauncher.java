package hundun.gdxgame.bugindustry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hundun.gdxgame.bugindustry.BugIndustryGame;

public class BugIndustryDesktopLauncher {
	public static void main (String[] arg) {
		double scale = 1;
	    
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (BugIndustryGame.LOGIC_WIDTH * scale);
		config.height = (int) (BugIndustryGame.LOGIC_HEIGHT * scale);
		new LwjglApplication(new BugIndustryGame(), config);
	}
}
