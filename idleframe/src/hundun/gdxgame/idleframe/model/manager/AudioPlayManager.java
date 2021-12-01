package hundun.gdxgame.idleframe.model.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;

import hundun.gdxgame.idleframe.BaseIdleGame;

/**
 * @author hundun
 * Created on 2021/11/18
 */
public class AudioPlayManager implements Disposable {
    
    long currentBgmId;
    Sound currentBgmSound;
    
    Sound menuBgmSound;
    Sound gameBgmSound;
    
    BaseIdleGame game;
    
    public AudioPlayManager(BaseIdleGame game) {
        this.game = game;
        menuBgmSound = Gdx.audio.newSound(Gdx.files.internal("audio/Loop-Menu.wav"));
        gameBgmSound = Gdx.audio.newSound(Gdx.files.internal("audio/forest.mp3"));
    }
    
    public void intoMenu() {
        if (game.drawGameImageAndPlayAudio) {
            setScreenBgm(menuBgmSound);
        }
        
    }
    
    public void intoGame() {
        if (game.drawGameImageAndPlayAudio) {
            setScreenBgm(gameBgmSound);
        }
        
    }
    
    
    private void setScreenBgm(Sound bgmSound) {
        if (currentBgmSound != null) {
            currentBgmSound.stop(currentBgmId);
        }
        currentBgmId = bgmSound.loop();
        currentBgmSound = bgmSound;
    }

    @Override
    public void dispose() {
        menuBgmSound.dispose();
        gameBgmSound.dispose();
    }
}
