package hundun.gdxgame.idleshare.gamelib.framework.model.achievement;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/12
 */
@Data
@NoArgsConstructor
public abstract class AbstractAchievement {
    protected IdleGameplayContext gameplayContext;
    protected String id;
    protected String name;
    protected String description;
    protected String congratulationText;
    protected List<ResourcePair> awardResourceMap;
    protected String nextAchievementId;

    public abstract boolean checkComplete();

    public AbstractAchievement(
            String id,
            String name,
            String description,
            String congratulationText,
            List<ResourcePair> awardResourceMap,
            String nextAchievementId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.congratulationText = congratulationText;
        this.awardResourceMap = awardResourceMap;
        this.nextAchievementId = nextAchievementId;
    }

    public void lazyInitDescription(IdleGameplayContext gameplayContext)
    {
        this.gameplayContext = gameplayContext;
    }
}
