package hundun.gdxgame.idledemo.ui.shared;
/**
 * @author hundun
 * Created on 2021/11/29
 */

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;

public class BackgroundImageBox extends Container<Image> implements IGameAreaChangeListener{
    BaseIdleDemoScreen parent;

    public BackgroundImageBox(BaseIdleDemoScreen parent) {
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
