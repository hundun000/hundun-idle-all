package hundun.gdxgame.idledemo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.idledemo.logic.DemoGameDictionary;
import hundun.gdxgame.idledemo.logic.DemoTextureManager;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idledemo.logic.DemoBuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.DemoSaveHandler;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.screen.DemoMenuScreen;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.EventManager;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;


public class DemoIdleGame extends BaseIdleGame<RootSaveData> {


    
    
    
    public DemoIdleGame(ISaveTool<RootSaveData> saveTool) {
        super(640, 480);
        this.debugMode = true;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new DemoSaveHandler(frontend, saveTool);
        this.mainSkinFilePath = null;
        this.textureManager = new DemoTextureManager();
        this.screenContext = new DemoScreenContext(this);
        this.audioPlayManager = new AudioPlayManager(this);
        this.childGameConfig = new DemoChildGameConfig();
        
        
    }

    @Override
    public List<String> getGameAreaValues() {
        return GameArea.values;
    }
    
    @Override
    protected void createStage1() {
        super.createStage1();
        this.mainSkin = new FreeTypeSkin(Gdx.files.internal("skins/DefaultSkinWithFreeType/DefaultSkinWithFreeType.json"));
        this.idleGameplayExport = new IdleGameplayExport(
                frontend,
                new DemoGameDictionary(),
                new DemoBuiltinConstructionsLoader(),
                BaseIdlePlayScreen.LOGIC_FRAME_PER_SECOND,
                childGameConfig
                );
        this.getSaveHandler().registerSubHandler(idleGameplayExport);
        saveHandler.systemSettingLoadOrNew();
    }
    
    @Override
    protected void createStage3() {
        super.createStage3();
        
        
        
        screenManager.pushScreen(DemoMenuScreen.class.getSimpleName(), null);
        getAudioPlayManager().intoScreen(DemoMenuScreen.class.getSimpleName());
    }

    
}
