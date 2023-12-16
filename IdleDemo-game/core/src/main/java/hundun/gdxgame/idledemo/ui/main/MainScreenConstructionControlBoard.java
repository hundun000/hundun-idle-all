package hundun.gdxgame.idledemo.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class MainScreenConstructionControlBoard extends Table
        implements ILogicFrameListener, IGameAreaChangeListener, IConstructionCollectionListener
{




    Table childTable;
    BaseDemoPlayScreen parent;
    protected ISecondaryInfoBoardCallback<BaseConstruction> callback;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    protected List<MainScreenConstructionControlNode> constructionControlNodes = new ArrayList<>();
    public MainScreenConstructionControlBoard(BaseDemoPlayScreen parent, ISecondaryInfoBoardCallback<BaseConstruction> callback) {
        this.parent = parent;
        this.callback = callback;

        childTable = new Table();
        childTable.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        this.add(childTable);

        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        if (parent.getGame().debugMode) {
            this.debugCell();
        }
    }


    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.subLogicFrame());
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        onConstructionCollectionChange();
    }

    @Override
    public void onConstructionCollectionChange() {
        List<BaseConstruction> newConstructions = parent.getGame().getIdleGameplayExport().getGameplayContext()
                .getConstructionManager().getSingletonConstructionInstancesOrEmpty();

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < newConstructions.size(); i++) {
            MainScreenConstructionControlNode constructionView = new MainScreenConstructionControlNode(parent, callback);
            constructionView.updateAsConstruction(newConstructions.get(i));
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).size(parent.getLayoutConst().CONSTRUCTION_BUTTON_AREA_WIDTH, parent.getLayoutConst().CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT).spaceRight(10);
        }
    }


}
