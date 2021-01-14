package hundun.gdxgame.bugindustry.ui.component;

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

import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.idleframe.listener.IGameAreaChangeListener;
import hundun.gdxgame.idleframe.listener.ILogicFrameListener;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlBoard extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    
    //public static int BOARD_DISTANCE_TO_FRAME = 10;
    public static int BOARD_BORDER_HEIGHT = 180;
    public static int LR_BUTTON_HEIGHT = 170;
    public static int SCOLL_AREA_HEIGHT = 150;
    //public static int BOARD_WIDTH = 100;
    
    //public static int NODE_CELL_WIDTH = 110;
    
    PlayScreen parent;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    List<ConstructionControlNode> constructionControlNodes = new ArrayList<>();

    int NUM_ALL = 7;
    ImageButton leftButton;
    ImageButton rightButton;
    
    
    

    
    public ConstructionControlBoard(PlayScreen parent) {
        
        this.parent = parent;
        

//        ScrollPane scrollPane = new ScrollPane(initChild());
//        //scrollPane.setSize(parent.game.LOGIC_WIDTH - 100, BOARD_HEIGHT);
//        scrollPane.setFlickScroll(false);

        leftButton = new ImageButton(BasePlayScreen.createBorderBoard(50, LR_BUTTON_HEIGHT, 0.8f, 3));
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        rightButton = new ImageButton(BasePlayScreen.createBorderBoard(50, LR_BUTTON_HEIGHT, 0.8f, 3));
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
        table.setBackground(BasePlayScreen.createBorderBoard(10, 5, 0.7f, 1));
        
        for (int i = 0; i < NUM_ALL; i++) {
            var constructionView = new ConstructionControlNode(parent, i);
            constructionControlNodes.add(constructionView);
            var cell = table.add(constructionView).spaceRight(10);
//            if (i % NUM_PER_ROW == NUM_PER_ROW - 1) {
//                cell.row();
//            }
        }
        //table.debugAll();
        table.debugCell();
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
