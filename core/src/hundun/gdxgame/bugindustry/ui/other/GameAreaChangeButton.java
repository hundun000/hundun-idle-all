package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.ui.IGameAreaChangeListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/04
 */
public class GameAreaChangeButton extends TextButton implements IGameAreaChangeListener {
    GameScreen parent;
    boolean directRight;
    
    
    public static GameAreaChangeButton create(GameScreen parent, String path, boolean directRight) {
        Texture texture = new Texture(path);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        return new GameAreaChangeButton(parent, drawable, directRight);
    }
    
    public GameAreaChangeButton(GameScreen parent, Drawable drawable, boolean directRight) {
        super("NONE", parent.game.getButtonSkin());
        this.parent = parent;
        this.directRight = directRight;
        this.setSize(64, 64);
        this.setBackground(drawable);
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
                    parent.setAreaAndNotifyChildren(parent.getArea().switchCatogory());
                } else {
                    parent.setAreaAndNotifyChildren(parent.getArea().switchFarmAndBuff());
                }
                
            }
            
        });
    }

    @Override
    public void onGameAreaChange(GameArea last, GameArea current) {
        GameArea next;
        if (directRight) {
            next = parent.getArea().switchCatogory();
        } else {
            next = parent.getArea().switchFarmAndBuff();
        }
        if (next != null) {
            this.setVisible(true);
            this.setText(next.name());
        } else {
            this.setVisible(false);
        }
        
    }

}
