package hundun.gdxgame.idleshare.gamelib.framework.listener;

import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEvent;

/**
 * @author hundun
 * Created on 2022/02/08
 */
public interface IResourceChangeListener {
    void onResourceChange(OneSecondResourceChangeEvent event);
}
