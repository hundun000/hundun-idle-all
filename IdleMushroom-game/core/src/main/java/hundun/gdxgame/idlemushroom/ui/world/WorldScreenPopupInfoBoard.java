package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionWikiVM;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class WorldScreenPopupInfoBoard extends Table {

    WorldPlayScreen parent;
    ConstructionWikiVM constructionWikiVM;
    public WorldScreenPopupInfoBoard(WorldPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType1Drawable());
        this.setVisible(false);

        this.constructionWikiVM = new ConstructionWikiVM(parent);
        this.add(constructionWikiVM)
                .size(parent.getLayoutConst().popupInfoBoardWidth, parent.getLayoutConst().popupInfoBoardHeight);
    }

    public void update(BaseConstruction construction) {
        this.constructionWikiVM.rebuildCells(construction);
    }



}
