package hundun.gdxgame.idlestarter.ui.component.board.construction.impl.fixed;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IGameAreaChangeListener;
import hundun.gdxgame.idleframe.listener.ILogicFrameListener;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idlestarter.ui.component.board.construction.impl.ConstructionControlNode;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;



/**
 * @author hundun
 * Created on 2021/11/05
 */
public class FixedConstructionControlBoard extends AbstractConstructionControlBoard {
    

    public static int FIXED_NODE_NUM = 5;
    
    Table childTable;

    
    public FixedConstructionControlBoard(BasePlayScreen<?> parent) {
        
        super(parent);

        childTable = new Table();
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);

        this.add(childTable);

        this.setBackground(parent.getLayoutConst().simpleBoardBackground);
        
        if (parent.game.debugMode) {
            this.debugCell();
        }
    }
    
    @Override
    protected int initChild(int areaShownConstructionsSize) {
        int childrenSize = FIXED_NODE_NUM;
        
        constructionControlNodes.clear();
        childTable.clearChildren();
        
        for (int i = 0; i < childrenSize; i++) {
            var constructionView = new ConstructionControlNode(parent, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }
        
        return childrenSize;

    }


    
    
    
}
