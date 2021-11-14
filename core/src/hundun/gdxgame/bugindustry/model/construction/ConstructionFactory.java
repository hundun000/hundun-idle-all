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
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.WOOD_GATHER_POINT);
            construction.name = construction.getClass().getSimpleName();
            construction.detailDescroptionConstPart = "Free gain wood";
            construction.baseOutputRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WOOD, 100)
                    );
            register(construction);
        }
    }
    
    private void initBee() {
        // gather
        {
            BaseConstruction construction = new BaseClickGatherConstruction(game, ConstructionId.BEE_GATHER_POINT);
            construction.name = construction.getClass().getSimpleName();
            construction.detailDescroptionConstPart = "Free gain worker-bee";
            construction.baseOutputRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.WORKER_BEE, 1)
                    );
            register(construction);
        }
        // auto
        {
            BaseConstruction construction = new BaseAutoConstruction(game, ConstructionId.SMALL_BEEHIVE);
            construction.name = construction.getClass().getSimpleName();
            construction.detailDescroptionConstPart = "Auto gain some honey";
            construction.baseOutputRules = Arrays.asList(
                    new ConstructionOuputRule(ResourceType.HONEY, 1)
                    );
            construction.baseUpgradeCostMap = (
                    Map.of(
                            ResourceType.WOOD, 300, 
                            ResourceType.WORKER_BEE, 3
                            )
                    );
            register(construction);
        }
        // buff
        {
            BaseConstruction construction = new BaseBuffConstruction(game, BuffId.BUFF_HONEY, ConstructionId.HONEY_BUFF_PROVIDER);
            construction.name = construction.getClass().getSimpleName();
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
