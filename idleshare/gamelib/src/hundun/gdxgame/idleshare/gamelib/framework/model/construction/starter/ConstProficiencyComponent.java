package hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.ProficiencyComponent;

public class ConstProficiencyComponent extends ProficiencyComponent {

    public ConstProficiencyComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public void onSubLogicFrame() {
        // do nothing
    }

    @Override
    public void afterUpgrade() {
        // do nothing
    }

    @Override
    public boolean isEnoughProficiencyForUpgrade() {
        return true;
    }
}
