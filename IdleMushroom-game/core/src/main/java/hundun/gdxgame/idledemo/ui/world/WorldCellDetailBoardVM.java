package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idleshare.gamelib.framework.callback.IConstructionCollectionListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;
import lombok.Setter;

public class WorldCellDetailBoardVM extends Table implements IConstructionCollectionListener, ILogicFrameListener {
    @Getter
    @Setter
    public BaseConstruction detailingConstruction;
    BaseCellDetailNodeVM content;
    protected BaseDemoPlayScreen screen;

    public WorldCellDetailBoardVM(WorldPlayScreen parent)
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
        content = null;
    }

    private void updateAsConstructionDetail(BaseConstruction construction)
    {
        this.clearChildren();

        WorldConstructionInstanceCellDetailNode innerBoardVM = new WorldConstructionInstanceCellDetailNode(screen);
        innerBoardVM.updateAsConstruction(construction, construction.getSaveData().getPosition());
        this.add(innerBoardVM)
                .width(screen.getLayoutConst().WorldConstructionCellDetailNodeWidth)
                .height(screen.getLayoutConst().WorldConstructionCellDetailNodeHeight);
        content = innerBoardVM;

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

        AbstractConstructionPrototype constructionPrototype = screen.getGame().getIdleGameplayExport()
                .getGameplayContext()
                .getConstructionManager()
                .getEmptyConstructionPrototype();


        WorldConstructionPrototypeCellDetailNode innerBoardVM = new WorldConstructionPrototypeCellDetailNode(screen);
        innerBoardVM.updateAsConstructionPrototype(construction, constructionPrototype, construction.getSaveData().getPosition());
        this.add(innerBoardVM)
                .width(screen.getLayoutConst().WorldConstructionCellDetailNodeWidth)
                .height(screen.getLayoutConst().WorldConstructionCellDetailNodeHeight);
        content = innerBoardVM;

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
            detailingConstruction = screen.getGame().getIdleGameplayExport().getGameplayContext().getConstructionManager().getConstructionAt(detailingConstruction.getPosition());
            selectCell(detailingConstruction);
        }
    }
}
