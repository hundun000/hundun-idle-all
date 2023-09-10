package hundun.gdxgame.idleshare.gamelib.framework.model.achievement;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAchievement {
    protected IdleGameplayContext gameplayContext;
    protected String id;
    protected String name;
    protected String description;
    protected String congratulationText;
    protected Map<String, Long> awardResourceMap;

    public abstract boolean checkUnlock();

    public AbstractAchievement(String id, String name, String description, String congratulationText, Map<String, Long> awardResourceMap) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.congratulationText = congratulationText;
        this.awardResourceMap = awardResourceMap;
    }

    public void lazyInitDescription(IdleGameplayContext gameplayContext)
    {
        this.gameplayContext = gameplayContext;
    }
}
