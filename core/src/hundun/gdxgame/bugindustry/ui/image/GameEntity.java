package hundun.gdxgame.bugindustry.ui.image;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/16
 */
@Data
public class GameEntity {
    Texture texture;
    float x;
    float y;
    int drawWidth;
    int drawHeight;
    boolean randomMove;
    int randomMoveCount;
    float speedX;
    float speedY;
}
