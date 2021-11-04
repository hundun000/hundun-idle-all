package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.ui.screen.GameBeeScreen;

/**
 * @author hundun
 * Created on 2021/11/04
 */
public class GameAreaChangeButton extends ImageButton {
    GameBeeScreen parent;
    boolean directRight;
    
    public static GameAreaChangeButton create(GameBeeScreen parent, String path, boolean directRight) {
        Texture texture = new Texture(path);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        return new GameAreaChangeButton(parent, drawable, directRight);
    }
    
    public GameAreaChangeButton(GameBeeScreen parent, Drawable drawable, boolean directRight) {
        super(drawable);
        this.parent = parent;
        this.directRight = directRight;
        this.setSize(64, 64);
        if (directRight) {
            this.setPosition(Gdx.graphics.getWidth() - this.getWidth(), Gdx.graphics.getHeight() / 2);
        } else {
            this.setPosition(0, Gdx.graphics.getHeight() / 2);
        }
        
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (directRight) {
                    parent.setArea(parent.getArea().previous());
                } else {
                    parent.setArea(parent.getArea().next());
                }
                
            }
            
        });
    }

}
