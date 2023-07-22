package hundun.gdxgame.idledemo.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoProficiencyComponent;

public class DemoSimpleProficiencyComponent extends BaseAutoProficiencyComponent {

    public static interface ProficiencySpeedCalculator {
        int invoke(BaseIdleDemoConstruction thiz);
    }
    public ProficiencySpeedCalculator proficiencySpeedCalculator;


    public DemoSimpleProficiencyComponent(BaseConstruction construction, Integer second) {
        super(construction, second, 50);
    }

    @Override
    public void onSubLogicFrame()
    {
        super.onSubLogicFrame();
        checkAutoPromoteDemote();
    }

    @Override
    protected void tryProficiencyOnce() {
        if (this.proficiencySpeedCalculator != null)
        {
            this.changeProficiency(proficiencySpeedCalculator.invoke((BaseIdleDemoConstruction)construction));
        }
    }
}
