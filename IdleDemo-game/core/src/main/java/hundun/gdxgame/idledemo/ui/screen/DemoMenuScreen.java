package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.DemoScreenId;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.starter.ui.screen.menu.BaseIdleMenuScreen;

/**
 * @author hundun
 * Created on 2023/02/16
 */
public class DemoMenuScreen extends BaseIdleMenuScreen<DemoIdleGame, RootSaveData> {

    public DemoMenuScreen(DemoIdleGame game) {
        super(game, 
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(true);
                        game.getScreenManager().pushScreen(DemoScreenId.SCREEN_COOKIE, null);
                        game.getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_COOKIE);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }, 
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(false);
                        game.getScreenManager().pushScreen(DemoScreenId.SCREEN_COOKIE, null);
                        game.getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_COOKIE);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
                );
    }

}
