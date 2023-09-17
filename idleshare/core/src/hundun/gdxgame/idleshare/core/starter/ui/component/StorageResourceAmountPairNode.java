package hundun.gdxgame.idleshare.core.starter.ui.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/25
 */
public class StorageResourceAmountPairNode<T_GAME extends BaseIdleGame<?>> extends HorizontalGroup {
    LabelStyle PLUS_STYLE;
    LabelStyle MINUS_STYLE;
    T_GAME game;

    @Getter
    String resourceType;

    Image image;
    Label amountLabel;
    Label deltaLabel;

    public StorageResourceAmountPairNode(T_GAME game, String resourceType) {
        super();
        this.game = game;
        this.resourceType = resourceType;
        TextureRegion textureRegion = game.getTextureManager().getResourceIcon(resourceType);
        this.image = new Image(textureRegion);
        this.addActor(image);
        this.amountLabel = new Label("", game.getMainSkin());
        this.addActor(amountLabel);
        this.deltaLabel = new Label("", game.getMainSkin());
        this.addActor(deltaLabel);

        this.PLUS_STYLE = game.getMainSkin().get("green_style", LabelStyle.class);
        this.MINUS_STYLE = game.getMainSkin().get("red_style", LabelStyle.class);
    }

    public void update(long delta, long amount) {
        amountLabel.setText(
                game.getTextFormatTool().format(amount)
                );
        if (delta > 0)
        {
            deltaLabel.setText("(+" + delta + ")");
            deltaLabel.setStyle(PLUS_STYLE);
        }
        else if(delta == 0)
        {
            deltaLabel.setText("");
        }
        else
        {
            deltaLabel.setText("(-" + Math.abs(delta) + ")");
            deltaLabel.setStyle(MINUS_STYLE);
        }
    }




}
