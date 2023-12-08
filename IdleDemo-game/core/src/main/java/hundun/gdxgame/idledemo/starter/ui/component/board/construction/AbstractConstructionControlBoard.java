package hundun.gdxgame.idledemo.starter.ui.component.board.construction;
/**
 * @author hundun
 * Created on 2022/02/09
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.starter.ui.component.board.construction.impl.StarterConstructionControlNode;
import hundun.gdxgame.idledemo.BaseIdleGame;
import hundun.gdxgame.idledemo.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public abstract class AbstractConstructionControlBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table
        implements ILogicFrameListener, IGameAreaChangeListener, IConstructionCollectionListener {
    protected BaseIdleScreen<T_GAME, T_SAVE> parent;
    protected ISecondaryInfoBoardCallback<BaseConstruction> callback;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    protected List<StarterConstructionControlNode<T_GAME, T_SAVE>> constructionControlNodes = new ArrayList<>();



    public AbstractConstructionControlBoard(BaseIdleScreen<T_GAME, T_SAVE> parent, ISecondaryInfoBoardCallback<BaseConstruction> callback) {
        super();
        this.parent = parent;
        this.callback = callback;
    }

    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.update());
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        onConstructionCollectionChange();
    }

    @Override
    public void onConstructionCollectionChange() {
        List<BaseConstruction> newConstructions = parent.getGame().getIdleGameplayExport().getGameplayContext()
                .getConstructionManager().getSingletonConstructionInstancesOrEmpty();
        newConstructions = filterConstructions(newConstructions);

        int childrenSize = initChild(newConstructions.size());

        for (int i = 0; i < childrenSize && i < newConstructions.size(); i++)
        {
            constructionControlNodes.get(i).setModel(newConstructions.get(i));
        }
        for (int i = newConstructions.size(); i < childrenSize; i++)
        {
            constructionControlNodes.get(i).setModel(null);
        }
        parent.getGame().getFrontend().log("ConstructionInfoBorad",
                "Constructions change to: " + newConstructions.stream().map(it -> it.getDescriptionPackage().getName()).collect(Collectors.joining(","))
            );
    }

    /**
     * 初始化某个数量的Children。该数量不一定等于areaShownConstructionsSize，由实现决定。
     * @return childrenSize
     */
    protected abstract int initChild(int areaShownConstructionsSize);

    /**
     * 子类可添加筛选
     */
    protected List<BaseConstruction> filterConstructions(List<BaseConstruction> constructions)
    {
        return constructions;
    }

}
