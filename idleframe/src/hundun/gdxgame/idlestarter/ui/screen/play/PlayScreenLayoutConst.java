package hundun.gdxgame.idlestarter.ui.screen.play;

import com.badlogic.gdx.Gdx;

/**
 * @author hundun
 * Created on 2022/01/22
 */
public class PlayScreenLayoutConst {
    public int CONSTRUCION_BOARD_BORDER_HEIGHT = 120;
    
    public int STORAGE_BOARD_BORDER_HEIGHT = 60;
    public int AREA_BOARD_BORDER_WIDTH = 100;
    public int AREA_BOARD_CELL_HEIGHT = 50;
//    public static final int STORAGE_BOARD_CONTAIN_WIDTH = 100;
//    public static final int STORAGE_BOARD_CONTAIN_HEIGHT = 50;
    
    public int EXPECTED_DRAW_MIN_X;
    public int EXPECTED_DRAW_MAX_X;
    public int EXPECTED_DRAW_MIN_Y;
    public int EXPECTED_DRAW_MAX_Y;
    
    public PlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
        this.EXPECTED_DRAW_MIN_X = 0;
        this.EXPECTED_DRAW_MAX_X = gameLogicWidth;
        this.EXPECTED_DRAW_MIN_Y = CONSTRUCION_BOARD_BORDER_HEIGHT;
        this.EXPECTED_DRAW_MAX_Y = gameLogicHeight - STORAGE_BOARD_BORDER_HEIGHT;
        Gdx.app.log(this.getClass().getSimpleName(), "EXPECTED_DRAW_MAX_X = " + EXPECTED_DRAW_MAX_X + ", EXPECTED_DRAW_MAX_Y = " + EXPECTED_DRAW_MAX_Y);
    }
}
