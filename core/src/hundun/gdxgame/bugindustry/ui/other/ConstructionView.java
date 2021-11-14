package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    TextButton textButton;
    
    public ConstructionView(GameScreen parent, int index) {
        super();
        this.parent = parent;
        
        constructionNameLabel = new Label("", parent.game.getButtonSkin());
        constructionNameLabel.setSize(100, 50);
        this.addActor(constructionNameLabel);
        
        this.textButton = new TextButton("", parent.game.getButtonSkin());
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                model.onClick();
            } 
        });
        textButton.setSize(100, 50);
        this.addActor(textButton);
        
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
        this.debugAll();
    }
    
    public void setModel(BaseConstruction model) {
        this.model = model;
    }

    @Override
    public void onLogicFrame() {
        // ------ update show-state ------
        if (model == null) {
            setVisible(false);
            textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
        }
        // ------ update text ------
        constructionNameLabel.setText(model.getName());
        textButton.setText(model.getButtonDescroption());

        boolean clickable = model.canClick();
        textButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);
        
        // ------ update model ------
        model.onLogicFrame();
        
    }
    
    
}
