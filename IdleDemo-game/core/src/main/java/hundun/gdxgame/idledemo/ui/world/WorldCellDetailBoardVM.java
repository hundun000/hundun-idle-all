package hundun.gdxgame.idledemo.ui.world;

import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class WorldCellDetailBoardVM extends BaseCellDetailBoardVM implements IConstructionCollectionListener {
    @Getter
    @Setter
    public BaseConstruction detailingConstruction;


    public WorldCellDetailBoardVM(WorldPlayScreen parent)
    {
        super.postPrefabInitialization(parent);
        updateDetail(null);
    }

    public void updateDetail(BaseConstruction construction)
    {
        this.detailingConstruction = construction;
        if (construction == null)
        {
            updateAsEmpty();
            return;
        }

        switch (construction.getPrototypeId())
        {
            case DemoConstructionPrototypeId.EMPTY_CELL:
                updateAsConstructionPrototypeDetail(construction);
                break;
            default:
                updateAsConstructionDetail(construction);
                break;
        }
    }
    private void updateAsEmpty()
    {
        this.clearChildren();
        contents.clear();
    }

    private void updateAsConstructionDetail(BaseConstruction construction)
    {
        this.clearChildren();
        contents.clear();

        WorldConstructionInstanceCellDetailNode innerBoardVM = new WorldConstructionInstanceCellDetailNode(parent);
        innerBoardVM.updateAsConstruction(construction, construction.getSaveData().getPosition());
        this.add(innerBoardVM);
        contents.add(innerBoardVM);

    }

    private void updateAsConstructionPrototypeDetail(BaseConstruction construction)
    {
        this.clearChildren();
        contents.clear();

        List<AbstractConstructionPrototype> constructionPrototypes = parent.getGame().getIdleGameplayExport()
                .getGameplayContext()
                .getConstructionManager()
                .getAreaShownConstructionPrototypesOrEmpty(parent.getArea());

        constructionPrototypes.forEach(constructionPrototype -> {
            WorldConstructionPrototypeCellDetailNode innerBoardVM = new WorldConstructionPrototypeCellDetailNode(parent);
                innerBoardVM.updateAsConstructionPrototype(constructionPrototype, construction.getSaveData().getPosition());
                this.add(innerBoardVM);
                contents.add(innerBoardVM);
        });

    }

    @Override
    public void onConstructionCollectionChange()
    {
        if (detailingConstruction != null)
        {
            detailingConstruction = parent.getGame().getIdleGameplayExport().getGameplayContext().getConstructionManager().getConstructionAt(detailingConstruction.getPosition());
            updateDetail(detailingConstruction);
        }
    }
}
