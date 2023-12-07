package hundun.gdxgame.idledemo;


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
