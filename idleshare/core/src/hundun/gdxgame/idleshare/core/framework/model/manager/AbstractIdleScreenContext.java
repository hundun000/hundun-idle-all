package hundun.gdxgame.idleshare.core.framework.model.manager;

import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public abstract class AbstractIdleScreenContext<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> {

    protected T_GAME game;

    public AbstractIdleScreenContext(T_GAME game) {
        this.game = game;
    }
    
    public abstract void lazyInit();

}
