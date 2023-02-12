package hundun.gdxgame.idleshare.framework;

import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.utils.viewport.Viewport;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.model.ManagerContext;
import hundun.gdxgame.idleshare.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.framework.model.manager.AbstractTextureManager;
import hundun.gdxgame.idleshare.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.framework.model.manager.EventManager;
import hundun.gdxgame.idleshare.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.framework.util.text.TextFormatTool;
import lombok.Getter;


public abstract class BaseIdleGame<T_SAVE> extends BaseHundunGame<T_SAVE> {

    @Getter
    protected AbstractIdleScreenContext<? extends BaseIdleGame<T_SAVE>, T_SAVE> screenContext;
    @Getter
    protected ManagerContext<? extends BaseIdleGame<T_SAVE>, T_SAVE> managerContext;
    @Getter
    protected AudioPlayManager audioPlayManager;
    @Getter
    protected IGameDictionary gameDictionary;
    @Getter
    protected EventManager eventManager;
    @Getter
    protected AbstractTextureManager textureManager;
    @Getter
    protected TextFormatTool textFormatTool;

    @Getter
    protected Viewport sharedViewport;
    
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
	    ChildGameConfig childGameConfig = getChildGameConfig();


        managerContext.lazyInitOnGameCreate(childGameConfig);
        audioPlayManager.lazyInit(childGameConfig.getScreenIdToFilePathMap());
        
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

    protected abstract ChildGameConfig getChildGameConfig();

    @Override
    public void dispose() {
        super.dispose();
        
        saveHandler.gameSaveCurrent();
    }
    
}
