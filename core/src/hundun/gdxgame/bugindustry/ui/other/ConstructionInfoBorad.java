package hundun.gdxgame.bugindustry.ui.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameBeeScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionInfoBorad extends Table implements ILogicFrameListener {
    
    GameBeeScreen parent;
    ConstructionView constructionView;
    
    public ConstructionInfoBorad(GameBeeScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(10, 10, Gdx.graphics.getWidth() - 20, 100);
        
        
        this.constructionView = new ConstructionView(parent, parent.game.getModelContext().getWoodGatherConstruction());
        constructionView.setBounds(10, 10, 120, 60);
        this.addActor(constructionView);
        this.debugAll();
    }

    @Override
    public void onLogicFrame() {
        this.constructionView.onLogicFrame();
    }
    
//    int nodeGroupWidth = (100 + 50 + 10);
//    private WidgetGroup createUpgradeNodeGroup(int index) {
//        HorizontalGroup group = new HorizontalGroup();
//        Button upgradeButton = new TextButton("upgrade " + index, parent.game.getButtonSkin());
//        upgradeButton.setSize(100, 50);
//        upgradeButton.addListener(new ClickListener() {
//            @Override
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                super.enter(event, x, y, pointer, fromActor);
//                Gdx.app.log("test", "enter");
//            }
//            
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                super.exit(event, x, y, pointer, toActor);
//                Gdx.app.log("test", "exit");
//            }
//            
//        });
//        //upgradeButton.setPosition(5, 0);
//        group.addActor(upgradeButton);
//        
//        Button questionButton = new TextButton("?", parent.game.getButtonSkin());
//        questionButton.setSize(50, 50);
//        //questionButton.setPosition(100, 5);
//        group.addActor(questionButton);
//        
//        group.setSize(160, 60);
//        group.setPosition(index * nodeGroupWidth, 0);
//        return group;
//    }
    
}
