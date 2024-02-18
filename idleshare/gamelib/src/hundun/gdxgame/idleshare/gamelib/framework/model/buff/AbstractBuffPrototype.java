package hundun.gdxgame.idleshare.gamelib.framework.model.buff;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.BuffManager.BuffSaveData;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2021/11/12
 */
@Data
@NoArgsConstructor
public abstract class AbstractBuffPrototype {
    protected IdleGameplayContext gameplayContext;
    protected String id;
    protected String name;
    protected String wikiText;
    protected String levelPart;

    public AbstractBuffPrototype(
            String id,
            String name,
            String wikiText,
            String levelPart
    ) {
        this.id = id;
        this.name = name;
        this.wikiText = wikiText;
        this.levelPart = levelPart;
    }

    public void lazyInitDescription(IdleGameplayContext gameplayContext)
    {
        this.gameplayContext = gameplayContext;
    }

    public long modifyOutputGain(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        // default do nothing
        return lastPhaseAmount;
    }

    public long modifyOutputCost(BuffSaveData saveData, String constructionPrototypeId, String resourceType, long lastPhaseAmount) {
        // default do nothing
        return lastPhaseAmount;
    }
}
