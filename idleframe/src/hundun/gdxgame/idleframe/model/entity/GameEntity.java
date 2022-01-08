package hundun.gdxgame.idleframe.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/16
 */
@Getter
@Setter
public class GameEntity {
    Sprite texture;
    float x;
    float y;
    int drawWidth;
    int drawHeight;
    boolean randomMove;
    int randomMoveCount;
    float speedX;
    float speedY;
    boolean textureFlipX;
    
    public void checkRandomeMoveSpeedChange() {};
    
}
