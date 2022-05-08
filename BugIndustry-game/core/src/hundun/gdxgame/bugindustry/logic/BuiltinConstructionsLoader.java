package hundun.gdxgame.bugindustry.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.BaseAutoConstruction;
import hundun.gdxgame.idleframe.model.construction.BaseClickGatherConstruction;

import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.construction.base.LevelComponent;
import hundun.gdxgame.idleframe.model.construction.base.OutputComponent;
import hundun.gdxgame.idleframe.model.construction.base.UpgradeComponent;
import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;
import hundun.gdxgame.idleframe.util.JavaHighVersionFeature;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class BuiltinConstructionsLoader {
    BugIndustryGame game;
    List<BaseConstruction> constructions = new ArrayList<>();
    
    public BuiltinConstructionsLoader(BugIndustryGame game) {
        this.game = game;
        
    }
    
    public List<BaseConstruction> load() {
        initWood();
        initBee();
        initShop();
        return constructions;
    }
    
    private void initWood() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WOOD_GATHER_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Click gain some wood";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_KEEPING);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain some wood";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 25,
                    ResourceType.WOOD, 5
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.setMAX_DRAW_NUM(15);
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_MAKER);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto use wood make wood-board";
            construction.descriptionPackage = BaseConstruction.WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD, 50
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD_BOARD, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WIN_THE_GAME);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Get a trophy and win the game";
            construction.descriptionPackage = BaseConstruction.WIN_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.QUEEN_BEE, 500
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WIN_TROPHY, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);            
            
            
            construction.updateDescription();
            constructions.add(construction);
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
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_SELL_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 5
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 50
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_BOARD_SELL_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell wood-board";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.WOOD_BOARD, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 300
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEE_SELL_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell bee";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;

            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.BEE, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 5
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 50
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.HONEY_SELL_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell honey";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.HONEY, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 10
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 100
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BEEWAX_SELL_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell beewax";
            construction.descriptionPackage = BaseConstruction.SELLING_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.HONEY, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 100
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 1000
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        
        
        
        
        
    }
    
    private void initBee() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.BEE_GATHER_HOUSE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Click gain bee";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.BEE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.SMALL_BEEHIVE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.HONEY, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 50, 
                    ResourceType.WOOD, 5, 
                    ResourceType.BEE, 3
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.MID_BEEHIVE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee and honey";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.BEE, 1,
                    ResourceType.HONEY, 3
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 200,
                    ResourceType.WOOD, 15, 
                    ResourceType.BEE, 5,
                    ResourceType.HONEY, 5
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.BIG_BEEHIVE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain bee, honey and beewax";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.BEE, 1,
                    ResourceType.HONEY, 3,
                    ResourceType.BEEWAX, 3
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 500,
                    ResourceType.WOOD, 30, 
                    ResourceType.WOOD_BOARD, 5, 
                    ResourceType.BEE, 10,
                    ResourceType.HONEY, 10
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.QUEEN_BEEHIVE);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain queen-bee";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.QUEEN_BEE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 5000,
                    ResourceType.WOOD_BOARD, 50, 
                    ResourceType.BEE, 100,
                    ResourceType.HONEY, 50,
                    ResourceType.BEEWAX, 10
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.updateDescription();
            constructions.add(construction);
        }
        
    }

}
