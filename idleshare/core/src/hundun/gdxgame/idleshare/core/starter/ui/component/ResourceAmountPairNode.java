package hundun.gdxgame.idleshare.core.starter.ui.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/25
 */
public class ResourceAmountPairNode<T_GAME extends BaseIdleGame<?>> extends HorizontalGroup {

    T_GAME game;

    @Getter
    String resourceType;

    Image image;
    Label label;

    public ResourceAmountPairNode(T_GAME game, String resourceType) {
        super();
        this.game = game;
        this.resourceType = resourceType;
        TextureRegion textureRegion = game.getTextureManager().getResourceIcon(resourceType);
        this.image = new Image(textureRegion);
        this.addActor(image);
        this.label = new Label("", game.getMainSkin());
        this.addActor(label);
    }

    public void update(long amout) {
        label.setText(
                game.getTextFormatTool().format(amout)
                );
    }




}
