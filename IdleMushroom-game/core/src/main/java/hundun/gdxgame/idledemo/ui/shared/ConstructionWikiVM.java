package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idledemo.IdleMushroomGame;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;

import java.util.ArrayList;
import java.util.List;

public class ConstructionWikiVM extends Table {

    BaseDemoPlayScreen parent;
    BaseConstruction model;

    Label label;

    public ConstructionWikiVM(BaseDemoPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.label = new Label("", parent.getGame().getMainSkin());
        this.add(label);
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
