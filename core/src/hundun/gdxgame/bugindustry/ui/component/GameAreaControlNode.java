package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.idlestarter.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/12/06
 */
public class GameAreaControlNode extends HorizontalGroup {
    
    PlayScreen parent;
    Image image;
    Label label;
    boolean debugType;
    String gameArea;
    
    public GameAreaControlNode(PlayScreen parent, String gameArea, boolean longVersion, boolean debugType) {
        this.parent = parent;
        this.debugType = debugType;
        this.gameArea = gameArea;
        
        image = new Image(rebuildImage(longVersion));
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parent.setAreaAndNotifyChildren(gameArea);
            }
        });
        this.addActor(image);
    }
    
    private Drawable rebuildImage(boolean longVersion) {
        if (!debugType) {
            Drawable drawable = new SpriteDrawable(new Sprite(parent.game.getTextureManager().getGameAreaTexture(gameArea, longVersion)));
            return drawable;
        } else {
            Drawable drawable = BasePlayScreen.createTwoColorBoard(GameAreaControlBoard.FULL_CELL_WIDTH, GameAreaControlBoard.CELL_HEIGHT, 0.8f, longVersion ? 0 : GameAreaControlBoard.FULL_CELL_WIDTH - GameAreaControlBoard.SHORT_CELL_WIDTH);
            return drawable;
        }
    }
    
    public void changeVersion(boolean longVersion) {
        image.setDrawable(rebuildImage(longVersion));
    }

}
