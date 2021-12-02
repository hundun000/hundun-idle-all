package hundun.gdxgame.idleframe.listener;


/**
 * @author hundun
 * Created on 2021/11/11
 */
public interface IAmountChangeEventListener {
    default void onBuffChange(boolean fromLoad) {};
    default void onResourceChange(boolean fromLoad) {};
}
