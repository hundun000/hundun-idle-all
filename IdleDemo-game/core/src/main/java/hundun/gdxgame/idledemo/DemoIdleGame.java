package hundun.gdxgame.idledemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.*;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idledemo.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.Getter;


public class DemoIdleGame extends BaseIdleGame<RootSaveData> {

    @Getter
    protected AbstractIdleScreenContext<DemoIdleGame, RootSaveData> screenContext;

    public DemoIdleGame(ISaveTool<RootSaveData> saveTool) {
        super(960, 640);
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
                new DemoBuiltinConstructionsLoader(),
                new DemoAchievementLoader(),
                BaseIdleScreen.LOGIC_FRAME_PER_SECOND,
                childGameConfig
                );
        this.getSaveHandler().registerSubHandler(idleGameplayExport);
        saveHandler.systemSettingLoadOrStarter();
    }

    @Override
    protected void createStage2() {
        super.createStage2();
        screenContext.lazyInit();
    }

    @Override
    protected void createStage3() {
        super.createStage3();
        
        
        
        screenManager.pushScreen(DemoScreenId.SCREEN_MENU, null);
        getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_MENU);
    }

    
}
