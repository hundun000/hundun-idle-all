package hundun.gdxgame.bugindustry.ui.component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.ui.IGameAreaChangeListener;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard extends Table implements IGameAreaChangeListener {

    GameScreen parent;
    Map<GameArea, Image> nodes = new LinkedHashMap<>();
    
    static final int FULL_CELL_WIDTH = 100;
    static final int SHORT_CELL_WIDTH = 75;
    static final int CELL_HEIGHT = 50;
    public static final int WIDTH = FULL_CELL_WIDTH;
    public static final int Y = ConstructionControlBoard.BOARD_DISTANCE_TO_FRAME + ConstructionControlBoard.BOARD_HEIGHT + 5;
    static final int HEIGHT = Gdx.graphics.getHeight() - (StorageInfoBoard.BOARD_DISTANCE_TO_FRAME_TOP + StorageInfoBoard.BOARD_HEIGHT) - Y;
    public static final int X = Gdx.graphics.getWidth() - WIDTH - 5;
    
    public GameAreaControlBoard(GameScreen parent) {
        super();
        this.parent = parent;
        this.setBounds(
                X, 
                Y, 
                WIDTH, 
                HEIGHT);
        
        initButtonMap(GameArea.BEE_FARM, false);
        initButtonMap(GameArea.FOREST_FARM, false);
        //initButtonMap(GameArea.BEE_BUFF);
        initButtonMap(GameArea.SHOP, false);
        rebuildChild(null);
        if (parent.game.debugMode) {
            this.debugAll();
        }
    }
    
    private void initButtonMap(GameArea gameArea, boolean longVersion) {
        Image node = new Image(new SpriteDrawable(new Sprite(parent.game.getTextureManager().getGameAreaTexture(gameArea, longVersion))));
        //Button button = new TextButton(gameArea.name(), parent.game.getButtonSkin());
        //textButton.setSize(SHORT_CELL_WIDTH, CELL_HEIGHT);
        node.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parent.setAreaAndNotifyChildren(gameArea);
            }
        });
        nodes.put(gameArea, node);
        this.add(node).width(FULL_CELL_WIDTH).height(CELL_HEIGHT).row();
    }

    @Override
    public void onGameAreaChange(GameArea last, GameArea current) {
//        buttons.entrySet().forEach(entry -> {
//            if (entry.getKey() == current) {
//                entry.getValue().setWidth(FULL_CELL_WIDTH);
//            } else {
//                entry.getValue().setWidth(SHORT_CELL_WIDTH);
//            }
//            //this.getCell(entry.getValue()).right();
//        });
        rebuildChild(current);
    }
    
    private void rebuildChild(GameArea current) {
        
        nodes.entrySet().forEach(entry -> {
            if (entry.getKey() == current) {
                entry.getValue().setDrawable(new SpriteDrawable(new Sprite(parent.game.getTextureManager().getGameAreaTexture(entry.getKey(), true))));
                //this.add(entry.getValue()).width(FULL_CELL_WIDTH).height(CELL_HEIGHT).colspan(2).row();
            } else {
                entry.getValue().setDrawable(new SpriteDrawable(new Sprite(parent.game.getTextureManager().getGameAreaTexture(entry.getKey(), false))));
                //this.add(entry.getValue()).width(SHORT_CELL_WIDTH).height(CELL_HEIGHT).row();
            }
            
        });
        
    }
    
    
}
