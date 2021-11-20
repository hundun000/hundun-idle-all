package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard extends Label {
    GameScreen parent;

    public PopupInfoBoard(GameScreen parent) {
        super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.setBounds(10, 120, Gdx.graphics.getWidth() - 20, 100);
        this.setTouchable(Touchable.disabled);
    }
    
    
}
