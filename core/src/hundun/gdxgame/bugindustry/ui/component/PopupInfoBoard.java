package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    
    private void rebuildCells(BaseConstruction model) {
        this.clearChildren();
        
        add(new Label(model.getDetailDescroptionConstPart(), parent.game.getButtonSkin())).row();
        
        if (model.getOutputCostDescriptionStart() != null && model.getModifiedOutputCostMap() != null) {
            add(new Label(model.getOutputCostDescriptionStart(), parent.game.getButtonSkin()));
            for (var entry : model.getModifiedOutputCostMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(node).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
        
        if (model.getOutputGainDescriptionStart() != null && model.getModifiedOutputGainMap() != null) {
            add(new Label(model.getOutputGainDescriptionStart(), parent.game.getButtonSkin()));
            for (var entry : model.getModifiedOutputGainMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(node).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }

        if (model.getUpgradeCostDescriptionStart() != null && model.getModifiedUpgradeCostMap() != null) {
            add(new Label(model.getUpgradeCostDescriptionStart(), parent.game.getButtonSkin()));
            for (var entry : model.getModifiedUpgradeCostMap().entrySet()) {
                var node = new ResourceAmountPairNode(parent.game, entry.getKey());
                node.update(entry.getValue());
                this.add(node).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
        
        this.debug();
    }
    

    public void update(BaseConstruction model) {
        rebuildCells(model);
    }
    
    
}
