package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ConstructionDetailPartVM extends Table {

    BaseIdleMushroomPlayScreen parent;
    BaseConstruction construction;

    public ConstructionDetailPartVM(BaseIdleMushroomPlayScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
    }


    private static  <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }

    public void rebuildCells(@Null BaseConstruction newModel) {
        if (newModel != null) {
            this.construction = newModel;
        }

        this.clearChildren();

        resourcePackAsActor(
                construction.getDescriptionPackage().getOutputCostDescriptionStart(),
                construction.getOutputComponent().getOutputCostPack(),
                this, parent);

        resourcePackAsActor(
                construction.getDescriptionPackage().getOutputGainDescriptionStart(),
                construction.getOutputComponent().getOutputGainPack(),
                this, parent);

        if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
            resourcePackAsActor(
                    construction.getDescriptionPackage().getUpgradeCostDescriptionStart(),
                    construction.getUpgradeComponent().getUpgradeCostPack(),
                    this, parent);
        } else if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER
            || construction.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_NO_TRANSFER
        ) {
            this.add(wapperContainer(new Label(construction.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin())));
            this.row();
        }


        if (parent.getGame().debugMode) {
            this.debug();
        }
    }

    public static void resourcePackAsActor(String descriptionStart, ResourcePack pack, Table target, BaseIdleMushroomPlayScreen parent) {
        resourcePackAsActor(descriptionStart, pack, target, parent.getGame(), false);
    }

    public static void resourcePackAsActor(
            String descriptionStart,
            ResourcePack pack,
            Table target,
            IdleMushroomGame game,
            boolean isPreviewNextLevel
    ) {
        if (pack != null) {
            List<ResourcePair> targetValue = isPreviewNextLevel ? pack.getPreviewNextLevelModifiedValues() : pack.getModifiedValues();
            if (targetValue != null && !targetValue.isEmpty()) {
                List<Actor> pairsToActors = pairsToActors(targetValue, game);
                target.add(wapperContainer(new Label(descriptionStart, game.getMainSkin())));
                for (Actor actor : pairsToActors) {
                    target.add(wapperContainer(actor))
                            .height(game.getIdleMushroomPlayScreenLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_HEIGHT)
                            .width(game.getIdleMushroomPlayScreenLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_WIDTH);
                }
                target.row();
            }
        }
    }

    public static List<Actor> pairsToActors(List<ResourcePair> pairs, IdleMushroomGame game) {
        List<Actor> pairsToActors = new ArrayList<>();
        for (ResourcePair entry : pairs) {
            IdleMushroomResourceAmountPairNode node = new IdleMushroomResourceAmountPairNode(game, entry.getType());
            node.update(entry.getAmount());
            pairsToActors.add(node);
        }
        return pairsToActors;
    }


    public void update() {
        rebuildCells(null);
    }


    public static class IdleMushroomResourceAmountPairNode extends HorizontalGroup {

        IdleMushroomGame game;

        @Getter
        String resourceType;

        Image image;
        Label label;

        public IdleMushroomResourceAmountPairNode(IdleMushroomGame game, String resourceType) {
            super();
            this.game = game;
            this.resourceType = resourceType;
            TextureRegion textureRegion = game.getTextureManager().getResourceIcon(resourceType);
            this.image = new Image(textureRegion);
            this.addActor(image);
            this.label = new Label("", game.getMainSkin());
            this.addActor(label);
        }

        public void update(long amout) {
            label.setText(
                    game.getTextFormatTool().format(amout)
            );
        }




    }
}
