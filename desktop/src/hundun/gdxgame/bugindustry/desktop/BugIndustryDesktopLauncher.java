package hundun.gdxgame.bugindustry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hundun.gdxgame.bugindustry.BugIndustryGame;

public class BugIndustryDesktopLauncher {
	public static void main (String[] arg) {
		
	    
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (BugIndustryGame.LOGIC_WIDTH * BugIndustryGame.scale);
		config.height = (int) (BugIndustryGame.LOGIC_HEIGHT * BugIndustryGame.scale);
		new LwjglApplication(new BugIndustryGame(), config);
	}
}
