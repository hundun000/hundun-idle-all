package hundun.gdxgame.idledemo.starter.ui.component.board.construction.impl;

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
import hundun.gdxgame.idledemo.BaseIdleGame;
import hundun.gdxgame.idledemo.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idledemo.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.core.framework.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StarterConstructionControlNode<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {
    BaseIdleScreen<T_GAME, T_SAVE> parent;
    ISecondaryInfoBoardCallback<BaseConstruction> callback;
    BaseConstruction construction;
    Label constructionNameLabel;
    TextButton upWorkingLevelButton;
    TextButton downWorkingLevelButton;
    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton clickOutputButton;
    TextButton upgradeButton;
    TextButton destroyButton;
    TextButton transformButton;

    Table changeWorkingLevelGroup;


    public StarterConstructionControlNode(
            BaseIdleScreen<T_GAME, T_SAVE> parent,
            ISecondaryInfoBoardCallback<BaseConstruction> callback,
            int index, PlayScreenLayoutConst playScreenLayoutConst) {
        super();
        this.parent = parent;
        this.callback = callback;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        this.clickOutputButton = new TextButton("", parent.getGame().getMainSkin());
        clickOutputButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "clickEffectButton changed");
                construction.getOutputComponent().doOutput();
            }
        });

        this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "upgradeButton changed");
                construction.getUpgradeComponent().doUpgrade();
            }
        });

        this.addListener(new StarterSecondaryInfoBoardCallerClickListener(() -> construction, callback));

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
        changeWorkingLevelGroup.add(workingLevelLabel).size(CHILD_WIDTH / 2.0f, CHILD_HEIGHT);

        this.upWorkingLevelButton = new TextButton("+", parent.getGame().getMainSkin());
        upWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "up clicked");
                construction.getLevelComponent().changeWorkingLevel(1);
            }
        });
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4.0f, CHILD_HEIGHT);

        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.positionLabel = new Label("", parent.getGame().getMainSkin());
        this.destroyButton = new TextButton("-", parent.getGame().getMainSkin());
        destroyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "upgradeButton changed");
                construction.getExistenceComponent().doDestroy();
            }
        });
        this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
        transformButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(StarterConstructionControlNode.class.getSimpleName(), "upgradeButton changed");
                construction.getUpgradeComponent().doTransform();
            }
        });
        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.add(clickOutputButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(proficiencyLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }







    public void setModel(BaseConstruction constructionExportProxy) {
        this.construction = constructionExportProxy;
        if (constructionExportProxy != null) {
            if (constructionExportProxy.getLevelComponent().isTypeWorkingLevelChangeable()) {
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
        constructionNameLabel.setText(construction.getDescriptionPackage().getName());
        clickOutputButton.setText(construction.getDescriptionPackage().getClickOutputButtonText());
        upgradeButton.setText(construction.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(DescriptionPackage.Helper.getWorkingLevelDescription(construction));
        proficiencyLabel.setText(DescriptionPackage.Helper.getProficiencyDescription(construction));
        positionLabel.setText(construction.getSaveData().getPosition().toShowText());
        destroyButton.setText(construction.getDescriptionPackage().getDestroyButtonText());

        // ------ update clickable-state ------
        if (construction.getOutputComponent().canOutput()) {
            clickOutputButton.setDisabled(false);
            clickOutputButton.getLabel().setColor(Color.WHITE);
        } else {
            clickOutputButton.setDisabled(true);
            clickOutputButton.getLabel().setColor(Color.RED);
        }
        if (construction.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }
        if (construction.getExistenceComponent().canDestroy())
        {
            destroyButton.setDisabled(false);
            destroyButton.getLabel().setColor(Color.WHITE);
        }
        else
        {
            destroyButton.setDisabled(true);
            destroyButton.getLabel().setColor(Color.RED);
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


}
