package hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport.ConstructionExportData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlNode<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {
    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;
    ConstructionExportData model;
    Label constructionNameLabel;
    TextButton upWorkingLevelButton;
    TextButton downWorkingLevelButton;
    Label workingLevelLabel;

    TextButton clickEffectButton;

    Table changeWorkingLevelGroup;




    public ConstructionControlNode(BaseIdlePlayScreen<T_GAME, T_SAVE> parent, int index, PlayScreenLayoutConst playScreenLayoutConst) {
        super();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        this.clickEffectButton = new TextButton("", parent.getGame().getMainSkin());
        clickEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                parent.getGame().getIdleGameplayExport().constructionOnClick(model.getId());
            }
        });

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model != null) {
                    parent.showAndUpdateGuideInfo(model);
                }
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "exit event");
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
                parent.getGame().getIdleGameplayExport().constructionChangeWorkingLevel(model.getId(), -1);
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
                parent.getGame().getIdleGameplayExport().constructionChangeWorkingLevel(model.getId(), 1);
            }
        });
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);


        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.add(clickEffectButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT);
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }


    private void initAsNormalStyle() {

        this.upWorkingLevelButton.setVisible(false);
        this.downWorkingLevelButton.setVisible(false);

        //changeWorkingLevelGroup.setVisible(false);

        //this.debug();
    }


    private void initAsChangeWorkingLevelStyle() {
        //clearStyle();

        //changeWorkingLevelGroup.setVisible(true);
        this.upWorkingLevelButton.setVisible(true);
        this.downWorkingLevelButton.setVisible(true);



    }

    public void setModel(ConstructionExportData constructionExportData) {
        this.model = constructionExportData;
        if (constructionExportData != null) {
            if (constructionExportData.isWorkingLevelChangable()) {
                initAsChangeWorkingLevelStyle();
            } else {
                initAsNormalStyle();
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
        clickEffectButton.setText(model.getButtonDescroption());
        workingLevelLabel.setText(model.getWorkingLevelDescroption());

        // ------ update clickable-state ------
        boolean canClickEffect = parent.getGame().getIdleGameplayExport().constructionCanClickEffect(model.getId());
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);


        if (canClickEffect) {
            clickEffectButton.getLabel().setColor(Color.WHITE);
        } else {
            clickEffectButton.setDisabled(true);
            clickEffectButton.getLabel().setColor(Color.RED);
        }

        boolean canUpWorkingLevel = parent.getGame().getIdleGameplayExport().constructionCanChangeWorkingLevel(model.getId(), 1);
        if (canUpWorkingLevel) {
            upWorkingLevelButton.getLabel().setColor(Color.WHITE);
        } else {
            upWorkingLevelButton.setDisabled(true);
            upWorkingLevelButton.getLabel().setColor(Color.RED);
        }

        boolean canDownWorkingLevel = parent.getGame().getIdleGameplayExport().constructionCanChangeWorkingLevel(model.getId(), -1);
        if (canDownWorkingLevel) {
            downWorkingLevelButton.getLabel().setColor(Color.WHITE);
        } else {
            downWorkingLevelButton.setDisabled(true);
            downWorkingLevelButton.getLabel().setColor(Color.RED);
        }
        // ------ update model ------
        //model.onLogicFrame();

    }


}
