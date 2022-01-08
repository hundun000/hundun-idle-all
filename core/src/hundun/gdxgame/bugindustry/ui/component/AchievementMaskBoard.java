package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.idleframe.model.AchievementPrototype;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementMaskBoard extends Table {

    PlayScreen parent;
    Label label;
    
    public AchievementMaskBoard(PlayScreen parent) {
        this.parent = parent;
        this.setBackground(new SpriteDrawable(new Sprite(parent.game.getTextureManager().getWinTexture())));
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        label = new Label("", parent.game.getButtonSkin());
        this.add(label).center().row();
        
        Button textButton = new TextButton("continue", parent.game.getButtonSkin());
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.hideAchievementMaskBoard();
            }
        });
        this.add(textButton).center();
        
        this.setVisible(false);
        
    }
    public void setAchievementPrototype(AchievementPrototype prototype) {
        label.setText(prototype.getDescription());
    }
}
