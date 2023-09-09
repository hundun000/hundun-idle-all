package hundun.gdxgame.idleshare.core.framework;

import java.util.List;

import com.badlogic.gdx.utils.viewport.Viewport;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractTextureManager;
import hundun.gdxgame.idleshare.core.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.Getter;


public abstract class BaseIdleGame<T_SAVE> extends BaseHundunGame<T_SAVE> {

    @Getter
    protected AbstractIdleScreenContext<? extends BaseIdleGame<T_SAVE>, T_SAVE> screenContext;
    @Getter
    protected AudioPlayManager audioPlayManager;
    
    @Getter
    protected AbstractTextureManager textureManager;
    @Getter
    protected TextFormatTool textFormatTool;

    @Getter
    protected Viewport sharedViewport;
    
    @Getter
    protected IdleGameplayExport idleGameplayExport;
    @Getter
    protected ChildGameConfig childGameConfig;
    
    public BaseIdleGame(int viewportWidth, int viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

	
	@Override
	protected void createStage2() {
	    textureManager.lazyInitOnGameCreateStage2();
	    screenContext.lazyInit();
	}
	
	
	@Override
	protected void createStage3() {

	    
        audioPlayManager.lazyInitOnGameCreate(childGameConfig.getScreenIdToFilePathMap());
        
	}

    public abstract List<String> getGameAreaValues();

//    /**
//     * 作为新存档，也需要修改ModelContext
//     */
//    public void newSaveStarter(ManagerContext modelContext) {
//        Collection<BaseConstruction> constructions = modelContext.getConstructionFactory().getConstructions();
//        for (BaseConstruction construction : constructions) {
//            construction.getSaveData().setLevel(starterData.getConstructionStarterLevelMap().getOrDefault(construction.getId(), 0));
//            if (starterData.getConstructionStarterWorkingLevelMap().getOrDefault(construction.getId(), false)) {
//                construction.getSaveData().setWorkingLevel(starterData.getConstructionStarterLevelMap().getOrDefault(construction.getId(), 0));
//            }
//            construction.updateModifiedValues();
//        }
//    }

    @Override
    public void dispose() {
        super.dispose();
        
        saveHandler.gameSaveCurrent();
    }
    
}
