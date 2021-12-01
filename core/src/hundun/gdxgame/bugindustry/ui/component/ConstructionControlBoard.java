package hundun.gdxgame.bugindustry.ui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ModelContext;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.model.construction.ConstructionId;
import hundun.gdxgame.bugindustry.model.resource.ResourceType;
import hundun.gdxgame.bugindustry.ui.IGameAreaChangeListener;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlBoard extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    
    public static int BOARD_DISTANCE_TO_FRAME = 10;
    public static int BOARD_HEIGHT = 100;
    
    GameScreen parent;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    List<ConstructionControlNode> constructionControlNodes = new ArrayList<>();

    int NUM = 5;
    

    
    public ConstructionControlBoard(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(BOARD_DISTANCE_TO_FRAME, BOARD_DISTANCE_TO_FRAME, Gdx.graphics.getWidth() - BOARD_DISTANCE_TO_FRAME * 2, BOARD_HEIGHT);
        
        for (int i = 0; i < NUM; i++) {
            var constructionView = new ConstructionControlNode(parent, i);
            constructionControlNodes.add(constructionView);
            this.addActor(constructionView);
        }
        
        //this.debugAll();
        
        //init(parent.game.getModelContext());
    }

    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.onLogicFrame());
        parent.game.getModelContext().getConstructionManager().logicFrameForAllConstructionModels();
        
        //backgroundConstructionModels.forEach(item -> item.onLogicFrame());
    }


    @Override
    public void onGameAreaChange(GameArea last, GameArea current) {
//        backgroundConstructionModels.clear();
//        backgroundConstructionModels.addAll(allConstructionModels);
        
        List<BaseConstruction> newConstructions = parent.game.getModelContext().getConstructionManager().getAreaShownConstructions(current);
        for (int i = 0; i < NUM; i++) {
            if (i < newConstructions.size()) {
                //backgroundConstructionModels.remove(newConstructions.get(i));
                constructionControlNodes.get(i).setModel(newConstructions.get(i));
            } else {
                constructionControlNodes.get(i).setModel(null);
            }
        }
        Gdx.app.log("ConstructionInfoBorad", "Constructions change to: " + newConstructions.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));
        //Gdx.app.log("ConstructionInfoBorad", "backgroundConstructionModels change to: " + backgroundConstructionModels.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));
        

    }
    
    
    
}
