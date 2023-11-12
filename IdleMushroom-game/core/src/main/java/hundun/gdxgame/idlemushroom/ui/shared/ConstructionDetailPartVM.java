package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.core.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;

import java.util.ArrayList;
import java.util.List;

public class ConstructionDetailPartVM extends Table {

    BaseIdleMushroomPlayScreen parent;
    BaseConstruction model;

    public ConstructionDetailPartVM(BaseIdleMushroomPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().simpleBoardBackground);
    }


    private static  <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }

    public void rebuildCells(@Null BaseConstruction newModel) {
        if (newModel != null) {
            this.model = newModel;
        }

        this.clearChildren();

        resourcePackAsActor(model.getOutputComponent().getOutputCostPack(), this, parent);

        resourcePackAsActor(model.getOutputComponent().getOutputGainPack(), this, parent);

        if (model.getUpgradeComponent().getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
            resourcePackAsActor(model.getUpgradeComponent().getUpgradeCostPack(), this, parent);
        } else if (model.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER
            || model.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_NO_TRANSFER
        ) {
            this.add(wapperContainer(new Label(model.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin())));
            this.row();
        }


        if (parent.getGame().debugMode) {
            this.debug();
        }
    }

    public static void resourcePackAsActor(ResourcePack pack, Table target, BaseIdleMushroomPlayScreen parent) {
        resourcePackAsActor(pack, target, parent, false);
    }

    public static void resourcePackAsActor(ResourcePack pack, Table target, BaseIdleMushroomPlayScreen parent, boolean isPreviewNextLevel) {
        if (pack != null) {
            List<ResourcePair> targetValue = isPreviewNextLevel ? pack.getPreviewNextLevelModifiedValues() : pack.getModifiedValues();
            if (targetValue != null && !targetValue.isEmpty()) {
                List<Actor> pairsToActors = pairsToActors(targetValue, parent.getGame());
                target.add(wapperContainer(new Label(pack.getDescriptionStart(), parent.getGame().getMainSkin())));
                for (Actor actor : pairsToActors) {
                    target.add(wapperContainer(actor))
                            .height(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_HEIGHT)
                            .width(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_WIDTH);
                }
                target.row();
            }
        }
    }

    public static List<Actor> pairsToActors(List<ResourcePair> pairs, IdleMushroomGame game) {
        List<Actor> pairsToActors = new ArrayList<>();
        for (ResourcePair entry : pairs) {
            ResourceAmountPairNode<IdleMushroomGame> node = new ResourceAmountPairNode<>(game, entry.getType());
            node.update(entry.getAmount());
            pairsToActors.add(node);
        }
        return pairsToActors;
    }


    public void update() {
        rebuildCells(null);
    }


}
