package hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.OutputComponent;

public abstract class BaseAutoOutputComponent extends OutputComponent {

    protected int autoOutputProgress = 0;

    public BaseAutoOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public void onSubLogicFrame() {
        autoOutputProgress++;
        int outputFrameCountMax = this.autoOutputSecondCountMax * construction.getGameplayContext().getIdleFrontend().getLogicFramePerSecond();
        if (autoOutputProgress >= outputFrameCountMax)
        {
            autoOutputProgress = 0;
            tryAutoOutputOnce();
        }
    }

    private void tryAutoOutputOnce()
    {
        if (!this.canOutput())
        {
            //gameContext.frontend.log(this.id, "canOutput");
            return;
        }
        //gameContext.frontend.log(this.id, "AutoOutput");
        this.doOutput();
    }
}
