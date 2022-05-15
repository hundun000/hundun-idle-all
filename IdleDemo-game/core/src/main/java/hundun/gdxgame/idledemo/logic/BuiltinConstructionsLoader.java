package hundun.gdxgame.idledemo.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.model.construction.BaseAutoConstruction;
import hundun.gdxgame.idleshare.framework.model.construction.BaseBuffConstruction;
import hundun.gdxgame.idleshare.framework.model.construction.BaseClickGatherConstruction;
import hundun.gdxgame.idleshare.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.framework.model.construction.base.LevelComponent;
import hundun.gdxgame.idleshare.framework.model.construction.base.OutputComponent;
import hundun.gdxgame.idleshare.framework.model.construction.base.UpgradeComponent;
import hundun.gdxgame.idleshare.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.framework.model.resource.ResourcePair;
import hundun.gdxgame.idleshare.framework.util.JavaHighVersionFeature;


public class BuiltinConstructionsLoader {
    IdleDemoGame game;
    List<BaseConstruction> constructions = new ArrayList<>();
    
    public BuiltinConstructionsLoader(IdleDemoGame game) {
        this.game = game;
        
    }
    
    public List<BaseConstruction> load() {
        initProviders();
        return constructions;
    }
    
    private void initProviders() {
        // clicker-provider
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.COOKIE_CLICK_PROVIDER);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Click gain some cookie";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.lazyInitDescription();
            constructions.add(construction);
        }
        // auto-provider
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.COOKIE_AUTO_PROVIDER);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto gain some cookie";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 25
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            construction.lazyInitDescription();
            constructions.add(construction);
        }
        // seller
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.COOKIE_SELLER);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Auto sell cookies";
            construction.descriptionPackage = BaseConstruction.WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 5
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            construction.lazyInitDescription();
            constructions.add(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseBuffConstruction(game, ConstructionId.WIN_PROVIDER, BuffId.WIN);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Get a trophy and win the game";
            construction.descriptionPackage = BaseConstruction.WIN_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
//            outputComponent.setOutputCostPack(toPack(JavaHighVersionFeature.mapOf(
//                    ResourceType.COIN, 500
//                    )));
//            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
//                    ResourceType.WIN_TROPHY, 1
//                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);            
            
            construction.setMaxLevel(1);
            construction.lazyInitDescription();
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
    
    

}
