package hundun.gdxgame.idledemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.logic.*;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;
import hundun.gdxgame.idleshare.core.framework.StarterIdleFrontend;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.buff.IBuffPrototypeLoader;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class IdleDemoGame extends BaseHundunGame<RootSaveData> {
    public static final int LOGIC_FRAME_PER_SECOND = 30;
    @Getter
    protected AudioPlayManager audioPlayManager;
    @Getter
    protected DemoGameDictionary demoGameDictionary;
    @Getter
    protected DemoTextureManager textureManager;
    @Getter
    protected TextFormatTool textFormatTool;

    @Getter
    protected Viewport sharedViewport;

    @Getter
    protected IdleGameplayExport idleGameplayExport;
    @Getter
    protected ChildGameConfig childGameConfig;
    @Setter
    @Getter
    protected String lastScreenId;
    @Getter
    protected
    List<String> controlBoardScreenIds;
    @Getter
    protected DemoScreenContext screenContext;


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



    public IdleDemoGame(ISaveTool<RootSaveData> saveTool) {
        super(960, 640, LOGIC_FRAME_PER_SECOND);
        this.debugMode = true;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new DemoSaveHandler(frontend, saveTool);
        this.mainSkinFilePath = null;
        this.textureManager = new DemoTextureManager();
        this.screenContext = new DemoScreenContext(this);
        this.audioPlayManager = new AudioPlayManager(this);
        this.childGameConfig = new DemoChildGameConfig();

        this.controlBoardScreenIds = JavaFeatureForGwt.listOf(
                DemoScreenId.SCREEN_COOKIE,
                DemoScreenId.SCREEN_WORLD,
                DemoScreenId.SCREEN_ACHIEVEMENT
        );
        this.demoGameDictionary = new DemoGameDictionary();
    }


    
    @Override
    protected void createStage1() {
        super.createStage1();
        this.mainSkin = new FreeTypeSkin(Gdx.files.internal("skins/DefaultSkinWithFreeType/DefaultSkinWithFreeType.json"));
        this.idleGameplayExport = new IdleGameplayExport(
                frontend,
                new StarterIdleFrontend(this),
                new DemoBuiltinConstructionsLoader(),
                new DemoAchievementLoader(),
                IBuffPrototypeLoader.emptyImpl(),
                childGameConfig
                );
        this.getSaveHandler().registerSubHandler(idleGameplayExport);
        saveHandler.systemSettingLoadOrStarter();
    }

    @Override
    protected void createStage2() {
        textureManager.lazyInitOnGameCreateStage2();
        screenContext.lazyInit();
    }

    @Override
    protected void createStage3() {
        audioPlayManager.lazyInitOnGameCreate(childGameConfig.getScreenIdToFilePathMap());
        
        screenManager.pushScreen(DemoScreenId.SCREEN_MENU, null);
        getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_MENU);
    }

    @Override
    protected void onLogicFrame(ILogicFrameListener source) {
        source.onLogicFrame();
        idleGameplayExport.onLogicFrame();
        if (this.getLogicFrameHelper().getClockCount() % this.getLogicFrameHelper().secondToFrameNum(10) == 0)
        {
            this.getSaveHandler().gameSaveCurrent();
        }
    }
}
