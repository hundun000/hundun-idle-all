package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailBoardVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;
import lombok.Setter;

public class WorldCellDetailBoardVM extends BaseCellDetailBoardVM implements IConstructionCollectionListener {
    @Getter
    @Setter
    public BaseConstruction detailingConstruction;


    public WorldCellDetailBoardVM(WorldPlayScreen parent)
    {
        super.postPrefabInitialization(parent);
        selectCell(null);
    }

    public void selectCell(BaseConstruction construction)
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

        WorldConstructionInstanceCellDetailNode innerBoardVM = new WorldConstructionInstanceCellDetailNode(screen);
        innerBoardVM.updateAsConstruction(construction, construction.getSaveData().getPosition());
        this.add(innerBoardVM);
        contents.add(innerBoardVM);

        TextButton textButton = new TextButton("clear", screen.getGame().getMainSkin());
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.onCellClicked(null);
            }
        });
        this.add(textButton);
    }

    private void updateAsConstructionPrototypeDetail(BaseConstruction construction)
    {
        this.clearChildren();
        contents.clear();



        WorldConstructionPrototypeCellDetailNode innerBoardVM = new WorldConstructionPrototypeCellDetailNode(screen);
            innerBoardVM.updateAsConstructionPrototype(construction, construction.getSaveData().getPosition());
            this.add(innerBoardVM);
            contents.add(innerBoardVM);

        TextButton textButton = new TextButton("clear", screen.getGame().getMainSkin());
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.onCellClicked(null);
            }
        });
        this.add(textButton);
    }

    @Override
    public void onConstructionCollectionChange()
    {
        if (detailingConstruction != null)
        {
            detailingConstruction = screen.getGame().getIdleGameplayExport().getGameplayContext().getConstructionManager().getWorldConstructionAt(detailingConstruction.getPosition());
            selectCell(detailingConstruction);
        }
    }
}
