package hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.ProficiencyComponent;

public abstract class BaseAutoProficiencyComponent extends ProficiencyComponent {

    protected final Integer upgradeLostProficiency;
    protected int autoProficiencyProgress = 0;
    protected final Integer AUTO_PROFICIENCY_SECOND_MAX; // n秒生长一次

    public BaseAutoProficiencyComponent(
            BaseConstruction construction,
            Integer second,
            Integer upgradeLostProficiency,
            int maxProficiency
    ) {
        super(construction);
        this.maxProficiency = maxProficiency;
        this.AUTO_PROFICIENCY_SECOND_MAX = second;
        this.upgradeLostProficiency = upgradeLostProficiency;
    }

    @Override
    public void onSubLogicFrame() {
        if (AUTO_PROFICIENCY_SECOND_MAX != null)
        {
            autoProficiencyProgress++;
            int proficiencyFrameCountMax = AUTO_PROFICIENCY_SECOND_MAX * construction.getGameplayContext().getIdleFrontend().getLogicFramePerSecond();
            if (autoProficiencyProgress >= proficiencyFrameCountMax)
            {
                autoProficiencyProgress = 0;
                tryProficiencyOnce();
            }
        }
    }

    @Override
    public void afterUpgrade() {
        if (upgradeLostProficiency != null)
        {
            construction.getSaveData().setProficiency(construction.getSaveData().getProficiency() - this.upgradeLostProficiency);
        }
    }


    @Override
    public boolean isEnoughProficiencyForUpgrade() {
        if (upgradeLostProficiency != null) {
            return isMaxProficiency();
        } else {
            return true;
        }
    }

    protected abstract void tryProficiencyOnce();
}
