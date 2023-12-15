package hundun.gdxgame.idlemushroom.ui.shared.wiki;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.ui.main.OneEpochInfoAreaVM;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class EpochConstructionWikiVM extends Table {

    IdleMushroomGame game;
    Label titleLabel;
    OneEpochInfoAreaVM currentLevelInfoAreaVM;
    OneEpochInfoAreaVM nextLevelInfoAreaVM;

    VerticalGroup midPart;
    public EpochConstructionWikiVM(IdleMushroomGame game) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.game = game;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);

        this.titleLabel = new Label("", game.getMainSkin());
        titleLabel.setWrap(true);
        this.add(titleLabel).grow().colspan(3).row();


        this.currentLevelInfoAreaVM = new OneEpochInfoAreaVM(game);
        this.add(currentLevelInfoAreaVM);
        this.midPart = new VerticalGroup();
        this.add(midPart).padLeft(25).padRight(25);
        this.nextLevelInfoAreaVM = new OneEpochInfoAreaVM(game);
        this.add(nextLevelInfoAreaVM);
        this.pad(game.getIdleMushroomPlayScreenLayoutConst().WorldConstructionCellTablePad);
    }

    public void rebuildCells(BaseConstruction epochCounterConstruction) {
        RootEpochConfig epochConfig = game.getIdleMushroomExtraGameplayExport().getCurrentRootEpochConfig();
        RootEpochConfig nextEpochConfig = game.getIdleMushroomExtraGameplayExport().getNextRootEpochConfig();
        titleLabel.setText(epochCounterConstruction.getDescriptionPackage().getWikiText());
        currentLevelInfoAreaVM.updateAsConstruction(epochConfig, false);
        midPart.clearChildren();
        if (nextEpochConfig != null) {
            midPart.addActor(new Label("-->", game.getMainSkin()));
            nextLevelInfoAreaVM.updateAsConstruction(nextEpochConfig, true);
        }else {
            midPart.addActor(new Label("max", game.getMainSkin()));
            nextLevelInfoAreaVM.setVisible(false);
        }

    }



}
