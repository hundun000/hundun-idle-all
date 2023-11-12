package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class ConstructionWikiVM extends Table {

    BaseIdleMushroomPlayScreen parent;
    BaseConstruction model;

    Label label;

    public ConstructionWikiVM(BaseIdleMushroomPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.label = new Label("", parent.getGame().getMainSkin());
        label.setWrap(true);
        this.add(label).grow();
    }


    public void rebuildCells(@Null BaseConstruction newModel) {
        if (newModel != null) {
            this.model = newModel;
        }

        this.label.setText(model.getDetailDescriptionConstPart());
    }



    public void update() {
        rebuildCells(null);
    }


}
