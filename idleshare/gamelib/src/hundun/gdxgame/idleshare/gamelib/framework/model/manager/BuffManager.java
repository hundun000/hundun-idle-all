package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.HashMap;
import java.util.Map;

import hundun.gdxgame.idleshare.gamelib.context.IdleGamePlayContext;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {

    private IdleGamePlayContext gameContext;

    private Map<String, Integer> buffAmounts = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Integer> getBuffAmounts() {
        return buffAmounts;
    }
    public void setBuffAmounts(Map<String, Integer> buffAmounts) {
        this.buffAmounts = buffAmounts;
    }

    public BuffManager(IdleGamePlayContext gameContext) {
        this.gameContext = gameContext;
    }



    public int getBuffAmoutOrDefault(String id) {
        return buffAmounts.getOrDefault(id, 0);
    }

    public void addBuffAmout(String id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        gameContext.getEventManager().notifyBuffChange();
    }


    public int modifyResourceGain(String resourceType, int oldAmout) {
        int newAmout = oldAmout;
        // TODO
        return newAmout;
    }

}
