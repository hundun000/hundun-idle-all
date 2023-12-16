package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomBuffId;
import hundun.gdxgame.idleshare.core.framework.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffAndStatus;
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


    public static class Node extends Table {
        BaseIdleMushroomScreen parent;

        @Getter
        BuffAndStatus model;

        Image image;
        Label nameLabel;
        Label amountLabel;
        Label deltaLabel;

        public Node(BaseIdleMushroomScreen parent, BuffAndStatus model) {
            super();
            this.parent = parent;
            this.model = model;
            TextureRegion textureRegion = parent.getGame().getTextureManager().getBuffIcon(model.getBuffPrototype().getId());
            this.image = new Image(textureRegion);
            this.add(image);
            this.nameLabel = new Label(model.getBuffPrototype().getName(), parent.getGame().getMainSkin());
            this.add(nameLabel).padRight(10);
            this.amountLabel = new Label("", parent.getGame().getMainSkin());
            this.add(amountLabel);
            this.deltaLabel = new Label("", parent.getGame().getMainSkin());
            this.add(deltaLabel).padRight(10);

            Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getTextureManager().getQuestionMarkTexture()));
            questionMarkArea.setBackground(parent.getGame().getTextureManager().getQuestionMarkTableDrawable());
            questionMarkArea.setTouchable(Touchable.enabled);
            questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener<>(() -> model, this.parent));
            this.add(questionMarkArea).size(parent.getGame().getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize, parent.getGame().getIdleMushroomPlayScreenLayoutConst().questionMarkAreaSize);
        }

        public void update(long delta, long amount) {
            amountLabel.setText(JavaFeatureForGwt.stringFormat(
                    model.getBuffPrototype().getLevelPart(),
                    parent.getGame().getTextFormatTool().format(amount)
            ));
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
            String buffId = buffIds.get(i);
            if (shownTypes.contains(buffId)) {
                BuffAndStatus buffAndStatus = parent.getGame().getIdleGameplayExport().getGameplayContext().getBuffManager().getBuffAndStatus(buffId);
                Node node = new Node(parent, buffAndStatus);
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
            long amount = node.getModel().getSaveData().getBuffLevel();
            node.update(historySum, amount);
        });
    }

}
