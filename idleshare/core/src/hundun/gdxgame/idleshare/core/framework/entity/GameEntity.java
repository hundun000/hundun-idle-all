package hundun.gdxgame.idleshare.core.framework.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
    boolean moveable;
    boolean hiden;
    float speedX;
    float speedY;
    boolean textureFlipX;

    public void frameLogic() {}
    public boolean checkRemove() {
        return false;
    }

}
