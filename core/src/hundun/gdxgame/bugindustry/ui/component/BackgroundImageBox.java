package hundun.gdxgame.bugindustry.ui.component;
/**
 * @author hundun
 * Created on 2021/11/29
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.ui.IGameAreaChangeListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

public class BackgroundImageBox extends Container<Image> implements IGameAreaChangeListener{
    GameScreen parent;
    Map<GameArea, Drawable> imageMap = new HashMap<>(); 
    
    public BackgroundImageBox(GameScreen parent) {
        this.parent = parent;
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        for (GameArea gameArea : GameArea.values()) {
            imageMap.put(gameArea, new SpriteDrawable(new Sprite(parent.game.getTextureManager().getBackgroundTexture(gameArea))));
        }
    }

    @Override
    public void onGameAreaChange(GameArea last, GameArea current) {
        if (parent.game.drawGameImageAndPlayAudio) {
            Drawable image = imageMap.get(current);
            this.setBackground(image);
        }
    }

}
