package hundun.gdxgame.idleshare.starter.ui.component;
/**
 * @author hundun
 * Created on 2021/11/29
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.corelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.starter.ui.screen.play.BaseIdlePlayScreen;

public class BackgroundImageBox<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Container<Image> implements IGameAreaChangeListener{
    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;
    Map<String, Drawable> imageMap = new HashMap<>();

    public BackgroundImageBox(BaseIdlePlayScreen<T_GAME, T_SAVE> parent) {
        this.parent = parent;
        this.setFillParent(true);
        //this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        for (String gameArea : parent.getGame().getGameAreaValues()) {
            imageMap.put(gameArea, new SpriteDrawable(new Sprite(parent.getGame().getTextureManager().getBackgroundTexture(gameArea))));
        }

    }

    @Override
    public void onGameAreaChange(String last, String current) {
        Drawable image = imageMap.get(current);
        this.setBackground(image);
    }

}
