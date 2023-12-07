package hundun.gdxgame.idlemushroom.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/18
 */
public class IdleMushroomAudioPlayManager implements Disposable {

    long currentBgmId;
    Sound currentBgmSound;

    IdleMushroomGame game;

    Map<String, Sound> screenIdToSoundMap = new HashMap<>();

    public IdleMushroomAudioPlayManager(IdleMushroomGame game) {
        this.game = game;
    }

    public void lazyInitOnGameCreate(Map<String, String> screenIdToFilePathMap) {
        if (screenIdToFilePathMap != null) {
            screenIdToFilePathMap.forEach((k, v) -> {
                try {
                    screenIdToSoundMap.put(k, Gdx.audio.newSound(Gdx.files.internal(v)));
                } catch (Exception e) {
                    Gdx.app.log("AudioPlayManager", "init error for " + v + ": " + e.getMessage());
                }
            });
        }
    }


    public void intoScreen(String screenId) {
        if (screenIdToSoundMap.containsKey(screenId)) {
            setScreenBgm(screenIdToSoundMap.get(screenId));
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
        screenIdToSoundMap.values().forEach(v -> v.dispose());
    }
}
