package hundun.gdxgame.idlemushroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext;
import hundun.gdxgame.idlemushroom.logic.*;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.core.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.*;

import java.util.Map;


public class IdleMushroomGame extends BaseIdleGame<RootSaveData> {
    @Getter
    IdleMushroomPlayScreenLayoutConst idleMushroomPlayScreenLayoutConst;
    @Getter
    protected AbstractIdleScreenContext<IdleMushroomGame, RootSaveData> screenContext;

    @Getter
    private final IdleMushroomTextureManager idleMushroomTextureManager;
    @Getter
    private final IdleMushroomScreenContext idleMushroomScreenContext;
    @Getter
    private final IdleMushroomGameDictionary idleMushroomGameDictionary;
    @Getter
    private final IdleMushroomExtraGameplayExport idleMushroomExtraGameplayExport;


    public IdleMushroomGame(ISaveTool<RootSaveData> saveTool) {
        super(960, 640);
        this.debugMode = false;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new DemoSaveHandler(frontend, saveTool);
        this.mainSkinFilePath = null;
        this.idleMushroomTextureManager = new IdleMushroomTextureManager();
        this.textureManager = this.idleMushroomTextureManager;
        this.idleMushroomScreenContext = new IdleMushroomScreenContext(this);
        this.screenContext = idleMushroomScreenContext;
        this.audioPlayManager = new AudioPlayManager(this);
        this.childGameConfig = new IdleMushroomChildGameConfig();
        this.idleMushroomGameDictionary = new IdleMushroomGameDictionary();
        this.controlBoardScreenIds = JavaFeatureForGwt.listOf(
                DemoScreenId.SCREEN_MAIN,
                DemoScreenId.SCREEN_WORLD,
                DemoScreenId.SCREEN_ACHIEVEMENT
        );
        this.idleMushroomExtraGameplayExport = new IdleMushroomExtraGameplayExport(this);
    }


    @Override
    protected void createStage1() {
        super.createStage1();
        this.mainSkin = new FreeTypeSkin(Gdx.files.internal("skins/IdleMushroom/IdleMushroom.json"));
        this.idleMushroomPlayScreenLayoutConst = new IdleMushroomPlayScreenLayoutConst(this.getWidth(), this.getHeight());
        this.idleGameplayExport = new IdleGameplayExport(
                frontend,
                idleMushroomGameDictionary,
                new DemoBuiltinConstructionsLoader(),
                new DemoAchievementLoader(idleMushroomGameDictionary),
                BaseIdleScreen.LOGIC_FRAME_PER_SECOND,
                childGameConfig
                );
        this.getSaveHandler().registerSubHandler(idleGameplayExport);
        saveHandler.systemSettingLoadOrStarter();
    }

    @Override
    protected void createStage2() {
        super.createStage2();
        idleMushroomExtraGameplayExport.lazyInitStage2();
        screenContext.lazyInit();
    }

    @Override
    protected void createStage3() {
        super.createStage3();
        
        
        
        screenManager.pushScreen(DemoScreenId.SCREEN_MENU, null);
        getAudioPlayManager().intoScreen(DemoScreenId.SCREEN_MENU);
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class RootEpochConfig {
        int enlargementLevel;
        int maxLevel;
        Map<String, ConstructionEpochConfig> constructionEpochConfigMap;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ConstructionEpochConfig {

        String transformToPrototypeId;
    }


}
