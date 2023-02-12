package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idleshare.starter.ui.screen.menu.AbstractMenuScreen;

/**
 * @author hundun
 * Created on 2023/02/16
 */
public class DemoMenuScreen extends AbstractMenuScreen<DemoIdleGame, RootSaveData> {

    public DemoMenuScreen(DemoIdleGame game) {
        super(game, 
                "IdleDemo", 
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameLoadOrNew(true);
                        game.getScreenManager().pushScreen(DemoPlayScreen.class.getSimpleName(), null);
                        game.getAudioPlayManager().intoScreen(DemoPlayScreen.class.getSimpleName());
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }, 
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameLoadOrNew(false);
                        game.getScreenManager().pushScreen(DemoPlayScreen.class.getSimpleName(), null);
                        game.getAudioPlayManager().intoScreen(DemoPlayScreen.class.getSimpleName());
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
                );
        // TODO Auto-generated constructor stub
    }

}
