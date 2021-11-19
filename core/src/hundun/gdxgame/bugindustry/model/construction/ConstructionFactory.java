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
        
    }
    
    private void initWood() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WOOD_GATHER_HOUSE);
            construction.name = construction.getId().name();
            construction.detailDescroptionConstPart = "Free gain wood";
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WOOD, 100)
                    );
            register(construction);
        }
        // sell
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.WOOD_SELL_HOUSE);
            construction.name = construction.getId().name();
            construction.detailDescroptionConstPart = "Auto sell wood";
            construction.baseOutputCostMap = Map.of(
                    ResourceType.WOOD, 1
                    );
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.COIN, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.WOOD, 300
                    );
            construction.workingLevelChangable = true;
            register(construction);
        }
    }
    
    private void initBee() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.BEE_GATHER_HOUSE);
            construction.name = construction.getId().name();
            construction.detailDescroptionConstPart = "Free gain bee";
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.BEE, 1)
                    );
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.SMALL_BEEHIVE);
            construction.name = construction.getId().name();
            construction.detailDescroptionConstPart = "Auto gain some honey";
            construction.baseOutputGainRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.HONEY, 1)
                    );
            construction.baseUpgradeCostMap = Map.of(
                    ResourceType.WOOD, 300, 
                    ResourceType.BEE, 3
                    );
            register(construction);
        }
        // buff
        {
            BaseConstruction construction = new BaseBuffConstruction(game, BuffId.BUFF_HONEY, ConstructionId.HONEY_BUFF_PROVIDER);
            construction.name = construction.getId().name();
            construction.detailDescroptionConstPart = "speed up gain honey.";
            construction.baseUpgradeCostMap = (
                    Map.of(
                            ResourceType.GENE_POINT, 1
                            )
                    );
            register(construction);
        }
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
