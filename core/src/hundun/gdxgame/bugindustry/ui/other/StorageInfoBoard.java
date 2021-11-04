package hundun.gdxgame.bugindustry.ui.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameBeeScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard extends Table {
    
    static Map<GameArea, List<ResourceType>> areaShownResources; 
    static {
        areaShownResources = new HashMap<>();
        areaShownResources.put(GameArea.BEE, Arrays.asList(
                ResourceType.WOOD,
                ResourceType.WORKER_BEE
                ));
        areaShownResources.put(GameArea.FOREST, Arrays.asList(
                ResourceType.WOOD

                ));
        
    }
    
    Label mainLabel;
    GameBeeScreen parent;
    public StorageInfoBoard(GameBeeScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(10, Gdx.graphics.getHeight() - 10 - 100, Gdx.graphics.getWidth() - 20, 100);
        this.mainLabel = new Label("", parent.game.getButtonSkin());
        this.add(mainLabel);
    }

    public void onRenderFrame() {
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

    
    
}
