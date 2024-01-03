package hundun.gdxgame.idlemushroom.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hundun.gdxgame.idlemushroom.IdleMushroomGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    IdleMushroomGame game = new IdleMushroomGame(
				new PreferencesSaveTool("IdleMushroom-desktop-save.xml"),
				new DesktopProxyManagerCallback()
		);
   
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.getWidth());
		config.height = (int) (game.getHeight());
		new LwjglApplication(game, config);
	}
}
