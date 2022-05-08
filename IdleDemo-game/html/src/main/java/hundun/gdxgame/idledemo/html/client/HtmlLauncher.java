package hundun.gdxgame.idledemo.html.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.html.GwtPreferencesSaveTool;

public class HtmlLauncher extends GwtApplication {
    
    IdleDemoGame game;
    
    public HtmlLauncher() {
        this.game = new IdleDemoGame(new GwtPreferencesSaveTool("IdleDemo-html-save"));
    }

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(game.LOGIC_WIDTH, game.LOGIC_HEIGHT);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return game;
    }
}