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
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;



/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlBoard<T_GAME extends BaseIdleGame> extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    

    
    public final int LR_BUTTON_HEIGHT;
    public final int LR_BUTTON_WIDTH = 0;

    
    BasePlayScreen<T_GAME> parent;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    List<ConstructionControlNode<T_GAME>> constructionControlNodes = new ArrayList<>();

    static final int NUM_NODE_MIN = 1;
    
    ImageButton leftButton;
    ImageButton rightButton;
    Table childTable;
    
    

    
    public ConstructionControlBoard(BasePlayScreen<T_GAME> parent) {
        
        this.parent = parent;

        this.LR_BUTTON_HEIGHT = parent.getLayoutConst().CONSTRUCION_BOARD_BORDER_HEIGHT;
        
        
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
        
        childTable = new Table();
        childTable.setBackground(BasePlayScreen.createBorderBoard(150, 50, 0.7f, 2));
        ScrollPane scrollPane = new ScrollPane(childTable, parent.game.getButtonSkin());

        this.add(leftButton);
        this.add(scrollPane).fill();
        this.add(rightButton);
        
    }
    
    private void initChild(int currentNumNode) {
        constructionControlNodes.clear();
        childTable.clearChildren();
        
        for (int i = 0; i < currentNumNode; i++) {
            var constructionView = new ConstructionControlNode<T_GAME>(parent, i);
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10);
        }

    }

    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.onLogicFrame());
        parent.game.getModelContext().getConstructionManager().logicFrameForAllConstructionModels();
        
        //backgroundConstructionModels.forEach(item -> item.onLogicFrame());
    }


    @Override
    public void onGameAreaChange(String last, String current) {


        List<BaseConstruction> newConstructions = parent.game.getModelContext().getConstructionManager().getAreaShownConstructionsOrEmpty(current);

        int currentNumNode = Math.max(NUM_NODE_MIN, newConstructions.size());
        initChild(currentNumNode);
        
        for (int i = 0; i < currentNumNode && i < newConstructions.size(); i++) {
            constructionControlNodes.get(i).setModel(newConstructions.get(i));
        }
        for (int i = newConstructions.size(); i < currentNumNode; i++) {
            constructionControlNodes.get(i).setModel(null);
        }
        Gdx.app.log("ConstructionInfoBorad", "Constructions change to: " + newConstructions.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));

    }
    
    
    
}
