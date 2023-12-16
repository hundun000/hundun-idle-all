package hundun.gdxgame.idledemo.ui.shared;

import hundun.gdxgame.idledemo.ui.main.GameEntityFactory.IdleDemoGameEntityFactoryLayoutConst;

/**
 * @author hundun
 * Created on 2022/01/22
 */
public class PlayScreenLayoutConst {

    public int CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT = 200;
    public int CONSTRUCTION_BUTTON_AREA_WIDTH = 400;

    public int STORAGE_BOARD_BORDER_HEIGHT = 60;
    public int AREA_BOARD_BORDER_WIDTH = 100;
    public int AREA_BOARD_CELL_HEIGHT = 50;
//    public static final int STORAGE_BOARD_CONTAIN_WIDTH = 100;
//    public static final int STORAGE_BOARD_CONTAIN_HEIGHT = 50;

    public IdleDemoGameEntityFactoryLayoutConst gameEntityFactoryLayoutConst;

    public int RESOURCE_AMOUNT_PAIR_NODE_HEIGHT = 25;
    public int RESOURCE_AMOUNT_PAIR_NODE_WIDTH = 70;

    public int ALL_ACHIEVEMENT_BOARD_SCROLL_BORDER_OFFSET = 40;
    public int ALL_ACHIEVEMENT_BOARD_NODE_WIDTH = 400;
    public int ALL_ACHIEVEMENT_BOARD_NODE_HEIGHT = 150;

    public PlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
        this.gameEntityFactoryLayoutConst = IdleDemoGameEntityFactoryLayoutConst.builder()
                .EXPECTED_DRAW_MIN_X(0)
                .EXPECTED_DRAW_MAX_X(gameLogicWidth)
                .EXPECTED_DRAW_MIN_Y(CONSTRUCTION_BOARD_ROOT_BOX_HEIGHT)
                .EXPECTED_DRAW_MAX_Y(gameLogicHeight - STORAGE_BOARD_BORDER_HEIGHT)
                .build();
    }
}
