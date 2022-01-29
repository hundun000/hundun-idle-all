package hundun.gdxgame.idlestarter.ui.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard<T_GAME extends BaseIdleGame> extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;
    
    BasePlayScreen<T_GAME> parent;
    
    public PopupInfoBoard(BasePlayScreen<T_GAME> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
    }
    
    
    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<T>(content);
        container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }
    
    private void rebuildCells(BaseConstruction model) {
        this.clearChildren();
        
        add(wapperContainer(new Label(model.getDetailDescroptionConstPart(), parent.game.getButtonSkin())))
            .colspan(3)
            .left()
            .row();
        
        buildOnePack(model.getOutputComponent().getOutputCostPack());
        
        buildOnePack(model.getOutputComponent().getOutputGainPack());
        
        buildOnePack(model.getUpgradeComponent().getUpgradeCostPack());
        
        if (parent.game.debugMode) {
            this.debug();
        }
    }
    
    private void buildOnePack(ResourcePack pack) {
        if (pack != null && pack.getModifiedValues() != null) {
            add(wapperContainer(new Label(pack.getDescriptionStart(), parent.game.getButtonSkin())));
            for (var entry : pack.getModifiedValues()) {
                var node = new ResourceAmountPairNode<T_GAME>(parent.game, entry.getType());
                node.update(entry.getAmount());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
    }
    

    public void update(BaseConstruction model) {
        rebuildCells(model);
    }
    
    
}