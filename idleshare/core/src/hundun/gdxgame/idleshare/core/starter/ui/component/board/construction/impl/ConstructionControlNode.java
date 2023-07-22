package hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl;

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
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlNode<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {
    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;
    BaseConstruction model;
    Label constructionNameLabel;
    TextButton upWorkingLevelButton;
    TextButton downWorkingLevelButton;
    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton clickOutputButton;
    TextButton upgradeButton;
    TextButton destoryButton;
    TextButton transformButton;

    Table changeWorkingLevelGroup;




    public ConstructionControlNode(BaseIdlePlayScreen<T_GAME, T_SAVE> parent, int index, PlayScreenLayoutConst playScreenLayoutConst) {
        super();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        this.clickOutputButton = new TextButton("", parent.getGame().getMainSkin());
        clickOutputButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "clickEffectButton changed");
                model.getOutputComponent().doOutput();
            }
        });

        this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "upgradeButton changed");
                model.getUpgradeComponent().doUpgrade();
            }
        });

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model != null) {
                    parent.showAndUpdateGuideInfo(model);
                }
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "this clicked event");
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null && pointer == -1) {
                    parent.showAndUpdateGuideInfo(model);
                }
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    parent.hideAndCleanGuideInfo();
                }
                super.exit(event, x, y, pointer, toActor);
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
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "up clicked");
                model.getLevelComponent().changeWorkingLevel(1);
            }
        });
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);

        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.positionLabel = new Label("", parent.getGame().getMainSkin());
        this.destoryButton = new TextButton("-", parent.getGame().getMainSkin());
        this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.add(clickOutputButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(proficiencyLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }







    public void setModel(BaseConstruction constructionExportProxy) {
        this.model = constructionExportProxy;
        if (constructionExportProxy != null) {
            if (constructionExportProxy.getLevelComponent().isWorkingLevelChangable()) {
                this.upWorkingLevelButton.setVisible(true);
                this.downWorkingLevelButton.setVisible(true);
            } else {
                this.upWorkingLevelButton.setVisible(false);
                this.downWorkingLevelButton.setVisible(false);
            }
            if (constructionExportProxy.getOutputComponent().isTypeClickOutput()) {
                this.clickOutputButton.setVisible(true);
                this.upgradeButton.setVisible(false);
            } else {
                this.clickOutputButton.setVisible(false);
                this.upgradeButton.setVisible(true);
            }
        }
        update();
    }

    public void update() {
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
        constructionNameLabel.setText(model.getName());
        clickOutputButton.setText(model.getDescriptionPackage().getClickOutputButtonText());
        upgradeButton.setText(model.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(model.getLevelComponent().getWorkingLevelDescroption());
        proficiencyLabel.setText(model.getProficiencyComponent().getProficiencyDescroption());
        positionLabel.setText(model.getSaveData().getPosition().toShowText());
        destoryButton.setText(model.descriptionPackage.getDestroyButtonText());

        // ------ update clickable-state ------
        if (model.getOutputComponent().canOutput()) {
            clickOutputButton.setDisabled(false);
            clickOutputButton.getLabel().setColor(Color.WHITE);
        } else {
            clickOutputButton.setDisabled(true);
            clickOutputButton.getLabel().setColor(Color.RED);
        }
        if (model.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }
        if (model.getExistenceComponent().canDestory())
        {
            destoryButton.setDisabled(false);
            destoryButton.getLabel().setColor(Color.WHITE);
        }
        else
        {
            destoryButton.setDisabled(true);
            destoryButton.getLabel().setColor(Color.RED);
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


}
