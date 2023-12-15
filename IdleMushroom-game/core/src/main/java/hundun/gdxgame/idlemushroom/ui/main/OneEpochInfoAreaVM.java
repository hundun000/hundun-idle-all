package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.IdleMushroomGame.RootEpochConfig;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomMainPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class OneEpochInfoAreaVM extends Table {

    IdleMushroomGame game;
    @Null
    RootEpochConfig epochConfig;
    Label epochInfoLabel;
    Label maxLevelLabel;
    Table mainClickerPart;
    boolean isPreviewNextLevel;

    public OneEpochInfoAreaVM(
            IdleMushroomGame game
    ) {
        super();
        final IdleMushroomPlayScreenLayoutConst playScreenLayoutConst = game.getIdleMushroomPlayScreenLayoutConst();
        this.game = game;

        int CHILD_WIDTH = playScreenLayoutConst.EpochInfoArea_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        // ------ changeWorkingLevelGroup ------


        this.epochInfoLabel = new Label("", game.getMainSkin());
        epochInfoLabel.setAlignment(Align.center);
        this.maxLevelLabel = new Label("", game.getMainSkin());
        maxLevelLabel.setAlignment(Align.center);
        this.mainClickerPart = new Table(game.getMainSkin());

        // ------ this ------
        this.add(epochInfoLabel).row();
        this.add(maxLevelLabel).row();
        this.add(mainClickerPart).row();

        this.setBackground(game.getTextureManager().getTableType3Drawable());
    }

    private void update() {




        // ------ update text ------
        mainClickerPart.clearChildren();
        if (epochConfig != null) {
            epochInfoLabel.setText(game.getIdleMushroomExtraGameplayExport().getEpochCounterConstruction()
                    .getDescriptionPackage().getExtraTexts().get(0) + epochConfig.getEnlargementLevel());
            maxLevelLabel.setText(game.getIdleMushroomExtraGameplayExport().getEpochCounterConstruction()
                    .getDescriptionPackage().getExtraTexts().get(1) + epochConfig.getMaxLevel());
            ConstructionDetailPartVM.resourcePackAsActor(
                    game.getIdleMushroomExtraGameplayExport().getMainClickerConstruction().getDescriptionPackage().getOutputGainDescriptionStart(),
                    game.getIdleMushroomExtraGameplayExport().getMainClickerConstruction().getOutputComponent().getOutputGainPack(),
                    mainClickerPart, game, isPreviewNextLevel);
        } else {
            epochInfoLabel.setText("");
            maxLevelLabel.setText("");
        }


    }


    public void updateAsConstruction(
            @Null RootEpochConfig epochConfig,
            boolean isPreviewNextLevel
    ) {
        this.isPreviewNextLevel = isPreviewNextLevel;
        this.epochConfig = epochConfig;
        update();
    }
}
