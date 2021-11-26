package hundun.gdxgame.bugindustry.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlNode extends Table implements ILogicFrameListener {
    GameScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;
    TextButton upWorkingLevelButton;
    TextButton downWorkingLevelButton;
    Label workingLevelLabel;
    
    TextButton clickEffectButton;
    
    Table changeWorkingLevelGroup;

    int SELF_WIDTH = 120;
    int SELF_HEIGHT = 100;
    
    int CHILD_WIDTH = 100;
    int CHILD_HEIGHT = 30;
    
    
    public ConstructionControlNode(GameScreen parent, int index) {
        super();
        this.parent = parent;
        
        this.constructionNameLabel = new Label("", parent.game.getButtonSkin());
        
        this.clickEffectButton = new TextButton("", parent.game.getButtonSkin());
        clickEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                model.onClick();
            } 
        });

        
        
        this.setBounds(5 + SELF_WIDTH * index, 5, SELF_WIDTH, SELF_HEIGHT);
        //this.setTouchable(Touchable.enabled);
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
        
        
        this.changeWorkingLevelGroup = new Table();
        
        this.upWorkingLevelButton = new TextButton("+", parent.game.getButtonSkin());
        upWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "up clicked");
                model.changeWorkingLevel(1);
            } 
        });
        //upWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);
        
        this.workingLevelLabel = new Label("", parent.game.getButtonSkin());
        workingLevelLabel.setAlignment(Align.center);
        changeWorkingLevelGroup.add(workingLevelLabel).size(CHILD_WIDTH / 2, CHILD_HEIGHT);
        
        this.downWorkingLevelButton = new TextButton("-", parent.game.getButtonSkin());
        downWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "down clicked");
                model.changeWorkingLevel(-1);
            } 
        });
        //downWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.add(downWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);
        
        
        //initAsNormalStyle();
        
        //this.debug();
        this.add(constructionNameLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(clickEffectButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT);
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
    
    public void setModel(BaseConstruction model) {
        this.model = model;
        if (model != null) {
            if (model.isWorkingLevelChangable()) {
                initAsChangeWorkingLevelStyle();
            } else {
                initAsNormalStyle();
            }
        }
    }

    @Override
    public void onLogicFrame() {
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
        boolean canClickEffect = model.canClickEffect();
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);
        
        
        if (canClickEffect) {
            clickEffectButton.getLabel().setColor(Color.WHITE);
        } else {
            clickEffectButton.setDisabled(true);
            clickEffectButton.getLabel().setColor(Color.RED);
        }
        
        
        // ------ update model ------
        model.onLogicFrame();
        
    }
    
    
}
