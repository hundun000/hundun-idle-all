package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.ui.screen.MainPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class TwoEpochInfoAreaVM extends Table {

    MainPlayScreen parent;
    Label titleLabel;
    OneEpochInfoAreaVM currentLevelInfoAreaVM;
    OneEpochInfoAreaVM nextLevelInfoAreaVM;

    VerticalGroup midPart;
    public TwoEpochInfoAreaVM(MainPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);

        this.titleLabel = new Label("", parent.getGame().getMainSkin());
        titleLabel.setWrap(true);
        this.add(titleLabel).grow().colspan(3).row();


        this.currentLevelInfoAreaVM = new OneEpochInfoAreaVM(parent);
        this.add(currentLevelInfoAreaVM);
        this.midPart = new VerticalGroup();
        this.add(midPart).padLeft(25).padRight(25);
        this.nextLevelInfoAreaVM = new OneEpochInfoAreaVM(parent);
        this.add(nextLevelInfoAreaVM);
        this.pad(parent.getIdleMushroomPlayScreenLayoutConst().WorldConstructionCellTablePad);
    }

    public void rebuildCells(BaseConstruction epochCounterConstruction) {
        int currentEpochLevel = epochCounterConstruction.getSaveData().getLevel();
        RootEpochConfig epochConfig = IdleMushroomGame.epochConfigMap.get(currentEpochLevel);
        int nextEpochLevel = currentEpochLevel + 1;
        RootEpochConfig nextEpochConfig = IdleMushroomGame.epochConfigMap.get(nextEpochLevel);
        titleLabel.setText(epochCounterConstruction.getDetailDescriptionConstPart());
        currentLevelInfoAreaVM.updateAsConstruction(epochConfig, epochCounterConstruction, currentEpochLevel);
        midPart.clearChildren();
        if (nextEpochConfig != null) {
            midPart.addActor(new Label("-->", parent.getGame().getMainSkin()));
            nextLevelInfoAreaVM.updateAsConstruction(nextEpochConfig, epochCounterConstruction, nextEpochLevel);
        }else {
            midPart.addActor(new Label("max", parent.getGame().getMainSkin()));
            nextLevelInfoAreaVM.setVisible(false);
        }

    }



}
