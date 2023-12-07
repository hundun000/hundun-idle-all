package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class IdleMushroomGameAreaControlBoard extends Table implements IGameAreaChangeListener {

    BaseIdleMushroomScreen parent;
    Map<String, Node> nodes = new LinkedHashMap<>();


    public IdleMushroomGameAreaControlBoard(BaseIdleMushroomScreen parent) {
        super();
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        this.padRight(0);
        
    }



    @Override
    public void onGameAreaChange(String last, String current) {
        rebuildChild(current);
    }

    private void rebuildChild(String current) {

        nodes.entrySet().forEach(entry -> {
            if (Objects.equals(entry.getKey(), current)) {
                entry.getValue().changeVersion(true);
            } else {
                entry.getValue().changeVersion(false);
            }

        });

    }

    public void lazyInit(List<String> controlBoardScreenIds) {

        nodes.clear();
        this.clear();

        for (String controlBoardScreenId : controlBoardScreenIds) {

            Node node = new Node(parent, controlBoardScreenId, false);
            nodes.put(controlBoardScreenId, node);
            this.add(node)
                    .width(parent.getLayoutConst().AREA_BOARD_BORDER_WIDTH)
                    .height(parent.getLayoutConst().AREA_BOARD_CELL_HEIGHT)
                    .row();
        }

        rebuildChild(null);
        
    }

    public static class Node extends Table {

        BaseIdleMushroomScreen parent;
        //Image image;
        //Label label;

        String gotoScreenId;

        public Node(BaseIdleMushroomScreen parent, String gotoScreenId, boolean longVersion) {
            this.parent = parent;
            this.gotoScreenId = gotoScreenId;
            this.setTouchable(Touchable.enabled);
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    parent.getGame().setLastScreenId(parent.getScreenId());
                    parent.getGame().getScreenManager().pushScreen(gotoScreenId, null);
                    parent.getGame().getAudioPlayManager().intoScreen(gotoScreenId);
                }
            });
/*            this.label = new Label(gotoScreenId, parent.getGame().getMainSkin());
            this.add(label).right();*/
        }


        public void changeVersion(boolean longVersion) {
            this.setBackground(new TextureRegionDrawable(
                    parent.getGame().getTextureManager().getGameAreaTexture(gotoScreenId, longVersion)
            ));
/*            label.setVisible(longVersion);*/
        }

    }
}
