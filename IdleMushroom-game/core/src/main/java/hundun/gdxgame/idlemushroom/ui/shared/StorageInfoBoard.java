package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idleshare.core.starter.ui.component.StorageResourceAmountPairNode;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.util.Utils;

import java.util.*;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard extends Table implements IOneFrameResourceChangeListener {

    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 120;

    public static int NUM_NODE_PER_ROW = 5;

    List<String> shownOrders;
    Set<String> shownTypes = new HashSet<>();
    BaseIdleMushroomScreen parent;

    List<StorageResourceAmountPairNode<IdleMushroomGame>> nodes = new ArrayList<>();

    public void lazyInit(List<String> shownOrders) {
        this.shownOrders = shownOrders;
        rebuildCells();
    }

    //Label mainLabel;


    public StorageInfoBoard(BaseIdleMushroomScreen parent) {
        this.parent = parent;
        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getDefaultBoardNinePatchDrawable());


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
                StorageResourceAmountPairNode<IdleMushroomGame> node = new StorageResourceAmountPairNode<>(parent.getGame(), resourceType);
                nodes.add(node);
                shownTypes.add(resourceType);
                Cell<StorageResourceAmountPairNode<IdleMushroomGame>> cell = this.add(node).width(NODE_WIDTH).height(NODE_HEIGHT);
                if ((i + 1) % NUM_NODE_PER_ROW == 0) {
                    cell.row();
                }
            }
        }

    }



    public void updateViewData(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        Set<String> unlockedResourceTypes = parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().getUnlockedResourceTypes();
        boolean needRebuildCells = !shownTypes.equals(unlockedResourceTypes);
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(unlockedResourceTypes);
            rebuildCells();
        }

        nodes.stream().forEach(node -> {
            long historySum;
            if (deltaHistoryMap.containsKey(node.getResourceType()))
            {
                historySum = deltaHistoryMap.get(node.getResourceType()).stream()
                        .collect(Utils.lastN(BaseIdleScreen.LOGIC_FRAME_PER_SECOND))
                        .stream()
                        .mapToLong(it -> it)
                        .sum()
                        ;
            }
            else
            {
                historySum = 0;
            }

            long amount = parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().getResourceNumOrZero(node.getResourceType());
            node.update(historySum, amount);
        });
    }


    @Override
    public void onResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        updateViewData(changeMap, deltaHistoryMap);
    }
}
