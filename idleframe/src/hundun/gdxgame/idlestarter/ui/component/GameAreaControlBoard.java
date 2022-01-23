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
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard<T_GAME extends BaseIdleGame> extends Table implements IGameAreaChangeListener {

    BasePlayScreen<T_GAME> parent;
    Map<String, GameAreaControlNode<T_GAME>> nodes = new LinkedHashMap<>();

    
    public GameAreaControlBoard(BasePlayScreen<T_GAME> parent, List<String> gameAreas) {
        super();
        this.parent = parent;
//        this.setSize(
//                WIDTH, 
//                HEIGHT);
        
        for (String gameArea : gameAreas) {
            initButtonMap(gameArea, false);
        }
        
        rebuildChild(null);
        if (parent.game.debugMode) {
            this.debugAll();
        }
    }
    
    private void initButtonMap(String gameArea, boolean longVersion) {
        GameAreaControlNode<T_GAME> node = new GameAreaControlNode<T_GAME>(parent, gameArea, longVersion, !parent.game.drawGameImageAndPlayAudio);
        nodes.put(gameArea, node);
        this.add(node).width(parent.getLayoutConst().AREA_BOARD_BORDER_WIDTH).height(parent.getLayoutConst().AREA_BOARD_CELL_HEIGHT).row();
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
