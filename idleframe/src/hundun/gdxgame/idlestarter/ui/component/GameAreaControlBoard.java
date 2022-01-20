package hundun.gdxgame.idlestarter.ui.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IGameAreaChangeListener;
import hundun.gdxgame.idlestarter.ui.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard<T_GAME extends BaseIdleGame> extends Table implements IGameAreaChangeListener {

    BasePlayScreen<T_GAME> parent;
    Map<String, GameAreaControlNode<T_GAME>> nodes = new LinkedHashMap<>();
    
    public static final int FULL_CELL_WIDTH = 100;
    public static final int SHORT_CELL_WIDTH = 75;
    public static final int CELL_HEIGHT = 50;
    public static final int WIDTH = FULL_CELL_WIDTH;
    //public static final int Y = ConstructionControlBoard.BOARD_DISTANCE_TO_FRAME + ConstructionControlBoard.BOARD_HEIGHT + 5;
    static final int HEIGHT = 300;
    //public static final int X = 0;
    
    public GameAreaControlBoard(BasePlayScreen<T_GAME> parent, List<String> gameAreas) {
        super();
        this.parent = parent;
        this.setSize(
                WIDTH, 
                HEIGHT);
        
        for (String gameArea : gameAreas) {
            initButtonMap(gameArea, false);
        }
        
        rebuildChild(null);
        if (parent.game.debugMode) {
            //this.debugAll();
        }
    }
    
    private void initButtonMap(String gameArea, boolean longVersion) {
        GameAreaControlNode<T_GAME> node = new GameAreaControlNode<T_GAME>(parent, gameArea, longVersion, !parent.game.drawGameImageAndPlayAudio);
        nodes.put(gameArea, node);
        this.add(node).row();
    }
    
    

    @Override
    public void onGameAreaChange(String last, String current) {
        rebuildChild(current);
    }
    
    private void rebuildChild(String current) {
        
        nodes.entrySet().forEach(entry -> {
            if (entry.getKey() == current) {
                entry.getValue().changeVersion(true);
            } else {
                entry.getValue().changeVersion(false);
            }
            
        });
        
    }
    
    
}
