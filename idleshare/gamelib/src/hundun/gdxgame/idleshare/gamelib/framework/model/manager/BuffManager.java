package hundun.gdxgame.idleshare.gamelib.framework.model.manager;

import java.util.HashMap;
import java.util.Map;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {

    private IdleGameplayContext gameContext;

    @Getter
    @Setter
    private Map<String, Integer> buffAmounts = new HashMap<>();


    public BuffManager(IdleGameplayContext gameContext) {
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
