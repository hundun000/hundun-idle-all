package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.starter.ui.screen.menu.BaseIdleMenuScreen;

/**
 * @author hundun
 * Created on 2023/02/16
 */
public class DemoMenuScreen extends BaseIdleMenuScreen<IdleMushroomGame, RootSaveData> {

    public DemoMenuScreen(IdleMushroomGame game) {
        super(game, 
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(true);
                        game.getScreenManager().pushScreen(DemoScreenId.SCREEN_MAIN, null);
                        game.getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_MAIN);
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
                        game.getScreenManager().pushScreen(DemoScreenId.SCREEN_MAIN, null);
                        game.getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_MAIN);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
                );
    }

}
