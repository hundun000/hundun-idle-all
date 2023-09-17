package hundun.gdxgame.idleshare.core.starter.ui.component;
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

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;

public class BackgroundImageBox<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Container<Image> implements IGameAreaChangeListener{
    BaseIdleScreen<T_GAME, T_SAVE> parent;

    public BackgroundImageBox(BaseIdleScreen<T_GAME, T_SAVE> parent) {
        this.parent = parent;
        this.setFillParent(true);
        //this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void onGameAreaChange(String last, String current) {
        Drawable image = new TextureRegionDrawable(parent.getGame().getTextureManager().getBackgroundTexture(current));
        this.setBackground(image);
    }

}
