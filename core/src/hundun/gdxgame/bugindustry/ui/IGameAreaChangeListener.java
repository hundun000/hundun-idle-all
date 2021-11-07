package hundun.gdxgame.bugindustry.ui;

import hundun.gdxgame.bugindustry.model.GameArea;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public interface IGameAreaChangeListener {
    void onChange(GameArea last, GameArea current);
}
