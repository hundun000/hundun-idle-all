package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;
    
    GameScreen parent;

    public PopupInfoBoard(GameScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
    }
    
    
    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<T>(content);
        container.setBackground(parent.tableBackgroundDrawable);
        return container;
    }
    
    private void rebuildCells(BaseConstruction model) {
        this.clearChildren();
        
        add(wapperContainer(new Label(model.getDetailDescroptionConstPart(), parent.game.getButtonSkin())))
            .colspan(3)
            .left()
            .row();
        
        String outputCostDescriptionStart = model.getDescriptionPackage().getOutputCostDescriptionStart();
        if (outputCostDescriptionStart != null && model.getModifiedOutputCostMap() != null) {
            add(wapperContainer(new Label(outputCostDescriptionStart, parent.game.getButtonSkin())));
            for (var entry : model.getModifiedOutputCostMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
        
        String outputGainDescriptionStart = model.getDescriptionPackage().getOutputGainDescriptionStart();
        if (outputGainDescriptionStart != null && model.getModifiedOutputGainMap() != null) {
            add(wapperContainer(new Label(outputGainDescriptionStart, parent.game.getButtonSkin())));
            for (var entry : model.getModifiedOutputGainMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }

        String upgradeCostGainDescriptionStart = model.getDescriptionPackage().getUpgradeCostDescriptionStart();
        if (upgradeCostGainDescriptionStart != null && model.getModifiedUpgradeCostMap() != null) {
            add(wapperContainer(new Label(upgradeCostGainDescriptionStart, parent.game.getButtonSkin())));
            for (var entry : model.getModifiedUpgradeCostMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
        
        if (parent.game.debugMode) {
            this.debug();
        }
    }
    

    public void update(BaseConstruction model) {
        rebuildCells(model);
    }
    
    
}
