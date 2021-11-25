package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.ResourceType;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/25
 */
public class StorageInfoNode extends HorizontalGroup {
    BugIndustryGame game;
    @Getter
    ResourceType resourceType;
    
    Image image;
    Label label;
    
    public StorageInfoNode(BugIndustryGame game, ResourceType resourceType) {
        super();
        this.game = game;
        this.resourceType = resourceType;
        TextureRegion textureRegion = game.getModelContext().getStorageManager().getResourceIcon(resourceType);
        this.image = new Image(textureRegion);
        this.addActor(image);
        this.label = new Label("", game.getButtonSkin());
        this.addActor(label);
    }
    
    public void update(int amout) {
        label.setText(
                amout
                );
    }
    
    
    
    
}
