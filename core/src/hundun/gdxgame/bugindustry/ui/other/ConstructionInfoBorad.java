package hundun.gdxgame.bugindustry.ui.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.ui.IGameAreaChangeListener;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionInfoBorad extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    
    GameScreen parent;
    List<ConstructionView> constructionViews = new ArrayList<>();

    Map<GameArea, List<BaseConstruction>> areaShownConstructions; 
    int NUM = 5;
    
    private void init(ModelContext modelContext) {
        areaShownConstructions = new HashMap<>();
        areaShownConstructions.put(GameArea.BEE_FARM, Arrays.asList(
                modelContext.getBeeGatherConstruction(),
                modelContext.getSmallBeehiveConstruction()
                ));
        areaShownConstructions.put(GameArea.BEE_BUFF, Arrays.asList(
                ));
        areaShownConstructions.put(GameArea.FOREST_FARM, Arrays.asList(
                modelContext.getWoodGatherConstruction()
                ));
    }
    
    public ConstructionInfoBorad(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(10, 10, Gdx.graphics.getWidth() - 20, 100);
        
        for (int i = 0; i < NUM; i++) {
            var constructionView = new ConstructionView(parent, i);
            constructionViews.add(constructionView);
            this.addActor(constructionView);
        }
        
        this.debugAll();
        
        init(parent.game.getModelContext());
    }

    @Override
    public void onLogicFrame() {
        constructionViews.forEach(item -> item.onLogicFrame());
    }


    @Override
    public void onChange(GameArea last, GameArea current) {
        List<BaseConstruction> newConstructions = areaShownConstructions.get(current);
        Gdx.app.log("ConstructionInfoBorad", "Constructions change to: " + newConstructions.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));
        for (int i = 0; i < NUM; i++) {
            if (i < newConstructions.size()) {
                constructionViews.get(i).setModel(newConstructions.get(i));
            } else {
                constructionViews.get(i).setModel(null);
            }
        }

    }
    
    
    
}
