package hundun.gdxgame.idleshare.gamelib.framework.listener;

import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneFrameResourceChangeEvent;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/02/08
 */
public interface IOneFrameResourceChangeListener {
    void onResourceChange(OneFrameResourceChangeEvent event);
}
