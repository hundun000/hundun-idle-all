package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

public class CellDetailInnerBoardVM extends Table {

    Label label;

    public void postPrefabInitialization(BaseDemoPlayScreen parent) {
        label = new Label("", parent.getGame().getMainSkin());
        this.add(label);
    }

    public void update() {

    }


    public void update(BaseConstruction construction, GridPosition position) {
        label.setText("construction: " + construction.getName());
    }

    public void update(AbstractConstructionPrototype constructionPrototype, GridPosition position) {
        label.setText("constructionPrototype: " + constructionPrototype.getPrototypeId());
    }
}
