package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
                    parent.showAndUpdateGuideInfo(model.getDetailDescroption());
                }
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "exit event");
                super.clicked(event, x, y);
            }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null && pointer == -1) {
                    parent.showAndUpdateGuideInfo(model.getDetailDescroption());
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
        
        TextButton upWorkingLevelButton = new TextButton("+", parent.game.getButtonSkin());
        upWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "up clicked");
                model.changeWorkingLevel(1);
            } 
        });
        //upWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 2, CHILD_HEIGHT);
        
        TextButton downWorkingLevelButton = new TextButton("-", parent.game.getButtonSkin());
        downWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "down clicked");
                model.changeWorkingLevel(-1);
            } 
        });
        //downWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.add(downWorkingLevelButton).size(CHILD_WIDTH / 2, CHILD_HEIGHT);
        
        
        //initAsNormalStyle();
        
        this.debug();
        
    }
    

    private void initAsNormalStyle() {
        clearChildren();
        
        this.add(constructionNameLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(clickEffectButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        
        //changeWorkingLevelGroup.setVisible(false);
        
        //this.debug();
    }
    
    
    private void initAsChangeWorkingLevelStyle() {
        //clearStyle();
        
        //changeWorkingLevelGroup.setVisible(true);
        clearChildren();
        
        this.add(constructionNameLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(clickEffectButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT);
        
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

        // ------ update clickable-state ------
        boolean canClickEffect = model.canClickEffect();
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);
        
        
        if (canClickEffect) {
            clickEffectButton.getLabel().setColor(Color.BLACK);
        } else {
            clickEffectButton.setDisabled(true);
            clickEffectButton.getLabel().setColor(Color.RED);
        }
        
        
        // ------ update model ------
        model.onLogicFrame();
        
    }
    
    
}
