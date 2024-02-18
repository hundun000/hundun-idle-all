package hundun.gdxgame.idledemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hundun.gdxgame.idledemo.IdleDemoGame;



public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    IdleDemoGame game = new IdleDemoGame(new PreferencesSaveTool("IdleDemo-destop-save.xml"));
   
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.getWidth());
		config.height = (int) (game.getHeight());
		new LwjglApplication(game, config);
	}
}
