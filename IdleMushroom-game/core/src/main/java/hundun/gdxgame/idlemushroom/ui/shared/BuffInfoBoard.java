package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.util.Utils;
import lombok.Getter;

import java.util.List;
import java.util.*;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class BuffInfoBoard extends Table implements IBuffChangeListener {



    public static int NUM_NODE_PER_ROW = 5;

    List<String> buffIds;
    Set<String> shownTypes = new HashSet<>();
    BaseIdleMushroomScreen parent;

    List<Node> nodes = new ArrayList<>();

    @Override
    public void onBuffChange(Map<String, Integer> changeMap) {
        updateViewData(changeMap);
    }


    public static class Node extends HorizontalGroup {
        IdleMushroomGame game;

        @Getter
        String buffId;

        Image image;
        Label amountLabel;
        Label deltaLabel;

        public Node(IdleMushroomGame game, String buffId) {
            super();
            this.game = game;
            this.buffId = buffId;
            TextureRegion textureRegion = game.getTextureManager().getResourceIcon(buffId);
            this.image = new Image(textureRegion);
            this.addActor(image);
            this.amountLabel = new Label("", game.getMainSkin());
            this.addActor(amountLabel);
            this.deltaLabel = new Label("", game.getMainSkin());
            this.addActor(deltaLabel);

        }

        public void update(long delta, long amount) {
            amountLabel.setText(
                    game.getTextFormatTool().format(amount)
            );
            if (delta > 0)
            {
                deltaLabel.setText("(+" + delta + ")");
            }
            else if(delta == 0)
            {
                deltaLabel.setText("");
            }
            else
            {
                deltaLabel.setText("(-" + Math.abs(delta) + ")");
            }
        }




    }

    public void lazyInit(List<String> shownOrders) {
        this.buffIds = shownOrders;
        rebuildCells();
    }

    //Label mainLabel;


    public BuffInfoBoard(BaseIdleMushroomScreen parent) {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());


        if (parent.getGame().debugMode) {
            this.debugAll();
        }
    }



    private void rebuildCells() {
        this.clearChildren();
        nodes.clear();

        for (int i = 0; i < buffIds.size(); i++) {
            String resourceType = buffIds.get(i);
            if (shownTypes.contains(resourceType)) {
                Node node = new Node(parent.getGame(), resourceType);
                nodes.add(node);
                Cell<Node> cell = this.add(node)
                        .width(parent.getLayoutConst().STORAGE_BOARD_NODE_WIDTH)
                        .height(parent.getLayoutConst().STORAGE_BOARD_NODE_HEIGHT);
                if ((i + 1) % NUM_NODE_PER_ROW == 0) {
                    cell.row();
                }
            }
        }

    }



    public void updateViewData(Map<String, Integer> changeMap) {
        Set<String> unlockedResourceTypes = new HashSet<>(IdleMushroomBuffId.VALUES_FOR_SHOW_ORDER);
        boolean needRebuildCells = !shownTypes.equals(unlockedResourceTypes);
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(unlockedResourceTypes);
            rebuildCells();
        }

        nodes.stream().forEach(node -> {
            long historySum = 0;
            long amount = parent.getGame().getIdleGameplayExport().getGameplayContext().getBuffManager().getBuffLevelMap().getOrDefault(node.getBuffId(), 0);
            node.update(historySum, amount);
        });
    }

}
