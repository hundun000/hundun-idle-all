package hundun.gdxgame.idledemo.html.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import hundun.gdxgame.idledemo.IdleDemoGame;


public class HtmlLauncher extends GwtApplication {
    
    IdleDemoGame game;
    
    public HtmlLauncher() {
        this.game = new IdleDemoGame(new GwtPreferencesSaveTool("IdleDemo-html-save"));
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