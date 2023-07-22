package hundun.gdxgame.idleshare.gamelib.framework.listener;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/02/08
 */
public interface IOneFrameResourceChangeListener {
    void onResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap);
}
