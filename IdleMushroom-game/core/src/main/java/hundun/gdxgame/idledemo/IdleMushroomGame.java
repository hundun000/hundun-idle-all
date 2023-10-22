package hundun.gdxgame.idledemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.FreeTypeSkin;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.logic.*;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.idledemo.ui.screen.DemoScreenContext;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.core.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.TextFormatTool;
import lombok.*;

import java.util.Map;


public class IdleMushroomGame extends BaseIdleGame<RootSaveData> {

    @Getter
    protected AbstractIdleScreenContext<IdleMushroomGame, RootSaveData> screenContext;

    @Getter
    protected IdleMushroomTextureManager idleMushroomTextureManager;

    public static Map<Integer, RootEpochConfig> epochConfigMap;
    static {
        epochConfigMap = JavaFeatureForGwt.mapOf(
                1, RootEpochConfig.builder()
                        .maxLevel(5)
                        .outputScale(1)
                        .constructionEpochConfigMap(JavaFeatureForGwt.mapOf(
                                DemoConstructionPrototypeId.EPOCH_1_MUSHROOM_AUTO_PROVIDER,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER)
                                        .build(),
                                DemoConstructionPrototypeId.EPOCH_1_EMPTY_CELL,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(DemoConstructionPrototypeId.EPOCH_2_EMPTY_CELL)
                                        .build(),
                                DemoConstructionPrototypeId.EPOCH_1_TREE,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(DemoConstructionPrototypeId.EPOCH_2_TREE)
                                        .build()
                        ))
                        .build(),
                2, RootEpochConfig.builder()
                        .maxLevel(10)
                        .outputScale(2)
                        .constructionEpochConfigMap(JavaFeatureForGwt.mapOf(
                                DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER)
                                        .build()
                        ))
                        .build(),
                3, RootEpochConfig.builder()
                        .maxLevel(15)
                        .outputScale(4)
                        .constructionEpochConfigMap(JavaFeatureForGwt.mapOf(
                                DemoConstructionPrototypeId.EPOCH_2_MUSHROOM_AUTO_PROVIDER,
                                ConstructionEpochConfig.builder()
                                        .transformToPrototypeId(DemoConstructionPrototypeId.EPOCH_3_MUSHROOM_AUTO_PROVIDER)
                                        .build()
                        ))
                        .build()
        );
    }


    public IdleMushroomGame(ISaveTool<RootSaveData> saveTool) {
        super(960, 640);
        this.debugMode = true;
        
        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new DemoSaveHandler(frontend, saveTool);
        this.mainSkinFilePath = null;
        this.idleMushroomTextureManager = new IdleMushroomTextureManager();
        this.textureManager = this.idleMushroomTextureManager;
        this.screenContext = new DemoScreenContext(this);
        this.audioPlayManager = new AudioPlayManager(this);
        this.childGameConfig = new IdleMushroomChildGameConfig();

        this.controlBoardScreenIds = JavaFeatureForGwt.listOf(
                DemoScreenId.SCREEN_MAIN,
                DemoScreenId.SCREEN_WORLD,
                DemoScreenId.SCREEN_ACHIEVEMENT
        );
    }


    
    @Override
    protected void createStage1() {
        super.createStage1();
        this.mainSkin = new FreeTypeSkin(Gdx.files.internal("skins/DefaultSkinWithFreeType/DefaultSkinWithFreeType.json"));
        this.idleGameplayExport = new IdleGameplayExport(
                frontend,
                new DemoGameDictionary(),
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

    @Data
    @AllArgsConstructor
    @Builder
    public static class RootEpochConfig {
        Integer maxLevel;
        Integer outputScale;
        Map<String, ConstructionEpochConfig> constructionEpochConfigMap;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ConstructionEpochConfig {

        String transformToPrototypeId;
    }

    public void doChangeEpoch(BaseConstruction epochCounter) {
        int currentEpochLevel = epochCounter.getSaveData().getLevel();
        RootEpochConfig rootEpochConfig = epochConfigMap.get(currentEpochLevel);

        idleGameplayExport.getGameplayContext().getConstructionManager().getWorldConstructionInstances().stream()
                .forEach(it -> {
                    ConstructionEpochConfig constructionEpochConfig = rootEpochConfig.getConstructionEpochConfigMap()
                            .get(it.getPrototypeId());
                    if (constructionEpochConfig != null) {
                        idleGameplayExport.getGameplayContext().getConstructionManager().addToRemoveQueue(it);
                        idleGameplayExport.getGameplayContext().getConstructionManager().addToCreateQueue(constructionEpochConfig.getTransformToPrototypeId(), it.getPosition());
                    }
                });

    }
}
