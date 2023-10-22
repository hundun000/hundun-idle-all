package hundun.gdxgame.idleshare.core.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import hundun.gdxgame.corelib.base.util.DrawableFactory;

/**
 * @author hundun
 * Created on 2022/01/22
 */
public class PlayScreenLayoutConst {
    public static final int EPOCH_PART_CHILD_WIDTH = 200;
    public static final int SELLER_PART_CHILD_WIDTH = 200;
    public final float DESK_WIDTH = 100;
    public final float DESK_HEIGHT = 100;
    public int CONSTRUCION_BOARD_ROOT_BOX_HEIGHT = 200;
    public int CONSTRUCION_CHILD_WIDTH = 100;
    public int CONSTRUCION_CHILD_BUTTON_HEIGHT = 30;
    public int CONSTRUCION_CHILD_NAME_HEIGHT = 50;

    public int STORAGE_BOARD_BORDER_HEIGHT = 60;
    public int AREA_BOARD_BORDER_WIDTH = 100;
    public int AREA_BOARD_CELL_HEIGHT = 50;
//    public static final int STORAGE_BOARD_CONTAIN_WIDTH = 100;
//    public static final int STORAGE_BOARD_CONTAIN_HEIGHT = 50;
    public Drawable simpleBoardBackground;
    public Drawable simpleBoardBackgroundMiddle;

    public int EXPECTED_DRAW_MIN_X;
    public int EXPECTED_DRAW_MAX_X;
    public int EXPECTED_DRAW_MIN_Y;
    public int EXPECTED_DRAW_MAX_Y;

    public int RESOURCE_AMOUNT_PAIR_NODE_HEIGHT = 25;
    public int RESOURCE_AMOUNT_PAIR_NODE_WIDTH = 70;
    public int FIRST_LOCKED_ACHIEVEMENT_BOARD_WIDTH = 200;
    //public int FIRST_LOCKED_ACHIEVEMENT_BOARD_HEIGHT = 150;

    public int ALL_ACHIEVEMENT_BOARD_SCROLL_BORDER_OFFSET = 40;
    public int ALL_ACHIEVEMENT_BOARD_NODE_WIDTH = 400;
    public int ALL_ACHIEVEMENT_BOARD_NODE_HEIGHT = 150;
    public int WorldConstructionCellDetailNodeWidth = 800;
    public int WorldConstructionCellDetailNodeHeight = CONSTRUCION_BOARD_ROOT_BOX_HEIGHT - 20;
    public float popupInfoBoardWidth = 400;
    public float popupInfoBoardHeight = 200;
    public float WorldConstructionCellTablePad = 20;
    public float questionMarkAreaSize = 50;

    public PlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
        this.EXPECTED_DRAW_MIN_X = 0;
        this.EXPECTED_DRAW_MAX_X = gameLogicWidth;
        this.EXPECTED_DRAW_MIN_Y = CONSTRUCION_BOARD_ROOT_BOX_HEIGHT;
        this.EXPECTED_DRAW_MAX_Y = gameLogicHeight - STORAGE_BOARD_BORDER_HEIGHT;
        Gdx.app.log(this.getClass().getSimpleName(), "EXPECTED_DRAW_MAX_X = " + EXPECTED_DRAW_MAX_X + ", EXPECTED_DRAW_MAX_Y = " + EXPECTED_DRAW_MAX_Y);

        simpleBoardBackground = DrawableFactory.createBorderBoard(10, 10, 0.8f, 1);
        simpleBoardBackgroundMiddle = DrawableFactory.createBorderBoard(10, 10, 0.7f, 1);
    }
}
