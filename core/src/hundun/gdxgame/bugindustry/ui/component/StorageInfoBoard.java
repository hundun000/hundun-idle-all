package hundun.gdxgame.bugindustry.ui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
    
    public static int BOARD_DISTANCE_TO_FRAME_TOP = 10;
    public static int BOARD_DISTANCE_TO_FRAME_SIDE = 10;
    public static int BOARD_HEIGHT = 60;
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 120;
    
    public static int NUM_NODE_PER_ROW = 5;
    
//    Map<GameArea, List<ResourceType>> areaShownResources; 
//    List<ResourceType> farmAreaShownResources;
    List<ResourceType> shownOrders;
    Set<ResourceType> shownTypes = new HashSet<>();
    List<ResourceAmountPairNode> nodes = new ArrayList<>();
    
    private void initData() {
        //areaShownResources = new HashMap<>();
        shownOrders = Arrays.asList(
                ResourceType.COIN,
                ResourceType.WOOD,
                ResourceType.WOOD_BOARD,
                ResourceType.BEE,
                ResourceType.HONEY,
                ResourceType.BEEWAX,
                ResourceType.QUEEN_BEE
                );
        
//        areaShownResources.put(GameArea.BEE_FARM, farmAreaShownResources);
//        areaShownResources.put(GameArea.BEE_BUFF, farmAreaShownResources);
//        areaShownResources.put(GameArea.FOREST_FARM, farmAreaShownResources);
        
        
        
    }
    
    //Label mainLabel;
    GameScreen parent;
    
    public StorageInfoBoard(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(
                BOARD_DISTANCE_TO_FRAME_SIDE, 
                Gdx.graphics.getHeight() - BOARD_DISTANCE_TO_FRAME_TOP - BOARD_HEIGHT, 
                Gdx.graphics.getWidth() - 2 * BOARD_DISTANCE_TO_FRAME_SIDE, 
                BOARD_HEIGHT);
//        this.mainLabel = new Label("", parent.game.getButtonSkin());
//        this.add(mainLabel);
        
        initData();
        rebuildCells();
        if (parent.game.debugMode) {
            this.debugAll();
        }
    }
    
    

    private void rebuildCells() {
        this.clearChildren();
        nodes.clear();
        
        for (int i = 0; i < shownOrders.size(); i++) {
            ResourceType resourceType = shownOrders.get(i);
            if (shownTypes.contains(resourceType)) {
                ResourceAmountPairNode node = new ResourceAmountPairNode(parent.game, resourceType);
                nodes.add(node);
                shownTypes.add(resourceType);
                var cell = this.add(node).width(NODE_WIDTH).height(NODE_HEIGHT);
                if ((i + 1) % NUM_NODE_PER_ROW == 0) {
                    cell.row();
                }
            }
        }
        
    }



    public void updateViewData() {
//        List<ResourceType> shownResources = areaShownResources.get(parent.getArea());
//        if (shownResources == null) {
//            mainLabel.setText("Unkonwn area");
//            return;
//        }
        
//        String text = shownResources.stream()
//                .map(shownResource -> parent.game.getModelContext().getStorageManager().getResourceDescription(shownResource))
//                .collect(Collectors.joining("    "));
//        text += "\nBuffs = " + parent.game.getModelContext().getBuffManager().getBuffAmounts();
//        mainLabel.setText(text);
        boolean needRebuildCells = !shownTypes.equals(parent.game.getModelContext().getStorageManager().getUnlockedResourceTypes());
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(parent.game.getModelContext().getStorageManager().getUnlockedResourceTypes());
            rebuildCells();
        }
        
        nodes.stream().forEach(node -> node.update(parent.game.getModelContext().getStorageManager().getResourceNum(node.getResourceType())));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateViewData();
        super.draw(batch, parentAlpha);
    }
    
    
}
