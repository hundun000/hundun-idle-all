package hundun.gdxgame.idledemo.ui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idledemo.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.StarterConstructionControlNode;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class MainScreenConstructionControlBoard extends Table
        implements ILogicFrameListener, IGameAreaChangeListener, IConstructionCollectionListener
{

    BaseDemoPlayScreen parent;
    protected ISecondaryInfoBoardCallback<BaseConstruction> callback;

    protected SellerPart sellerPart;
    protected ConstructionDetailPartVM sellerDetailPart;
    protected EpochPart epochPart;
    protected ConstructionDetailPartVM epochDetailPart;
    public MainScreenConstructionControlBoard(BaseDemoPlayScreen parent, ISecondaryInfoBoardCallback<BaseConstruction> callback) {
        this.parent = parent;
        this.callback = callback;


        this.setBackground(parent.getLayoutConst().simpleBoardBackground);

        if (parent.getGame().debugMode) {
            this.debugCell();
        }
    }



    @Override
    public void onLogicFrame() {
        if (sellerPart != null) {
            sellerPart.subLogicFrame();
            epochPart.subLogicFrame();
        }
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        onConstructionCollectionChange();
    }

    @Override
    public void onConstructionCollectionChange() {
        List<BaseConstruction> singletonConstructions = parent.getGame().getIdleGameplayExport().getGameplayContext()
                .getConstructionManager().getSingletonConstructionInstancesOrEmpty();

        this.clearChildren();


        this.sellerPart = new SellerPart(parent, callback);
        this.add(sellerPart).spaceRight(10).expand();

        this.sellerDetailPart = new ConstructionDetailPartVM(parent);
        this.add(sellerDetailPart).spaceRight(10).expand();

        this.epochPart = new EpochPart(parent, callback);
        this.add(epochPart).spaceRight(10).expand();

        this.epochDetailPart = new ConstructionDetailPartVM(parent);
        this.add(epochDetailPart).spaceRight(10).expand();

        BaseConstruction sellerConstruction = singletonConstructions.stream()
                .filter(it -> it.getPrototypeId().equals(DemoConstructionPrototypeId.MUSHROOM_AUTO_SELLER))
                .findAny()
                .orElse(null);
        this.sellerPart.updateAsConstruction(sellerConstruction);
        this.sellerDetailPart.rebuildCells(sellerConstruction);

        BaseConstruction epochConstruction = singletonConstructions.stream()
                .filter(it -> it.getPrototypeId().equals(DemoConstructionPrototypeId.EPOCH_COUNTER))
                .findAny()
                .orElse(null);
        this.epochPart.updateAsConstruction(epochConstruction);
        this.epochDetailPart.rebuildCells(sellerConstruction);

        parent.getGame().getFrontend().log("ConstructionInfoBorad",
                "Constructions change to: " + singletonConstructions.stream().map(BaseConstruction::getName).collect(Collectors.joining(","))
        );
    }

    public static class EpochPart extends BaseCellDetailNodeVM {
        BaseDemoPlayScreen parent;
        BaseConstruction model;
        Label constructionNameLabel;

        Label workingLevelLabel;

        TextButton upgradeButton;





        public EpochPart(
                BaseDemoPlayScreen parent,
                ISecondaryInfoBoardCallback<BaseConstruction> callback) {
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
                    Gdx.app.log(SellerPart.class.getSimpleName(), "upgradeButton changed");
                    model.getUpgradeComponent().doUpgrade();
                }
            });

            // ------ changeWorkingLevelGroup ------

            this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
            workingLevelLabel.setAlignment(Align.center);

            // ------ this ------
            this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
            this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
            this.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
            this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
            this.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (model != null) {
                        callback.showAndUpdateGuideInfo(model);
                    }
                    Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "this clicked event");
                    super.clicked(event, x, y);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (model != null && pointer == -1) {
                        callback.showAndUpdateGuideInfo(model);
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (pointer == -1) {
                        callback.hideAndCleanGuideInfo();
                    }
                    super.exit(event, x, y, pointer, toActor);
                }
            });
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


        public void updateAsConstruction(BaseConstruction construction) {
            this.model = construction;
            update();
        }

        @Override
        public void subLogicFrame() {
            update();
        }
    }

    public static class SellerPart extends BaseCellDetailNodeVM {
        BaseDemoPlayScreen parent;
        BaseConstruction model;
        Label constructionNameLabel;
        TextButton upWorkingLevelButton;
        TextButton downWorkingLevelButton;
        Label workingLevelLabel;

        TextButton upgradeButton;
        TextButton transformButton;

        Table changeWorkingLevelGroup;




        public SellerPart(
                BaseDemoPlayScreen parent,
                ISecondaryInfoBoardCallback<BaseConstruction> callback) {
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
                    Gdx.app.log(SellerPart.class.getSimpleName(), "upgradeButton changed");
                    model.getUpgradeComponent().doUpgrade();
                }
            });

            // ------ changeWorkingLevelGroup ------
            this.changeWorkingLevelGroup = new Table();

            this.downWorkingLevelButton = new TextButton("-", parent.getGame().getMainSkin());
            downWorkingLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("ConstructionView", "down clicked");
                    model.getLevelComponent().changeWorkingLevel(-1);
                }
            });
            changeWorkingLevelGroup.add(downWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);

            this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
            workingLevelLabel.setAlignment(Align.center);
            changeWorkingLevelGroup.add(workingLevelLabel).size(CHILD_WIDTH / 2, CHILD_HEIGHT);

            this.upWorkingLevelButton = new TextButton("+", parent.getGame().getMainSkin());
            upWorkingLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log(SellerPart.class.getSimpleName(), "up clicked");
                    model.getLevelComponent().changeWorkingLevel(1);
                }
            });
            changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);


            this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
            // ------ this ------
            this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
            this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
            this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT).row();
            this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
            this.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (model != null) {
                        callback.showAndUpdateGuideInfo(model);
                    }
                    Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "this clicked event");
                    super.clicked(event, x, y);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (model != null && pointer == -1) {
                        callback.showAndUpdateGuideInfo(model);
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (pointer == -1) {
                        callback.hideAndCleanGuideInfo();
                    }
                    super.exit(event, x, y, pointer, toActor);
                }
            });
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

            boolean canUpWorkingLevel = model.getLevelComponent().canChangeWorkingLevel(1);
            if (canUpWorkingLevel) {
                upWorkingLevelButton.setDisabled(false);
                upWorkingLevelButton.getLabel().setColor(Color.WHITE);
            } else {
                upWorkingLevelButton.setDisabled(true);
                upWorkingLevelButton.getLabel().setColor(Color.RED);
            }

            boolean canDownWorkingLevel = model.getLevelComponent().canChangeWorkingLevel(-1);
            if (canDownWorkingLevel) {
                downWorkingLevelButton.setDisabled(false);
                downWorkingLevelButton.getLabel().setColor(Color.WHITE);
            } else {
                downWorkingLevelButton.setDisabled(true);
                downWorkingLevelButton.getLabel().setColor(Color.RED);
            }
            // ------ update model ------
            //model.onLogicFrame();

        }


        public void updateAsConstruction(BaseConstruction construction) {
            this.model = construction;
            if (construction != null) {
                if (construction.getLevelComponent().isTypeWorkingLevelChangeable()) {
                    this.upWorkingLevelButton.setVisible(true);
                    this.downWorkingLevelButton.setVisible(true);
                } else {
                    this.upWorkingLevelButton.setVisible(false);
                    this.downWorkingLevelButton.setVisible(false);
                }
            }
            update();
        }

        @Override
        public void subLogicFrame() {
            update();
        }
    }

}
