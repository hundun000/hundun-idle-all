package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idlemushroom.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.StarterConstructionControlNode.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldMushroomDetailNode extends BaseCellDetailNodeVM {
    BaseIdleMushroomPlayScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;

    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton upgradeButton;

    ProgressBar progressBar;
    Table leftPart;
    ConstructionDetailPartVM rightPart;

    public WorldMushroomDetailNode(
            WorldPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
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
                model.getUpgradeComponent().doUpgrade();
            }
        });

        this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
        workingLevelLabel.setAlignment(Align.center);




        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.progressBar = new ProgressBar(0, 100, 1, false, parent.getGame().getMainSkin());

        this.positionLabel = new Label("", parent.getGame().getMainSkin());

        Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getIdleMushroomTextureManager().getQuestionMarkTexture()));
        questionMarkArea.setBackground(parent.getGame().getIdleMushroomTextureManager().getQuestionMarkTableDrawable());
        questionMarkArea.setTouchable(Touchable.enabled);
        questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener(() -> model, parent));

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

        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType3Drawable());
        this.add(leftPart).padRight(20);
        this.add(rightPart);
    }







    private void update() {
        // ------ update show-state ------
        if (model == null) {
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
        constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                "%s",
                model.getName()
        ));
        upgradeButton.setText(model.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(model.getLevelComponent().getWorkingLevelDescription());
        proficiencyLabel.setText(model.getProficiencyComponent().getProficiencyDescroption());
        progressBar.setRange(0, model.getProficiencyComponent().maxProficiency);
        progressBar.setValue(model.getSaveData().getProficiency());
        positionLabel.setText(model.getSaveData().getPosition().toShowText());

        // ------ update clickable-state ------
        if (model.getUpgradeComponent().canUpgrade()) {
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
        this.model = construction;
        update();
        rightPart.rebuildCells(model);
    }

    @Override
    public void subLogicFrame() {
        update();
        rightPart.update();
    }

}
