package hundun.gdxgame.idledemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hundun.gdxgame.idledemo.DemoIdleGame;



public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    DemoIdleGame game = new DemoIdleGame(new PreferencesSaveTool("IdleMushroom-destop-save.xml"));
   
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.getWidth());
		config.height = (int) (game.getHeight());
		new LwjglApplication(game, config);
	}
}
