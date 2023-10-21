package hundun.gdxgame.idleshare.core.starter.ui.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table implements IGameAreaChangeListener {

    BaseIdleScreen<T_GAME, T_SAVE> parent;
    Map<String, GameAreaControlNode<T_GAME, T_SAVE>> nodes = new LinkedHashMap<>();


    public GameAreaControlBoard(BaseIdleScreen<T_GAME, T_SAVE> parent) {
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

            GameAreaControlNode<T_GAME, T_SAVE> node = new GameAreaControlNode<>(parent, controlBoardScreenId, false);
            nodes.put(controlBoardScreenId, node);
            this.add(node)
                    .width(parent.getLayoutConst().AREA_BOARD_BORDER_WIDTH)
                    .height(parent.getLayoutConst().AREA_BOARD_CELL_HEIGHT)
                    .row();
        }

        rebuildChild(null);
        if (parent.getGame().debugMode) {
            this.debugAll();
        }
        
    }


}
