package hundun.gdxgame.idlemushroom.ui.shared.wiki;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class NormalConstructionWikiVM extends Table {

    IdleMushroomGame game;
    BaseConstruction model;

    Label label;

    public NormalConstructionWikiVM(IdleMushroomGame game) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.game = game;
        this.label = new Label("", game.getMainSkin());
        label.setWrap(true);
        this.add(label).grow();
    }


    public void rebuildCells(@Null BaseConstruction newModel) {
        if (newModel != null) {
            this.model = newModel;
        }

        this.label.setText(model.getDescriptionPackage().getWikiText());
    }



    public void update() {
        rebuildCells(null);
    }


}
