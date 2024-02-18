package hundun.gdxgame.idleshare.gamelib.framework.model.achievement;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/12
 */
@Data
@NoArgsConstructor
public abstract class AbstractAchievementPrototype {
    protected IdleGameplayContext gameplayContext;
    protected String id;
    protected String name;
    protected String description;
    protected String congratulationText;
    protected List<ResourcePair> awardResourceMap;
    protected List<String> nextAchievementIds;

    public abstract boolean checkComplete();

    public AbstractAchievementPrototype(
            String id,
            String name,
            String description,
            String congratulationText,
            List<ResourcePair> awardResourceMap,
            List<String> nextAchievementIds
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.congratulationText = congratulationText;
        this.awardResourceMap = awardResourceMap;
        this.nextAchievementIds = nextAchievementIds;
    }

    public void lazyInitDescription(IdleGameplayContext gameplayContext)
    {
        this.gameplayContext = gameplayContext;
    }
}
