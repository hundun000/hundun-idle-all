package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.logic.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.screen.MainPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class OneEpochInfoAreaVM extends Table {

    MainPlayScreen parent;
    BaseConstruction epochCounterConstruction;
    BaseConstruction mainClickerConstruction;
    @Null
    RootEpochConfig epochConfig;
    int epochLevel;
    Label epochInfoLabel;
    Label maxLevelLabel;
    Table mainClickerPart;

    public OneEpochInfoAreaVM(
            MainPlayScreen parent
    ) {
        super();
        final IdleMushroomPlayScreenLayoutConst playScreenLayoutConst = parent.getIdleMushroomPlayScreenLayoutConst();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.EpochInfoArea_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        // ------ changeWorkingLevelGroup ------


        this.epochInfoLabel = new Label("", parent.getGame().getMainSkin());
        epochInfoLabel.setAlignment(Align.center);
        this.maxLevelLabel = new Label("", parent.getGame().getMainSkin());
        maxLevelLabel.setAlignment(Align.center);
        this.mainClickerPart = new Table(parent.getGame().getMainSkin());

        // ------ this ------
        this.add(epochInfoLabel).row();
        this.add(maxLevelLabel).row();
        this.add(mainClickerPart).row();

        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType3Drawable());
    }

    private void update() {
        // ------ update show-state ------
        if (epochCounterConstruction == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
        }



        // ------ update text ------
        mainClickerPart.clearChildren();
        if (epochConfig != null) {
            epochInfoLabel.setText("时期：" + epochLevel);
            maxLevelLabel.setText("蘑菇等级上限：" + epochConfig.getMaxLevel());
            ConstructionDetailPartVM.resourcePackAsActor(mainClickerConstruction.getOutputComponent().getOutputGainPack(), mainClickerPart, parent);
        } else {
            epochInfoLabel.setText("");
            maxLevelLabel.setText("");
        }


    }


    public void updateAsConstruction(
            @Null RootEpochConfig epochConfig,
            BaseConstruction epochCounterConstruction,
            int epochLevel
    ) {
        this.epochCounterConstruction = epochCounterConstruction;
        this.mainClickerConstruction = parent.getGame().getIdleGameplayExport().getGameplayContext().getConstructionManager()
                .getSingletonConstructionInstancesOrEmpty()
                .stream()
                .filter(it -> it.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.MAIN_MUSHROOM))
                .findAny()
                .orElse(null);
        this.epochConfig = epochConfig;
        this.epochLevel = epochLevel;
        update();
    }
}
