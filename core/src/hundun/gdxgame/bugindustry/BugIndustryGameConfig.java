package hundun.gdxgame.bugindustry;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idlestarter.ConstructionsFileLoader;

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
        areaShownConstructionIds.put(GameArea.BEE_FARM, Arrays.asList(
            ConstructionId.BEE_GATHER_HOUSE,
            ConstructionId.SMALL_BEEHIVE,
            ConstructionId.MID_BEEHIVE,
            ConstructionId.BIG_BEEHIVE,
            ConstructionId.QUEEN_BEEHIVE
        ));
        areaShownConstructionIds.put(GameArea.FOREST_FARM, Arrays.asList(
            ConstructionId.WOOD_GATHER_HOUSE,
            ConstructionId.WOOD_KEEPING,
            ConstructionId.WOOD_BOARD_MAKER,
            ConstructionId.WIN_THE_GAME
        ));
        areaShownConstructionIds.put(GameArea.SHOP, Arrays.asList(
            ConstructionId.WOOD_SELL_HOUSE,
            ConstructionId.WOOD_BOARD_SELL_HOUSE,
            ConstructionId.BEE_SELL_HOUSE,
            ConstructionId.HONEY_SELL_HOUSE,
            ConstructionId.BEEWAX_SELL_HOUSE
        ));
        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.BEE_FARM, Arrays.asList(
            ResourceType.BEE
        ));
        this.setAreaShowEntityResourceIds(areaShownResourceIds);
        
        Map<String, Integer> constructionStarterLevelMap = Map.of(ConstructionId.WOOD_SELL_HOUSE, 1);
        this.setConstructionStarterLevelMap(constructionStarterLevelMap);
    }
}
