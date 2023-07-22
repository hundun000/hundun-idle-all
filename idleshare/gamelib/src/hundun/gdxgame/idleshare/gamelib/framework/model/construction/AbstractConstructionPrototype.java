package hundun.gdxgame.idleshare.gamelib.framework.model.construction;

import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;

@Getter
public abstract class AbstractConstructionPrototype {

    protected String prototypeId;
    protected Language language;
    protected ResourcePack buyInstanceCostPack;

    protected DescriptionPackage descriptionPackage;

    public AbstractConstructionPrototype(String prototypeId,
                                            Language language,
                                            ResourcePack buyInstanceCostPack
    )
    {
        this.prototypeId = prototypeId;
        this.language = language;
        this.buyInstanceCostPack = buyInstanceCostPack;

        if (buyInstanceCostPack != null)
        {
            buyInstanceCostPack.setModifiedValues(buyInstanceCostPack.getBaseValues());
            buyInstanceCostPack.setModifiedValuesDescription(ResourcePack.toDescription(buyInstanceCostPack.getModifiedValues()));
        }
    }

    public void lazyInitDescription(IdleGameplayContext gameContext, Language language) {
        if (buyInstanceCostPack != null)
        {
            buyInstanceCostPack.setDescriptionStart(gameContext.getGameDictionary().getPlayScreenTexts(language).get(1));
        }
    }

    public abstract BaseConstruction getInstance(GridPosition position);
}
