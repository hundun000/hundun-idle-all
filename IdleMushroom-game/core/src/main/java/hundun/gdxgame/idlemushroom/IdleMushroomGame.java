package hundun.gdxgame.idlemushroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomScreenId;
import hundun.gdxgame.idlemushroom.logic.loader.IdleMushroomBuffPrototypeLoader;
import hundun.gdxgame.idlemushroom.logic.loader.IdleMushroomConstructionsLoader;
import hundun.gdxgame.idlemushroom.logic.loader.IdleMushroomAchievementLoader;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext;
import hundun.gdxgame.idlemushroom.logic.*;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.*;

import java.util.List;
import java.util.Map;


public class IdleMushroomGame extends BaseHundunGame<RootSaveData> {

    @Getter
    protected IdleMushroomAudioPlayManager audioPlayManager;

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
    IdleMushroomPlayScreenLayoutConst idleMushroomPlayScreenLayoutConst;
    @Getter
    private final IdleMushroomTextureManager textureManager;
    @Getter
    private final IdleMushroomScreenContext screenContext;
    @Getter
    private final IdleMushroomGameDictionary idleMushroomGameDictionary;
    @Getter
    private final IdleMushroomExtraGameplayExport idleMushroomExtraGameplayExport;


    public IdleMushroomGame(ISaveTool<RootSaveData> saveTool) {
        super(960, 640);
        this.debugMode = false;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new IdleMushroomSaveHandler(frontend, saveTool);
        this.mainSkinFilePath = null;
        this.textureManager = new IdleMushroomTextureManager();
        this.screenContext = new IdleMushroomScreenContext(this);
        this.audioPlayManager = new IdleMushroomAudioPlayManager(this);
        this.childGameConfig = new IdleMushroomChildGameConfig();
        this.idleMushroomGameDictionary = new IdleMushroomGameDictionary();
        this.controlBoardScreenIds = JavaFeatureForGwt.listOf(
                IdleMushroomScreenId.SCREEN_MAIN,
                IdleMushroomScreenId.SCREEN_WORLD,
                IdleMushroomScreenId.SCREEN_ACHIEVEMENT
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
                new IdleMushroomConstructionsLoader(),
                new IdleMushroomAchievementLoader(idleMushroomGameDictionary),
                new IdleMushroomBuffPrototypeLoader(),
                BaseIdleMushroomScreen.LOGIC_FRAME_PER_SECOND,
                childGameConfig
                );
        this.getSaveHandler().registerSubHandler(idleGameplayExport);
        saveHandler.systemSettingLoadOrStarter();
    }

    @Override
    protected void createStage2() {
        textureManager.lazyInitOnGameCreateStage2();

        idleMushroomExtraGameplayExport.lazyInitStage2();
        screenContext.lazyInit();
    }

    @Override
    protected void createStage3() {
        audioPlayManager.lazyInitOnGameCreate(childGameConfig.getScreenIdToFilePathMap());
        
        screenManager.pushScreen(IdleMushroomScreenId.SCREEN_MENU, null);
        getAudioPlayManager().intoScreen(IdleMushroomScreenId.SCREEN_MENU);
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

    @Override
    public void dispose() {
        super.dispose();

        saveHandler.gameSaveCurrent();
    }
}
