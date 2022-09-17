package hundun.gdxgame.bugindustry;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.BuffId;
import hundun.gdxgame.bugindustry.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.bugindustry.logic.ScreenId;
import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.data.StarterData;
import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.framework.util.JavaHighVersionFeature;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class BugIndustryGameConfig extends ChildGameConfig {
    
    public BugIndustryGameConfig(BugIndustryGame game) {
//        File configFile = Gdx.files.internal("constructions.json").file();
//        ConstructionsFileLoader constructionsLoader = new ConstructionsFileLoader(game, game.getGameDictionary(), configFile);
        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.BEE_FARM, JavaHighVersionFeature.arraysAsList(
            ConstructionId.BEE_GATHER_HOUSE,
            ConstructionId.SMALL_BEEHIVE,
            ConstructionId.MID_BEEHIVE,
            ConstructionId.BIG_BEEHIVE,
            ConstructionId.QUEEN_BEEHIVE
        ));
        areaShownConstructionIds.put(GameArea.FOREST_FARM, JavaHighVersionFeature.arraysAsList(
            ConstructionId.WOOD_GATHER_HOUSE,
            ConstructionId.WOOD_KEEPING,
            ConstructionId.WOOD_BOARD_MAKER,
            ConstructionId.WIN_PROVIDER
        ));
        areaShownConstructionIds.put(GameArea.SHOP, JavaHighVersionFeature.arraysAsList(
            ConstructionId.WOOD_SELL_HOUSE,
            ConstructionId.WOOD_BOARD_SELL_HOUSE,
            ConstructionId.BEE_SELL_HOUSE,
            ConstructionId.HONEY_SELL_HOUSE,
            ConstructionId.BEEWAX_SELL_HOUSE
        ));
        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.BEE_FARM, JavaHighVersionFeature.arraysAsList(
            ResourceType.BEE
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.WOOD_SELL_HOUSE, 1);
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.WOOD_SELL_HOUSE, Boolean.FALSE);
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
        Map<String, String> screenIdToFilePathMap = JavaHighVersionFeature.mapOf(
                ScreenId.MENU, "audio/Loop-Menu.wav",
                ScreenId.PLAY, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaHighVersionFeature.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaHighVersionFeature.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }
}
