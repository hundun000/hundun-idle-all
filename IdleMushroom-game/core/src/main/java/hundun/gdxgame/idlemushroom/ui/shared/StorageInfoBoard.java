package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEvent;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.StorageManager.ModifyResourceTag;
import lombok.Getter;

import java.util.*;
import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard extends Table implements IResourceChangeListener {



    public static int NUM_NODE_PER_ROW = 5;

    List<String> shownOrders;
    Set<String> shownTypes = new HashSet<>();
    BaseIdleMushroomScreen parent;

    List<Node> nodes = new ArrayList<>();

    public static class Node extends HorizontalGroup {
        LabelStyle PLUS_STYLE;
        LabelStyle MINUS_STYLE;
        IdleMushroomGame game;

        @Getter
        String resourceType;

        Image image;
        Label amountLabel;
        Label deltaLabel;

        public Node(IdleMushroomGame game, String resourceType) {
            super();
            this.game = game;
            this.resourceType = resourceType;
            TextureRegion textureRegion = game.getTextureManager().getResourceIcon(resourceType);
            this.image = new Image(textureRegion);
            this.addActor(image);
            this.amountLabel = new Label("", game.getMainSkin());
            this.addActor(amountLabel);
            this.deltaLabel = new Label("", game.getMainSkin());
            this.addActor(deltaLabel);

            this.PLUS_STYLE = game.getMainSkin().get("green_style", LabelStyle.class);
            this.MINUS_STYLE = game.getMainSkin().get("red_style", LabelStyle.class);
        }

        public void update(long amount, long... delta) {
            amountLabel.setText(
                    game.getTextFormatTool().format(amount)
            );
            StringBuilder deltaLabelText = new StringBuilder();
            for (int i = 0; i < delta.length; i++) {
                if (delta[i] > 0)
                {
                    deltaLabelText.append("(+").append(game.getTextFormatTool().format(delta[i])).append(")");
                    deltaLabel.setStyle(PLUS_STYLE);
                }
                else if (delta[i] < 0)
                {
                    deltaLabelText.append("(-").append(game.getTextFormatTool().format(Math.abs(delta[i]))).append(")");
                    deltaLabel.setStyle(MINUS_STYLE);
                }
            }
            deltaLabel.setText(deltaLabelText.toString());
        }




    }
    
    public void lazyInit(List<String> shownOrders) {
        this.shownOrders = shownOrders;
        updateViewData();
    }

    //Label mainLabel;


    public StorageInfoBoard(BaseIdleMushroomScreen parent) {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());


        if (parent.getGame().debugMode) {
            this.debugAll();
        }
    }



    private void rebuildCells() {
        this.clearChildren();
        nodes.clear();

        for (int i = 0; i < shownOrders.size(); i++) {
            String resourceType = shownOrders.get(i);
            if (shownTypes.contains(resourceType)) {
                Node node = new Node(parent.getGame(), resourceType);
                nodes.add(node);
                shownTypes.add(resourceType);
                Cell<Node> cell = this.add(node)
                        .width(parent.getLayoutConst().STORAGE_BOARD_NODE_WIDTH)
                        .height(parent.getLayoutConst().STORAGE_BOARD_NODE_HEIGHT);
                if ((i + 1) % NUM_NODE_PER_ROW == 0) {
                    cell.row();
                }
            }
        }

    }



    public void updateViewData() {
        Set<String> unlockedResourceTypes = parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().getUnlockedResourceTypes();
        boolean needRebuildCells = !shownTypes.equals(unlockedResourceTypes);
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(unlockedResourceTypes);
            rebuildCells();
        }

        nodes.stream().forEach(node -> {
            long historySum = parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().getSecondChangeMap(ModifyResourceTag.OUTPUT, 0).getOrDefault(node.getResourceType(), 0L);
            long amount = parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().getResourceNumOrZero(node.getResourceType());
            node.update(amount, historySum);
        });
    }


    @Override
    public void onResourceChange(OneSecondResourceChangeEvent event) {
        updateViewData();
    }
}
