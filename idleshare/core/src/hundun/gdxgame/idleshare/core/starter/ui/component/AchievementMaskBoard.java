package hundun.gdxgame.idleshare.core.starter.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementMaskBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {

    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;
    Label label;

    public AchievementMaskBoard(BaseIdlePlayScreen<T_GAME, T_SAVE> parent) {
        this.parent = parent;
        this.setBackground(new SpriteDrawable(new Sprite(parent.getGame().getTextureManager().getAchievementMaskBoardTexture())));
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        label = new Label("", parent.getGame().getMainSkin());
        this.add(label).center().row();

        Button textButton = new TextButton("continue", parent.getGame().getMainSkin());
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.hideAchievementMaskBoard();
            }
        });
        this.add(textButton).center();

        this.setVisible(false);

    }
    public void setAchievementPrototype(AbstractAchievement prototype) {
        label.setText(prototype.getDescription());
    }
}
