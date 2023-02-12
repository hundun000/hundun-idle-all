package hundun.gdxgame.idledemo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

import hundun.gdxgame.idledemo.logic.GameDictionary;
import hundun.gdxgame.idledemo.logic.DemoTextureManager;
import hundun.gdxgame.corelib.base.save.ISaveTool;
import hundun.gdxgame.corelib.starter.StarterSaveHandler.ISubGameplaySaveHandler;
import hundun.gdxgame.idledemo.logic.DemoSaveHandler;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.screen.DemoMenuScreen;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.framework.model.ManagerContext;
import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.framework.model.manager.EventManager;
import hundun.gdxgame.idleshare.framework.util.text.TextFormatTool;


public class DemoIdleGame extends BaseIdleGame<RootSaveData> implements ISubGameplaySaveHandler<GameplaySaveData>{


    
    
    
    public DemoIdleGame(ISaveTool<RootSaveData> saveTool) {
        super(640, 480);
        this.debugMode = true;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new DemoSaveHandler(saveTool);
        this.mainSkinFilePath = "skins/default/skin/uiskin.json";
        this.gameDictionary = new GameDictionary();
        this.textureManager = new DemoTextureManager();
        this.screenContext = new DemoScreenContext(this);
        this.managerContext = new ManagerContext<>(this);
        this.eventManager = new EventManager();
        this.audioPlayManager = new AudioPlayManager(this);
        
    }
    
    @Override
    protected ChildGameConfig getChildGameConfig() {
        return new DemoChildGameConfig(this);
    }

    @Override
    public List<String> getGameAreaValues() {
        return GameArea.values;
    }
    
    @Override
    protected void createStage3() {
        super.createStage3();
        
        this.getSaveHandler().registerSubHandler(this);
        
        screenManager.pushScreen(DemoMenuScreen.class.getSimpleName(), null);
        getAudioPlayManager().intoScreen(DemoMenuScreen.class.getSimpleName());
    }

    @Override
    public void applyGameSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = managerContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            if (gameplaySaveData.getConstructionSaveDataMap().containsKey(construction.id)) {
                construction.setSaveData(gameplaySaveData.getConstructionSaveDataMap().get(construction.id));
                construction.updateModifiedValues();
            }
        }
        managerContext.getStorageManager().setUnlockedResourceTypes(gameplaySaveData.getUnlockedResourceTypes());
        managerContext.getStorageManager().setOwnResoueces(gameplaySaveData.getOwnResoueces());
        managerContext.getAchievementManager().setUnlockedAchievementNames(gameplaySaveData.getUnlockedAchievementNames());
    }

    @Override
    public void currentSituationToSaveData(GameplaySaveData gameplaySaveData) {
        Collection<BaseConstruction> constructions = managerContext.getConstructionFactory().getConstructions();
        gameplaySaveData.setConstructionSaveDataMap(constructions.stream()
                .collect(Collectors.toMap(
                        it -> it.getId(), 
                        it -> it.getSaveData()
                        ))
                );
        gameplaySaveData.setUnlockedResourceTypes(managerContext.getStorageManager().getUnlockedResourceTypes());
        gameplaySaveData.setOwnResoueces(managerContext.getStorageManager().getOwnResoueces());
        gameplaySaveData.setUnlockedAchievementNames(managerContext.getAchievementManager().getUnlockedAchievementNames());
    }
    
}
