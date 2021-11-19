package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
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
public class ConstructionView extends VerticalGroup implements ILogicFrameListener {
    GameScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;
    TextButton clickEffectButton;
    
    HorizontalGroup changeWorkingLevelGroup;

    
    public ConstructionView(GameScreen parent, int index) {
        super();
        this.parent = parent;
        
        constructionNameLabel = new Label("", parent.game.getButtonSkin());
        constructionNameLabel.setSize(100, 50);
        this.addActor(constructionNameLabel);
        
        this.clickEffectButton = new TextButton("", parent.game.getButtonSkin());
        clickEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                model.onClick();
            } 
        });
        clickEffectButton.setSize(100, 50);
        this.addActor(clickEffectButton);
        
        this.setBounds(10 + 120 * index, 10, 120, 60);
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null) {
                    parent.showAndUpdateGuideInfo(model.getDetailDescroption());
                }
                super.enter(event, x, y, pointer, fromActor);
            }
            
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                parent.hideAndCleanGuideInfo();
                super.exit(event, x, y, pointer, toActor);
            }
        });
        
    }
    
    private void clearType() {
        if (changeWorkingLevelGroup != null) {
            this.removeActor(changeWorkingLevelGroup);
            changeWorkingLevelGroup = null;
        }
    }
    
    private void initAsNormalType() {
        clearType();
        
        this.debugAll();
    }
    
    
    private void initAsChangeWorkingLevelType() {
        clearType();
        
        this.changeWorkingLevelGroup = new HorizontalGroup();
        
        TextButton upWorkingLevelButton = new TextButton("+", parent.game.getButtonSkin());
        upWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "up clicked");
                model.changeWorkingLevel(1);
            } 
        });
        upWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.addActor(upWorkingLevelButton);
        
        TextButton downWorkingLevelButton = new TextButton("-", parent.game.getButtonSkin());
        downWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "down clicked");
                model.changeWorkingLevel(-1);
            } 
        });
        downWorkingLevelButton.setSize(50, 50);
        changeWorkingLevelGroup.addActor(downWorkingLevelButton);
        
        this.addActor(changeWorkingLevelGroup);
        
        this.debugAll();
    }
    
    public void setModel(BaseConstruction model) {
        this.model = model;
        if (model != null) {
            if (model.isWorkingLevelChangable()) {
                initAsChangeWorkingLevelType();
            } else {
                initAsNormalType();
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

        boolean clickable = model.canClick();
        clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);
        
        // ------ update model ------
        model.onLogicFrame();
        
    }
    
    
}
