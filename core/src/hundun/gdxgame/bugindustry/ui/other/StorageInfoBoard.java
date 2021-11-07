package hundun.gdxgame.bugindustry.ui.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard extends Table {
    
    Map<GameArea, List<ResourceType>> areaShownResources; 
    private void init() {
        areaShownResources = new HashMap<>();
        areaShownResources.put(GameArea.BEE_FARM, Arrays.asList(
                ResourceType.WOOD,
                ResourceType.WORKER_BEE,
                ResourceType.HONEY
                ));
        areaShownResources.put(GameArea.FOREST_FARM, Arrays.asList(
                ResourceType.WOOD

                ));
        
    }
    
    Label mainLabel;
    GameScreen parent;
    public StorageInfoBoard(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(10, Gdx.graphics.getHeight() - 10 - 100, Gdx.graphics.getWidth() - 20, 50);
        this.mainLabel = new Label("", parent.game.getButtonSkin());
        this.add(mainLabel);
        
        init();
    }

    public void updateViewData() {
        List<ResourceType> shownResources = areaShownResources.get(parent.getArea());
        if (shownResources == null) {
            mainLabel.setText("Unkonwn area");
            return;
        }
        
        String text = shownResources.stream()
                .map(shownResource -> parent.game.getModelContext().getStorageModel().getResourceDescription(shownResource))
                .collect(Collectors.joining("    "));
        mainLabel.setText(text);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateViewData();
        super.draw(batch, parentAlpha);
    }
    
    
}
