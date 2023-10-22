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
import java.util.stream.Collectors;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class MainScreenConstructionControlBoard extends Table
        implements ILogicFrameListener, IGameAreaChangeListener, IConstructionCollectionListener
{


    public static int FIXED_NODE_NUM = 5;

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
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);

        this.add(childTable);

        this.setBackground(parent.getLayoutConst().simpleBoardBackground);

        if (parent.getGame().debugMode) {
            this.debugCell();
        }
    }

    protected int initChild(int areaShownConstructionsSize) {
        int childrenSize = FIXED_NODE_NUM;

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < childrenSize; i++) {
            MainScreenConstructionControlNode constructionView = new MainScreenConstructionControlNode(parent, callback);
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }

        return childrenSize;

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

        int childrenSize = initChild(newConstructions.size());

        for (int i = 0; i < childrenSize && i < newConstructions.size(); i++)
        {
            constructionControlNodes.get(i).updateAsConstruction(newConstructions.get(i));
        }
        for (int i = newConstructions.size(); i < childrenSize; i++)
        {
            constructionControlNodes.get(i).updateAsConstruction(null);
        }
        parent.getGame().getFrontend().log("ConstructionInfoBorad",
                "Constructions change to: " + newConstructions.stream().map(BaseConstruction::getName).collect(Collectors.joining(","))
        );
    }


}
