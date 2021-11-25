package hundun.gdxgame.bugindustry.ui.other;

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
    GameScreen parent;

    public PopupInfoBoard(GameScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.setBounds(10, 120, Gdx.graphics.getWidth() - 20, 100);
        this.setTouchable(Touchable.disabled);
    }
    
    private void rebuildCells(BaseConstruction model) {
        this.clearChildren();
        
        add(new Label(model.getDetailDescroptionConstPart(), parent.game.getButtonSkin())).row();
        
        add(new Label(model.getModifiedUpgradeCostDescription(), parent.game.getButtonSkin()));
        // TODO
        this.row();
        
        add(new Label(model.getModifiedOuputCostDescription(), parent.game.getButtonSkin()));
        // TODO
        this.row();
        
        add(new Label(model.getModifiedOutputGainDescription(), parent.game.getButtonSkin()));
        // TODO
        this.row();
        
        this.debug();
    }
    

    public void update(BaseConstruction model) {
        rebuildCells(model);
    }
    
    
}
