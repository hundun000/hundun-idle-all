package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idleshare.core.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldConstructionInstanceCellDetailNode extends BaseCellDetailNodeVM {
    BaseDemoPlayScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;

    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton upgradeButton;
    TextButton transformButton;


    Table leftPart;
    RightPartVM rightPart;

    public WorldConstructionInstanceCellDetailNode(
            BaseDemoPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;

        this.leftPart = new Table();
        this.rightPart = new RightPartVM(parent);

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
                model.getUpgradeComponent().doUpgrade();
            }
        });

        this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
        workingLevelLabel.setAlignment(Align.center);




        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.positionLabel = new Label("", parent.getGame().getMainSkin());

        this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
        transformButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getGame().getFrontend().log(this.getClass().getSimpleName(), "transformButton clicked");
                model.getUpgradeComponent().doTransfer();
            }
        });


        // ------ leftPart ------
        leftPart.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        leftPart.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        leftPart.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        leftPart.add(proficiencyLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        leftPart.add(transformButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        leftPart.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));

        this.add(leftPart);
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
                "%s (%s, %s)",
                model.getName(),
                model.getSaveData().getPosition().getX(),
                model.getSaveData().getPosition().getY()
        ));
        upgradeButton.setText(model.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(model.getLevelComponent().getWorkingLevelDescription());
        proficiencyLabel.setText(model.getProficiencyComponent().getProficiencyDescroption());
        positionLabel.setText(model.getSaveData().getPosition().toShowText());

        // ------ update clickable-state ------
        if (model.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }
        if (model.getUpgradeComponent().canTransfer())
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
        this.model = construction;
        update();
        rightPart.rebuildCells(model);
    }

    @Override
    public void subLogicFrame() {
        update();
        rightPart.update(null);
    }

    public static class RightPartVM extends Table {

        BaseDemoPlayScreen parent;
        BaseConstruction model;
        public RightPartVM(BaseDemoPlayScreen parent) {
            //super("GUIDE_TEXT", parent.game.getButtonSkin());
            this.parent = parent;
            //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
            this.setTouchable(Touchable.disabled);
            this.setBackground(parent.getLayoutConst().simpleBoardBackground);
        }


        private <T extends Actor> Container<T> wapperContainer(T content) {
            Container<T> container = new Container<>(content);
            //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
            container.fill(true);
            return container;
        }

        private void rebuildCells(@Null BaseConstruction newModel) {
            if (newModel != null) {
                this.model = newModel;
            }

            this.clearChildren();

            add(wapperContainer(new Label(model.getDetailDescroptionConstPart(), parent.getGame().getMainSkin())))
                    .colspan(3)
                    .left()
                    .row();

            buildOnePack(model.getOutputComponent().getOutputCostPack());

            buildOnePack(model.getOutputComponent().getOutputGainPack());

            if (model.getUpgradeComponent().getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
                buildOnePack(model.getUpgradeComponent().getUpgradeCostPack());
            } else if (model.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER) {
                this.add(wapperContainer(new Label(model.getUpgradeComponent().getUpgradeCostPack().getDescriptionStart(), parent.getGame().getMainSkin())));
                this.add(wapperContainer(new Label(model.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin())));
                this.row();
            }



            if (parent.getGame().debugMode) {
                this.debug();
            }
        }

        private void buildOnePack(ResourcePack pack) {
            if (pack != null && pack.getModifiedValues() != null) {
                this.add(wapperContainer(new Label(pack.getDescriptionStart(), parent.getGame().getMainSkin())));
                for (ResourcePair entry : pack.getModifiedValues()) {
                    ResourceAmountPairNode<DemoIdleGame> node = new ResourceAmountPairNode<>(parent.getGame(), entry.getType());
                    node.update(entry.getAmount());
                    this.add(wapperContainer(node))
                            .height(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_HEIGHT)
                            .width(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_WIDTH);
                }
                this.row();
            }
        }


        public void update(BaseConstruction model) {
            rebuildCells(model);
        }


    }
}
