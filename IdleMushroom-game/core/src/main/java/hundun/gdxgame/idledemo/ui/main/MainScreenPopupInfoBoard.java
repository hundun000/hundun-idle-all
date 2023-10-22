package hundun.gdxgame.idledemo.ui.main;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.ui.screen.MainPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idledemo.ui.shared.ConstructionWikiVM;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class MainScreenPopupInfoBoard extends Table {

    MainPlayScreen parent;
    TwoEpochInfoAreaVM twoEpochInfoAreaVM;
    ConstructionWikiVM constructionDetailPartVM;
    public MainScreenPopupInfoBoard(MainPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        this.setBackground(parent.getLayoutConst().simpleBoardBackground);
        this.setVisible(false);

        this.twoEpochInfoAreaVM = new TwoEpochInfoAreaVM(parent);
        this.constructionDetailPartVM = new ConstructionWikiVM(parent);
    }

    public void update(BaseConstruction construction) {
        this.clearChildren();
        if (construction.getPrototypeId().equals(DemoConstructionPrototypeId.EPOCH_COUNTER)) {
            this.add(twoEpochInfoAreaVM)
                    .size(parent.getLayoutConst().popupInfoBoardWidth, parent.getLayoutConst().popupInfoBoardHeight);
            twoEpochInfoAreaVM.rebuildCells(construction);
        } else {
            this.add(constructionDetailPartVM)
                    .size(parent.getLayoutConst().popupInfoBoardWidth, parent.getLayoutConst().popupInfoBoardHeight);
            constructionDetailPartVM.rebuildCells(construction);
        }
    }



}
