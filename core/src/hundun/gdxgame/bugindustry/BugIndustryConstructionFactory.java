package hundun.gdxgame.bugindustry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.BaseAutoConstruction;
import hundun.gdxgame.idleframe.model.construction.BaseClickGatherConstruction;
import hundun.gdxgame.idleframe.model.construction.BaseConstruction;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class BugIndustryConstructionFactory extends BaseConstructionFactory {
    

    public BugIndustryConstructionFactory(BaseIdleGame game) {
        this.game = game;
        
    }
    
    @Override
    public void lazyInit() {
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
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.WOOD, 1)
                    );
            construction.updateDescription();
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_KEEPING, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain some wood";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.WOOD, 1)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 25,
                    ResourceType.WOOD, 5
                    ));
            construction.setMAX_DRAW_NUM(15);
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_MAKER, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto use wood make wood-board";
            construction.descriptionPackage = BaseConstruction.WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.WOOD, 50
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.WOOD_BOARD, 1)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 500
                    ));
            construction.updateDescription();
            register(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WIN_THE_GAME);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Get a trophy and win the game";
            construction.descriptionPackage = BaseConstruction.WIN_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.QUEEN_BEE, 500
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.WIN_TROPHY, 1)
                    );
            construction.updateDescription();
            register(construction);
        }
    }
    
    private ResourcePack toPack(Map<String, Integer> map) {
        ResourcePack pack = new ResourcePack();
        List<ResourcePair> pairs = new ArrayList<>(map.size());
        map.entrySet().forEach(entry -> pairs.add(new ResourcePair(entry.getKey(), (long)entry.getValue())));
        pack.setBaseValues(pairs);
        return pack;
    }
    
    private void initShop() {
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.WOOD, 1
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.COIN, 5)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 50
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood-board";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.WOOD_BOARD, 1
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.COIN, 300)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 500
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEE_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell bee";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.BEE, 1
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.COIN, 5)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 50
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.HONEY_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell honey";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.HONEY, 1
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.COIN, 10)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 100
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEEWAX_SELL_HOUSE, true);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell beewax";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            construction.outputCostPack = toPack(Map.of(
                    ResourceType.HONEY, 1
                    ));
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.COIN, 100)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 1000
                    ));
            construction.updateDescription();
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
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.BEE, 1)
                    );
            construction.updateDescription();
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.SMALL_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.HONEY, 1)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 50, 
                    ResourceType.WOOD, 5, 
                    ResourceType.BEE, 3
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.MID_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee and honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.BEE, 1,
                    ResourceType.HONEY, 3)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 200,
                    ResourceType.WOOD, 15, 
                    ResourceType.BEE, 5,
                    ResourceType.HONEY, 5
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BIG_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee, honey and beewax";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.BEE, 1,
                    ResourceType.HONEY, 3,
                    ResourceType.BEEWAX, 3)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 500,
                    ResourceType.WOOD, 30, 
                    ResourceType.WOOD_BOARD, 5, 
                    ResourceType.BEE, 10,
                    ResourceType.HONEY, 10
                    ));
            construction.updateDescription();
            register(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.QUEEN_BEEHIVE, false);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain queen-bee";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            construction.outputGainPack = toPack(Map.of(
                    ResourceType.QUEEN_BEE, 1)
                    );
            construction.upgradeCostPack = toPack(Map.of(
                    ResourceType.COIN, 5000,
                    ResourceType.WOOD_BOARD, 50, 
                    ResourceType.BEE, 100,
                    ResourceType.HONEY, 50,
                    ResourceType.BEEWAX, 10
                    ));
            construction.updateDescription();
            register(construction);
        }
        
    }

}
