package hundun.gdxgame.idlemushroom.ui.shared.wiki;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffAndStatus;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class SharedWikiPopupInfoBoard extends Table {

    IdleMushroomGame game;
    EpochConstructionWikiVM epochConstructionWikiVM;
    NormalConstructionWikiVM constructionDetailPartVM;
    BuffWikiVM buffWikiVM;

    public SharedWikiPopupInfoBoard(IdleMushroomGame game) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.game = game;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        this.setBackground(game.getTextureManager().getTableType1Drawable());
        this.setVisible(false);

        this.epochConstructionWikiVM = new EpochConstructionWikiVM(game);
        this.constructionDetailPartVM = new NormalConstructionWikiVM(game);
        this.buffWikiVM = new BuffWikiVM(game);
    }

    public void update(Object model) {
        this.clearChildren();
        if (model instanceof BaseConstruction) {
            BaseConstruction construction = (BaseConstruction) model;
            if (construction.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER)) {
                this.add(epochConstructionWikiVM)
                        .size(game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardWidth, game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardHeight);
                epochConstructionWikiVM.rebuildCells(construction);
            } else {
                this.add(constructionDetailPartVM)
                        .size(game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardWidth, game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardHeight);
                constructionDetailPartVM.rebuildCells(construction);
            }
        } else if (model instanceof BuffAndStatus) {
            BuffAndStatus buffAndStatus = (BuffAndStatus) model;
            this.add(buffWikiVM)
                    .size(game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardWidth, game.getIdleMushroomPlayScreenLayoutConst().popupInfoBoardHeight);
            buffWikiVM.rebuildCells(buffAndStatus);
        }


    }



}
