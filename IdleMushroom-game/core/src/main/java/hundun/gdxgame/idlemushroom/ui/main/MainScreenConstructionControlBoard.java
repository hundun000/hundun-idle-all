package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomMainPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idlemushroom.ui.shared.ConstructionDetailPartVM;
import hundun.gdxgame.idleshare.core.framework.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

import java.util.List;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class MainScreenConstructionControlBoard extends Table
        implements ILogicFrameListener, IGameAreaChangeListener, IConstructionCollectionListener
{

    IdleMushroomMainPlayScreen parent;
    protected ISecondaryInfoBoardCallback<Object> callback;

    protected SellerPart sellerPart;
    protected EpochPart epochPart;

    public MainScreenConstructionControlBoard(IdleMushroomMainPlayScreen parent, ISecondaryInfoBoardCallback<Object> callback) {
        this.parent = parent;
        this.callback = callback;


        this.setBackground(parent.getGame().getTextureManager().getTableType1Drawable());
        this.pad(20);

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
        this.add(sellerPart).spaceRight(10).grow();

        this.epochPart = new EpochPart(parent, callback);
        this.add(epochPart).spaceRight(10).grow();

        BaseConstruction sellerConstruction = singletonConstructions.stream()
                .filter(it -> it.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.MUSHROOM_AUTO_SELLER))
                .findAny()
                .orElse(null);
        this.sellerPart.updateForNewConstruction(sellerConstruction, null);

        BaseConstruction epochConstruction = singletonConstructions.stream()
                .filter(it -> it.getPrototypeId().equals(IdleMushroomConstructionPrototypeId.EPOCH_COUNTER))
                .findAny()
                .orElse(null);
        this.epochPart.updateForNewConstruction(epochConstruction, null);

    }

    public static class EpochPart extends BaseCellDetailNodeVM {
        IdleMushroomMainPlayScreen parent;
        BaseConstruction model;
        Label constructionNameLabel;

        Label workingLevelLabel;

        TextButton upgradeButton;


        Table leftPart;
        protected ConstructionDetailPartVM epochDetailPart;


        public EpochPart(
                IdleMushroomMainPlayScreen parent,
                ISecondaryInfoBoardCallback<Object> callback) {
            super();
            final IdleMushroomPlayScreenLayoutConst playScreenLayoutConst = parent.getIdleMushroomPlayScreenLayoutConst();
            this.parent = parent;

            int CHILD_WIDTH = playScreenLayoutConst.EPOCH_PART_CHILD_WIDTH;
            int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
            int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

            this.leftPart = new Table();
            this.epochDetailPart = new ConstructionDetailPartVM(parent);

            this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
            constructionNameLabel.setWrap(true);


            this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
            upgradeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.log(SellerPart.class.getSimpleName(), "upgradeButton changed");
                    model.getUpgradeComponent().doUpgrade();
                    parent.getGame().getIdleMushroomExtraGameplayExport().doChangeEpoch(model.getSaveData().getLevel());
                }
            });

            this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
            workingLevelLabel.setAlignment(Align.center);

            Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getTextureManager().getQuestionMarkTexture()));
            questionMarkArea.setBackground(parent.getGame().getTextureManager().getQuestionMarkTableDrawable());
            questionMarkArea.setTouchable(Touchable.enabled);
            questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener<>(() -> model, parent));

            // ------ this ------
            leftPart.add(constructionNameLabel).size(playScreenLayoutConst.SELLER_PART_NAME_WIDTH, NAME_CHILD_HEIGHT);
            leftPart.add(questionMarkArea)
                    .size(parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize, parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize)
                    .row();
            leftPart.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).colspan(2).row();
            leftPart.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT).colspan(2).row();

            this.add(leftPart).padRight(20);
            this.add(epochDetailPart);

            this.setBackground(parent.getGame().getTextureManager().getTableType3Drawable());
        }

        private void update() {
            // ------ update show-state ------
            if (model == null) {
                setVisible(false);
                return;
            } else {
                setVisible(true);
            }
            // ------ update text ------
            constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                    "%s",
                    model.getDescriptionPackage().getName()
            ));
            upgradeButton.setText(model.getDescriptionPackage().getUpgradeButtonText());
            workingLevelLabel.setText(DescriptionPackage.Helper.getWorkingLevelDescription(model));


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
            this.epochDetailPart.rebuildCells(construction);
            update();
        }

        @Override
        public void subLogicFrame() {
            update();
        }
    }

    public static class SellerPart extends BaseCellDetailNodeVM {
        IdleMushroomMainPlayScreen parent;
        BaseConstruction construction;
        Label constructionNameLabel;
        TextButton upWorkingLevelButton;
        TextButton downWorkingLevelButton;
        Label workingLevelLabel;

        TextButton upgradeButton;

        Table changeWorkingLevelGroup;

        Table leftPart;
        Table detailGroup;


        public SellerPart(
                IdleMushroomMainPlayScreen parent,
                ISecondaryInfoBoardCallback<Object> callback) {
            super();
            final IdleMushroomPlayScreenLayoutConst playScreenLayoutConst = parent.getIdleMushroomPlayScreenLayoutConst();
            this.parent = parent;

            int CHILD_WIDTH = playScreenLayoutConst.SELLER_PART_CHILD_WIDTH;
            int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
            int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

            leftPart = new Table();

            this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
            constructionNameLabel.setWrap(true);


            this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
            upgradeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.log(SellerPart.class.getSimpleName(), "upgradeButton changed");
                    construction.getUpgradeComponent().doUpgrade();
                }
            });

            // ------ changeWorkingLevelGroup ------
            this.changeWorkingLevelGroup = new Table();

            this.downWorkingLevelButton = new TextButton("-", parent.getGame().getMainSkin());
            downWorkingLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("ConstructionView", "down clicked");
                    construction.getLevelComponent().changeWorkingLevel(-1);
                }
            });
            changeWorkingLevelGroup.add(downWorkingLevelButton).size(CHILD_WIDTH / 4.0f, CHILD_HEIGHT);

            this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
            workingLevelLabel.setAlignment(Align.center);

            this.upWorkingLevelButton = new TextButton("+", parent.getGame().getMainSkin());
            upWorkingLevelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log(SellerPart.class.getSimpleName(), "up clicked");
                    construction.getLevelComponent().changeWorkingLevel(1);
                }
            });
            changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4.0f, CHILD_HEIGHT);

            detailGroup = new Table();

            Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getTextureManager().getQuestionMarkTexture()));
            questionMarkArea.setBackground(parent.getGame().getTextureManager().getQuestionMarkTableDrawable());
            questionMarkArea.setTouchable(Touchable.enabled);
            questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener<>(() -> construction, parent));

            // ------ this ------
            leftPart.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT);
            leftPart.add(questionMarkArea)
                    .size(parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize, parent.getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize)
                    .row();
            leftPart.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).colspan(2).row();
            leftPart.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT).colspan(2).row();
            leftPart.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT).colspan(2).row();

            this.add(leftPart).padRight(20);
            this.add(detailGroup);

            this.setBackground(parent.getGame().getTextureManager().getTableType3Drawable());
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

            detailGroup.clearChildren();
            ConstructionDetailPartVM.resourcePackAsActor(
                    construction.getDescriptionPackage().getOutputCostDescriptionStart(),
                    construction.getOutputComponent().getOutputCostPack(),
                    detailGroup, parent);

            ConstructionDetailPartVM.resourcePackAsActor(
                    construction.getDescriptionPackage().getOutputGainDescriptionStart(),
                    construction.getOutputComponent().getOutputGainPack(),
                    detailGroup, parent);

            if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
                ConstructionDetailPartVM.resourcePackAsActor(
                        construction.getDescriptionPackage().getUpgradeCostDescriptionStart(),
                        construction.getUpgradeComponent().getUpgradeCostPack(),
                        detailGroup,
                        parent);
            } else if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_NO_TRANSFER
                || construction.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER
            ) {
                detailGroup.add(new Label(construction.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin()))
                        .colspan(2);
            }

            // ------ update text ------
            constructionNameLabel.setText(construction.getDescriptionPackage().getName());
            upgradeButton.setText(construction.getDescriptionPackage().getUpgradeButtonText());
            workingLevelLabel.setText(DescriptionPackage.Helper.getWorkingLevelDescription(construction));


            // ------ update clickable-state ------

            if (construction.getUpgradeComponent().canUpgrade()) {
                upgradeButton.setDisabled(false);
                upgradeButton.getLabel().setColor(Color.WHITE);
            } else {
                upgradeButton.setDisabled(true);
                upgradeButton.getLabel().setColor(Color.RED);
            }

            boolean canUpWorkingLevel = construction.getLevelComponent().canChangeWorkingLevel(1);
            if (canUpWorkingLevel) {
                upWorkingLevelButton.setDisabled(false);
                upWorkingLevelButton.getLabel().setColor(Color.WHITE);
            } else {
                upWorkingLevelButton.setDisabled(true);
                upWorkingLevelButton.getLabel().setColor(Color.RED);
            }

            boolean canDownWorkingLevel = construction.getLevelComponent().canChangeWorkingLevel(-1);
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


        @Override
        public void updateForNewConstruction(BaseConstruction construction, GridPosition position) {
            this.construction = construction;
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
