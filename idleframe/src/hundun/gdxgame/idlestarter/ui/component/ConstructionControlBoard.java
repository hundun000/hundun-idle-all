package hundun.gdxgame.idlestarter.ui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IGameAreaChangeListener;
import hundun.gdxgame.idleframe.listener.ILogicFrameListener;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.BasePlayScreen;



/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlBoard<T_GAME extends BaseIdleGame> extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    

    public static int BOARD_BORDER_HEIGHT = 120;
    public static int LR_BUTTON_HEIGHT = BOARD_BORDER_HEIGHT;
    public static int LR_BUTTON_WIDTH = 0;

    
    BasePlayScreen<T_GAME> parent;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    List<ConstructionControlNode<T_GAME>> constructionControlNodes = new ArrayList<>();

    int NUM_ALL = 5;
    ImageButton leftButton;
    ImageButton rightButton;
    
    
    

    
    public ConstructionControlBoard(BasePlayScreen<T_GAME> parent) {
        
        this.parent = parent;
        

        leftButton = new ImageButton(BasePlayScreen.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        rightButton = new ImageButton(BasePlayScreen.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        
        Table childTable = initChild();
        ScrollPane scrollPane = new ScrollPane(childTable, parent.game.getButtonSkin());
        //scrollPane.setSize(parent.game.LOGIC_WIDTH - 100, BOARD_HEIGHT);
        //scrollPane.setHeight(SCOLL_AREA_HEIGHT);
        //scrollPane.setFillParent(true);
        //scrollPane.setFlickScroll(false);
        //scrollPane.debug();
        this.add(leftButton);
        this.add(scrollPane).fill();
        this.add(rightButton);
        
        //this.debugCell();
        //this.debugTable();
        //this.setSize(parent.game.LOGIC_WIDTH, BOARD_HEIGHT);
        //this.add(initChild());
        

        
    }
    
    private Table initChild() {
        constructionControlNodes.clear();
        Table table = new Table();
        //this.setBounds(BOARD_DISTANCE_TO_FRAME, BOARD_DISTANCE_TO_FRAME, Gdx.graphics.getWidth() - BOARD_DISTANCE_TO_FRAME * 2, BOARD_HEIGHT);
        //table.setSize(parent.game.LOGIC_WIDTH - 10, BOARD_HEIGHT - 10);
        table.setBackground(BasePlayScreen.createBorderBoard(150, 50, 0.7f, 2));
        
        for (int i = 0; i < NUM_ALL; i++) {
            var constructionView = new ConstructionControlNode<T_GAME>(parent, i);
            constructionControlNodes.add(constructionView);
            var cell = table.add(constructionView).spaceRight(10);
        }
        //table.debugAll();
        //table.debugCell();
        //table.debugTable();
        //table.setFillParent(true);
        return table;
    }

    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.onLogicFrame());
        parent.game.getModelContext().getConstructionManager().logicFrameForAllConstructionModels();
        
        //backgroundConstructionModels.forEach(item -> item.onLogicFrame());
    }


    @Override
    public void onGameAreaChange(String last, String current) {
//        backgroundConstructionModels.clear();
//        backgroundConstructionModels.addAll(allConstructionModels);
        
        List<BaseConstruction> newConstructions = parent.game.getModelContext().getConstructionManager().getAreaShownConstructionsOrEmpty(current);
//        if (constructionControlNodes.size() != newConstructions.size()) {
//            //this.add(leftButton);
//            this.add(initChild(newConstructions.size())).expand();
//            //this.add(rightButton);
//        }
        
        for (int i = 0; i < newConstructions.size() && i < NUM_ALL; i++) {
            constructionControlNodes.get(i).setModel(newConstructions.get(i));
        }
        for (int i = newConstructions.size(); i < NUM_ALL; i++) {
            constructionControlNodes.get(i).setModel(null);
        }
        Gdx.app.log("ConstructionInfoBorad", "Constructions change to: " + newConstructions.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));
        //Gdx.app.log("ConstructionInfoBorad", "backgroundConstructionModels change to: " + backgroundConstructionModels.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));
        

    }
    
    
    
}
