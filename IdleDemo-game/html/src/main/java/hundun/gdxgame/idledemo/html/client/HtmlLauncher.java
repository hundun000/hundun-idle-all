package hundun.gdxgame.idledemo.html.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import hundun.gdxgame.idledemo.DemoIdleGame;


public class HtmlLauncher extends GwtApplication {
    
    DemoIdleGame game;
    
    public HtmlLauncher() {
        this.game = new DemoIdleGame(new GwtPreferencesSaveTool("IdleDemo-html-save"));
    }

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(game.getWidth(), game.getHeight());
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return game;
    }
}