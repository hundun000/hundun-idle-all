package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameBeeScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionView extends VerticalGroup implements ILogicFrameListener {
    GameBeeScreen parent;
    BaseConstruction model;
    TextButton textButton;
    
    public ConstructionView(GameBeeScreen parent, BaseConstruction model) {
        super();
        this.parent = parent;
        this.model = model;
        
        this.textButton = new TextButton("", parent.game.getButtonSkin());
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                model.clickGather();
            } 
        });
        textButton.setBounds(0, 0, 100, 50);
        this.addActor(textButton);
    }

    @Override
    public void onLogicFrame() {
        if (model.canClickGather()) {
            textButton.setText(model.getGatherDescroption());
        } else {
            String newText = model.getName() + "(lv." + model.getLevel() + ")";
            textButton.setText(newText);
            textButton.setLayoutEnabled(model.canUpgrade());
        }
        
    }
    
    
}
