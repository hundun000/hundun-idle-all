package hundun.gdxgame.threecookie.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hundun.gdxgame.idleshare.desktop.DesktopExitHookTask;
import hundun.gdxgame.idleshare.desktop.PreferencesSaveTool;
import hundun.gdxgame.threecookie.IdleDemoGame;



public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    IdleDemoGame game = new IdleDemoGame(new PreferencesSaveTool("ThreeCookie-destop-save"));
	    Runtime.getRuntime().addShutdownHook(new DesktopExitHookTask(game));
	    
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.LOGIC_WIDTH * game.desktopScale);
		config.height = (int) (game.LOGIC_HEIGHT * game.desktopScale);
		new LwjglApplication(game, config);
	}
}
