package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.bugindustry.model.AchievementPrototype;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementMaskBoard extends Table {

    GameScreen parent;
    Label label;
    
    public AchievementMaskBoard(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.maskBackgroundDrawable);
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        label = new Label("", parent.game.getButtonSkin());
        this.add(label).center().row();
        
        Button textButton = new TextButton("comfirm", parent.game.getButtonSkin());
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
