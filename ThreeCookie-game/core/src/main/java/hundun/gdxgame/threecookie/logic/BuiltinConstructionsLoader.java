package hundun.gdxgame.threecookie.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

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
import hundun.gdxgame.threecookie.IdleDemoGame;


public class BuiltinConstructionsLoader {
    IdleDemoGame game;
    List<BaseConstruction> constructions = new ArrayList<>();

    private static final BiFunction<Long, Integer, Long> THREE_THEME_UPGRADE_CALCULATE_COST_FUNCTION = (baseValue, level) -> {
        switch (level) {
            case 0:
                return 3L;
            case 1:
                return 13L;
            case 2:
                return 33L;
            case 3:
                return 63L;
            case 4:
                return 93L;
            case 5:
                return 133L;
            case 6:
                return 233L;
            case 7:
                return 333L;
            case 8:
                return 633L;
            case 9:
                return 1333L;
            case 10:
                return 3333L;
            default:
                return baseValue;
        }
    };
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
                    ResourceType.COOKIE, 3
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
                    ResourceType.COOKIE, 3
                    )));
            outputComponent.setAutoOutputSecondCountMax(3);
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 3
                    )));
            upgradeComponent.setCalculateCostFunction(THREE_THEME_UPGRADE_CALCULATE_COST_FUNCTION);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            construction.lazyInitDescription();
            construction.setMaxLevel(9);
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
                    ResourceType.COOKIE, 3
                    )));
            outputComponent.setOutputGainPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 9
                    )));
            outputComponent.setAutoOutputSecondCountMax(3);
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaHighVersionFeature.mapOf(
                    ResourceType.COIN, 3
                    )));
            upgradeComponent.setCalculateCostFunction(THREE_THEME_UPGRADE_CALCULATE_COST_FUNCTION);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            construction.lazyInitDescription();
            construction.setMaxLevel(9);
            constructions.add(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseBuffConstruction(game, ConstructionId.WIN_PROVIDER, BuffId.WIN);
            construction.name = game.getGameDictionary().constructionIdToShowName(construction.getId());
            construction.detailDescroptionConstPart = "Unlock the trophy and win the game";
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
                    ResourceType.COIN, 333
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
