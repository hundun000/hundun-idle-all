package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idledemo.ui.shared.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldConstructionInstanceCellDetailNode extends BaseCellDetailNodeVM {
    BaseDemoPlayScreen parent;
    BaseConstruction construction;
    Label constructionNameLabel;

    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton upgradeButton;
    TextButton destoryButton;
    TextButton transformButton;





    public WorldConstructionInstanceCellDetailNode(
            BaseDemoPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);


        this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(WorldConstructionInstanceCellDetailNode.class.getSimpleName(), "upgradeButton changed");
                construction.getUpgradeComponent().doUpgrade();
            }
        });

        this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
        workingLevelLabel.setAlignment(Align.center);




        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.positionLabel = new Label("", parent.getGame().getMainSkin());
        this.destoryButton = new TextButton("-", parent.getGame().getMainSkin());
        this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(proficiencyLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }







    private void setModel(BaseConstruction constructionExportProxy) {
        this.construction = constructionExportProxy;

        update();
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
                "%s (%s, %s)",
                construction.getDescriptionPackage().getName(),
                construction.getSaveData().getPosition().getX(),
                construction.getSaveData().getPosition().getY()
        ));
        upgradeButton.setText(construction.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(DescriptionPackage.Helper.getWorkingLevelDescription(construction));
        proficiencyLabel.setText(DescriptionPackage.Helper.getProficiencyDescription(construction));
        positionLabel.setText(construction.getSaveData().getPosition().toShowText());
        destoryButton.setText(construction.getDescriptionPackage().getDestroyButtonText());

        // ------ update clickable-state ------
        if (construction.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }
        if (construction.getExistenceComponent().canDestroy())
        {
            destoryButton.setDisabled(false);
            destoryButton.getLabel().setColor(Color.WHITE);
        }
        else
        {
            destoryButton.setDisabled(true);
            destoryButton.getLabel().setColor(Color.RED);
        }
        if (construction.getUpgradeComponent().canTransfer())
        {
            transformButton.setDisabled(false);
            transformButton.getLabel().setColor(Color.WHITE);
        }
        else
        {
            transformButton.setDisabled(true);
            transformButton.getLabel().setColor(Color.RED);
        }

        // ------ update model ------
        //model.onLogicFrame();

    }


    public void updateAsConstruction(BaseConstruction construction, GridPosition position) {
        setModel(construction);
        update();
    }

    @Override
    public void subLogicFrame() {
        update();
    }
}
