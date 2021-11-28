package hundun.gdxgame.bugindustry.model.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;

public class TextureManager {
    @Getter
    private Texture backgroundTexture;
    
    public TextureManager() {
        backgroundTexture = new Texture(Gdx.files.internal("640x480.png"));
    }
}
