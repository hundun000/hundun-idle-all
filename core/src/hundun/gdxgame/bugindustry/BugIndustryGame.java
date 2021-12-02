package hundun.gdxgame.bugindustry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.ConstructionsLoader;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.GameDictionary;
import hundun.gdxgame.bugindustry.logic.TextureManager;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;
import hundun.gdxgame.bugindustry.ui.screen.MenuScreen;
import hundun.gdxgame.bugindustry.ui.screen.ScreenContext;
import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import lombok.Getter;

public class BugIndustryGame extends BaseIdleGame {

    @Getter
    private TextureManager textureManager;
    
    @Getter
    private ScreenContext screenContext;
    
    @Getter
    private GameDictionary gameDictionary;
    
    ;
    
    public BugIndustryGame() {
        super();
    }
    
    @Override
    protected ChildGameConfig getChildGameConfig() {
        ChildGameConfig childGameConfig = new ChildGameConfig();
        ConstructionsLoader constructionsLoader = new ConstructionsLoader(this);
        childGameConfig.setConstructions(constructionsLoader.load());
        
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
        childGameConfig.setAreaShownConstructionIds(areaShownConstructionIds);
        
        Map<String, Integer> constructionStarterLevelMap = Map.of(ConstructionId.WOOD_SELL_HOUSE, 1);
        childGameConfig.setConstructionStarterLevelMap(constructionStarterLevelMap);
        
        return childGameConfig;
    }
    
    @Override
    public void create () {
        super.create();
       
        setScreen(screenContext.getMenuScreen());
        getAudioPlayManager().intoMenu();
    }
    
    @Override
    protected void initContexts() {
        super.initContexts();
        
        this.gameDictionary = new GameDictionary();
        this.textureManager = new TextureManager();
        
        this.screenContext = new ScreenContext();
        screenContext.setMenuScreen(new MenuScreen(this));
        screenContext.setGameBeeScreen(new GameScreen(this));
    }
    
}
