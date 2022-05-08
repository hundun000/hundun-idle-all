package hundun.gdxgame.idledemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idlehelper.desktop.DesktopExitHookTask;
import hundun.gdxgame.idlehelper.desktop.PreferencesSaveTool;



public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    IdleDemoGame game = new IdleDemoGame(new PreferencesSaveTool("IdleDemo-destop-save"));
	    Runtime.getRuntime().addShutdownHook(new DesktopExitHookTask(game));
	    
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.LOGIC_WIDTH * game.desktopScale);
		config.height = (int) (game.LOGIC_HEIGHT * game.desktopScale);
		new LwjglApplication(game, config);
	}
}
