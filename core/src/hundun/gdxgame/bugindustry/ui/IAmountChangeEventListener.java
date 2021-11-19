package hundun.gdxgame.bugindustry.ui;

import hundun.gdxgame.bugindustry.model.GameArea;

/**
 * @author hundun
 * Created on 2021/11/11
 */
public interface IAmountChangeEventListener {
    default void onBuffChange(boolean fromLoad) {};
    default void onResourceChange(boolean fromLoad) {};
}
