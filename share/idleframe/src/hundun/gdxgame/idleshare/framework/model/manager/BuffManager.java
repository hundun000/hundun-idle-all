package hundun.gdxgame.idleshare.framework.model.manager;

import java.util.HashMap;
import java.util.Map;

import hundun.gdxgame.idleshare.framework.BaseIdleGame;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {

    private BaseIdleGame game;

    private Map<String, Integer> buffAmounts = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Integer> getBuffAmounts() {
        return buffAmounts;
    }
    public void setBuffAmounts(Map<String, Integer> buffAmounts) {
        this.buffAmounts = buffAmounts;
    }

    public BuffManager(BaseIdleGame game) {
        this.game = game;
    }



    public int getBuffAmoutOrDefault(String id) {
        return buffAmounts.getOrDefault(id, 0);
    }

    public void addBuffAmout(String id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        game.getEventManager().notifyBuffChange();
    }


    public int modifyResourceGain(String resourceType, int oldAmout) {
        int newAmout = oldAmout;
        // TODO
        return newAmout;
    }

}
