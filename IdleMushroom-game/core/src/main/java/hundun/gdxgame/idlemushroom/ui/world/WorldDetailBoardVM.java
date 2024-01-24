package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomConstructionPrototypeId;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomWorldPlayScreen;
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
    protected IdleMushroomWorldPlayScreen screen;

    public WorldDetailBoardVM(IdleMushroomWorldPlayScreen parent)
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
            case IdleMushroomConstructionPrototypeId.EPOCH_1_EMPTY_CELL:
            case IdleMushroomConstructionPrototypeId.EPOCH_2_EMPTY_CELL:
            case IdleMushroomConstructionPrototypeId.EPOCH_3_EMPTY_CELL: {
                WorldEmptyDetailNode innerBoardVM = new WorldEmptyDetailNode(screen);
                content = innerBoardVM;
                break;
            }
            case IdleMushroomConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER:
            case IdleMushroomConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER:
            case IdleMushroomConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER: {
                WorldMushroomDetailNode innerBoardVM = new WorldMushroomDetailNode(screen);
                content = innerBoardVM;
                break;
            }
            case IdleMushroomConstructionPrototypeId.EPOCH_1_TREE:
            case IdleMushroomConstructionPrototypeId.EPOCH_2_TREE:
            case IdleMushroomConstructionPrototypeId.EPOCH_3_TREE: {
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
