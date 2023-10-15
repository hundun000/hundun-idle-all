package hundun.gdxgame.idledemo.ui.sub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementAndStatus;

import java.util.List;

public class OneAchievementNodeVM<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {

    BaseIdleScreen<T_GAME, T_SAVE> parent;

    AchievementAndStatus achievementAndStatus;
    Label descriptionLabel;

    public OneAchievementNodeVM(BaseIdleScreen<T_GAME, T_SAVE> parent, AchievementAndStatus achievementAndStatus) {
        this.parent = parent;
        this.setBackground(new TextureRegionDrawable(parent.getGame().getTextureManager().getDefaultBoardNinePatchTexture()));

        descriptionLabel = new Label("", parent.getGame().getMainSkin());
        this.add(descriptionLabel).center().row();

        updateData();

    }

    private void updateData() {
    }
}
