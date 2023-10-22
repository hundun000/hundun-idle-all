package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

public abstract class BaseCellDetailNodeVM extends Table {

    public abstract void subLogicFrame();
    public abstract void updateForNewConstruction(BaseConstruction construction, GridPosition position);
}
