package hundun.gdxgame.bugindustry.ui.image;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/16
 */
@Data
public class GameEntity {
    float x;
    float y;
    boolean randomMove;
    int randomMoveCount;
    float speedX;
    float speedY;
}
