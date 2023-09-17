package hundun.gdxgame.idleshare.core.starter.ui.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;


/**
 * @author hundun
 * Created on 2021/12/06
 */
public class GameAreaControlNode<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Image {

    BaseIdleScreen<T_GAME, T_SAVE> parent;
    //Image image;
    Label label;

    String gotoScreenId;

    public GameAreaControlNode(BaseIdleScreen<T_GAME, T_SAVE> parent, String gotoScreenId, boolean longVersion) {
        this.parent = parent;
        this.gotoScreenId = gotoScreenId;

        rebuildImage(longVersion);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                parent.getGame().setLastScreenId(parent.getScreenId());
                parent.getGame().getScreenManager().pushScreen(gotoScreenId, null);
                parent.getGame().getAudioPlayManager().intoScreen(gotoScreenId);
            }
        });

    }

    private Drawable rebuildImage(boolean longVersion) {

        return new TextureRegionDrawable(
                parent.getGame().getTextureManager().getGameAreaTexture(gotoScreenId, longVersion)
        );

    }

    public void changeVersion(boolean longVersion) {
        this.setDrawable(rebuildImage(longVersion));
    }

}
