package hundun.gdxgame.idledemo.ui.shared;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard extends Table implements IGameAreaChangeListener {

    BaseIdleDemoScreen parent;
    Map<String, GameAreaControlNode> nodes = new LinkedHashMap<>();


    public GameAreaControlBoard(BaseIdleDemoScreen parent) {
        super();
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        
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

            GameAreaControlNode node = new GameAreaControlNode(parent, controlBoardScreenId, false);
            nodes.put(controlBoardScreenId, node);
            this.add(node)
                    .width(parent.getLayoutConst().AREA_BOARD_BORDER_WIDTH)
                    .height(parent.getLayoutConst().AREA_BOARD_CELL_HEIGHT)
                    .row();
        }

        rebuildChild(null);
        
    }


}
