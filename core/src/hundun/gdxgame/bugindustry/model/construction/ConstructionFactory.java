package hundun.gdxgame.bugindustry.model.construction;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.data.ConstructionOuputRule;
import hundun.gdxgame.bugindustry.model.ResourceType;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class ConstructionFactory {
    
    Map<ConstructionId, BaseConstruction> constructions = new HashMap<>();
    BugIndustryGame game;
    
    public ConstructionFactory(BugIndustryGame game) {
        this.game = game;
        initWood();
        initBee();
        initShop();
    }
    
    private void initWood() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WOOD_GATHER_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Click gain some wood";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WOOD, 1)
                    );
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_KEEPING, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain some wood";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WOOD, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 25,
                    ResourceType.WOOD, 5
                    );
            construction.setMAX_DRAW_NUM(15);
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_MAKER, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto use wood make wood-board";
            construction.descriptionPackage = BaseConstruction.WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.WOOD, 50
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WOOD_BOARD, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 500
                    );
            register(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WIN_THE_GAME);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Get a trophy and win the game";
            construction.descriptionPackage = BaseConstruction.WIN_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.QUEEN_BEE, 500
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WIN_TROPHY, 1)
                    );
            register(construction);
        }
    }
    
    private void initShop() {
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.WOOD, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 5)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 50
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood-board";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.WOOD_BOARD, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 300)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 500
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEE_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell bee";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.BEE, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 5)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 50
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.HONEY_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell honey";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.HONEY, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 10)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 100
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEEWAX_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell beewax";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.baseOutputCostMap = Map.of(
                    ResourceType.HONEY, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 100)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 1000
                    );
            register(construction);
        }
        
        
        
        
        
    }
    
    private void initBee() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.BEE_GATHER_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Click gain bee";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.BEE, 1)
                    );
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.SMALL_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.HONEY, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 50, 
                    ResourceType.WOOD, 5, 
                    ResourceType.BEE, 3
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.MID_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee and honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.BEE, 1),
                    new ConstructionOuputRule(ResourceType.HONEY, 3)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 200,
                    ResourceType.WOOD, 15, 
                    ResourceType.BEE, 5,
                    ResourceType.HONEY, 5
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BIG_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee, honey and beewax";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.BEE, 1),
                    new ConstructionOuputRule(ResourceType.HONEY, 3),
                    new ConstructionOuputRule(ResourceType.BEEWAX, 3)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 500,
                    ResourceType.WOOD, 30, 
                    ResourceType.WOOD_BOARD, 5, 
                    ResourceType.BEE, 10,
                    ResourceType.HONEY, 10
                    );
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.QUEEN_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain queen-bee";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.QUEEN_BEE, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.COIN, 5000,
                    ResourceType.WOOD_BOARD, 50, 
                    ResourceType.BEE, 100,
                    ResourceType.HONEY, 50,
                    ResourceType.BEEWAX, 10
                    );
            register(construction);
        }
//        // buff
//        {
//            BaseConstruction construction = new BaseBuffConstruction(game, BuffId.BUFF_HONEY, ConstructionId.HONEY_BUFF_PROVIDER);
//            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
//            construction.detailDescroptionConstPart = "speed up gain honey.";
//            construction.descriptionPackage = BaseConstruction.BUFF_DESCRIPTION_PACKAGE;
//            construction.baseUpgradeCostMap = (
//                    Map.of(
//                            ResourceType.QUEEN_BEE, 1
//                            )
//                    );
//            register(construction);
//        }
        
    }
    
    private void register(BaseConstruction construction) {
        constructions.put(construction.getId(), construction);
    }
    
    public BaseConstruction getConstruction(ConstructionId id) {
        var result = constructions.get(id);
        if (result == null) {
            throw new RuntimeException("getConstruction " + id + " not found");
        }
        return result;
    }
    
    public Collection<BaseConstruction> getConstructions() {
        return constructions.values();
    }
}
