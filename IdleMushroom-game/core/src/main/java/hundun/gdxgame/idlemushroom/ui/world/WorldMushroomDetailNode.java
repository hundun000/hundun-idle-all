package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomWorldPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.core.framework.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldMushroomDetailNode extends BaseCellDetailNodeVM {
    BaseIdleMushroomPlayScreen parent;
    BaseConstruction construction;
    Label constructionNameLabel;

    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton upgradeButton;

    ProgressBar progressBar;
    Table leftPart;
    ConstructionDetailPartVM rightPart;

    public WorldMushroomDetailNode(
            IdleMushroomWorldPlayScreen parent
            ) {
        super();
        final IdleMushroomPlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;

        this.leftPart = new Table();
        this.rightPart = new ConstructionDetailPartVM(parent);

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);


        this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(WorldMushroomDetailNode.class.getSimpleName(), "upgradeButton changed");
                construction.getUpgradeComponent().doUpgrade();
            }
        });

        this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
        workingLevelLabel.setAlignment(Align.center);




        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.progressBar = new ProgressBar(0, 100, 1, false, parent.getGame().getMainSkin());

        this.positionLabel = new Label("", parent.getGame().getMainSkin());

        Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getTextureManager().getQuestionMarkTexture()));
        questionMarkArea.setBackground(parent.getGame().getTextureManager().getQuestionMarkTableDrawable());
        questionMarkArea.setTouchable(Touchable.enabled);
        questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener<>(() -> construction, parent));

        // ------ leftPart ------
        leftPart.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT);
        leftPart.add(questionMarkArea).size(parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize, parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize);
        leftPart.row();
        leftPart.add(workingLevelLabel).colspan(2).size(CHILD_WIDTH, CHILD_HEIGHT);
        leftPart.row();
        leftPart.add(upgradeButton).colspan(2).size(CHILD_WIDTH, CHILD_HEIGHT);
        leftPart.row();
        leftPart.add(proficiencyLabel).colspan(2).size(CHILD_WIDTH, CHILD_HEIGHT);
        leftPart.row();
        leftPart.add(progressBar).colspan(2).size(CHILD_WIDTH, CHILD_HEIGHT);
        leftPart.row();

        this.setBackground(parent.getGame().getTextureManager().getTableType3Drawable());
        this.add(leftPart).padRight(20);
        this.add(rightPart);
    }







    private void update() {
        // ------ update show-state ------
        if (construction == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getDescriptionPackage().getName() + " set to its view");
        }
        // ------ update text ------
        constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                "%s",
                construction.getDescriptionPackage().getName()
        ));
        upgradeButton.setText(construction.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(DescriptionPackage.Helper.getWorkingLevelDescription(construction));
        proficiencyLabel.setText(DescriptionPackage.Helper.getProficiencyDescription(construction));
        progressBar.setRange(0, construction.getProficiencyComponent().getMaxProficiency());
        progressBar.setValue(construction.getSaveData().getProficiency());
        positionLabel.setText(construction.getSaveData().getPosition().toShowText());

        // ------ update clickable-state ------
        if (construction.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }


        // ------ update model ------
        //model.onLogicFrame();

    }


    @Override
    public void updateForNewConstruction(BaseConstruction construction, GridPosition position) {
        this.construction = construction;
        update();
        rightPart.rebuildCells(this.construction);
    }

    @Override
    public void subLogicFrame() {
        update();
        rightPart.update();
    }

}
