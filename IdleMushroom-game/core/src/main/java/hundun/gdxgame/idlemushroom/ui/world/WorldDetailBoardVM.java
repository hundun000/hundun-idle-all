package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idlemushroom.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;
import lombok.Setter;

public class WorldDetailBoardVM extends Table implements IConstructionCollectionListener, ILogicFrameListener {
    @Getter
    @Setter
    public BaseConstruction detailingConstruction;
    BaseCellDetailNodeVM content;
    protected WorldPlayScreen screen;

    public WorldDetailBoardVM(WorldPlayScreen parent)
    {
        this.screen = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        setTouchable(Touchable.enabled);
        selectCell(null);
    }

    @Override
    public void onLogicFrame()
    {
        if (content != null) {
            content.subLogicFrame();
        }
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
            case DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
            case DemoConstructionPrototypeId.EPOCH_2_EMPTY_CELL:
            case DemoConstructionPrototypeId.EPOCH_3_EMPTY_CELL: {
                WorldEmptyDetailNode innerBoardVM = new WorldEmptyDetailNode(screen);
                content = innerBoardVM;
                break;
            }
            case DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
            case DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
            case DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER: {
                WorldMushroomDetailNode innerBoardVM = new WorldMushroomDetailNode(screen);
                content = innerBoardVM;
                break;
            }
            case DemoConstructionPrototypeId.EPOCH_1_TREE:
            case DemoConstructionPrototypeId.EPOCH_2_TREE:
            case DemoConstructionPrototypeId.EPOCH_3_TREE: {
                WorldTreeDetailNode innerBoardVM = new WorldTreeDetailNode(screen);
                content = innerBoardVM;
                break;
            }
            default:
        }

        this.clearChildren();

        content.updateForNewConstruction(construction, construction.getSaveData().getPosition());
        this.add(content)
                //.width(screen.getLayoutConst().WorldConstructionCellDetailNodeWidth)
                .height(screen.getLayoutConst().WorldConstructionCellDetailNodeHeight)
        ;

/*        TextButton textButton = new TextButton("clear", screen.getGame().getMainSkin());
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.onCellClicked(null);
            }
        });
        this.add(textButton);*/
    }
    private void updateAsEmpty()
    {
        this.clearChildren();
        content = null;
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
