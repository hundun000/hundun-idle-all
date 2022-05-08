package hundun.gdxgame.idlehelper.desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import hundun.gdxgame.idleframe.util.IGameDictionary;
import hundun.gdxgame.idlestarter.ConstructionsConfig;
import hundun.gdxgame.idlestarter.ConstructionsConfig.ConstructionNode;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class ConstructionsFileLoader {
    private ObjectMapper objectMapper;
    File configFile;
    IGameDictionary gameDictionary;
    BaseIdleGame game;
    List<BaseConstruction> constructions = new ArrayList<>();
    
    public ConstructionsFileLoader(BaseIdleGame game, IGameDictionary gameDictionary, File configFile) {
        this.gameDictionary = gameDictionary;
        this.game = game;
        this.objectMapper = new ObjectMapper();
        this.configFile = configFile;
    }
    
    private ResourcePack toPack(Map<String, Integer> map) {
        ResourcePack pack = new ResourcePack();
        List<ResourcePair> pairs = new ArrayList<>(map.size());
        map.entrySet().forEach(entry -> pairs.add(new ResourcePair(entry.getKey(), (long)entry.getValue())));
        pack.setBaseValues(pairs);
        return pack;
    }


//    public List<BaseConstruction> loadFromFile() {
//        if (!configFile.exists()) {
//            Gdx.app.log(DesktopSaveUtils.class.getSimpleName(), "no savefile, load() do nothing");
//            return constructions;
//        }
//        
//        ConstructionsConfig config;
//        try {
//            config = objectMapper.readValue(configFile, ConstructionsConfig.class);
//        } catch (IOException e) {
//            Gdx.app.error(DesktopSaveUtils.class.getSimpleName(), "load() error", e);
//            return constructions;
//        }
//        
//        for (ConstructionNode node : config.getNodes()) {
//            registerOneConstructionFromFile(node);
//        }
//        return constructions;
//    }
    
    private void registerOneConstructionFromFile(ConstructionNode node) {
        BaseConstruction construction;
        switch (node.getClazz()) {
            case CLICK:
                construction = new BaseClickGatherConstruction(game, node.getId());
                break;
            case AUTO:
                construction = new BaseAutoConstruction(game, node.getId());
                break;
            default:
                return;
        }
                
        construction.name = gameDictionary.constructionIdToShowName(construction.getId());
        construction.detailDescroptionConstPart = node.getDetailDescroptionConstPart();
        construction.descriptionPackage = node.getDescriptionPackage();
        
        OutputComponent outputComponent = new OutputComponent(construction);
        outputComponent.setOutputCostPack(node.getOutputCostPack());
        outputComponent.setOutputGainPack(node.getOutputGainPack());
        construction.setOutputComponent(outputComponent);
        
        UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
        upgradeComponent.setUpgradeCostPack(node.getUpgradeCostPack());
        construction.setUpgradeComponent(upgradeComponent);
        
        LevelComponent levelComponent = new LevelComponent(construction, node.isWorkingLevelChangable());
        construction.setLevelComponent(levelComponent);
        
        construction.updateDescription();
        
        constructions.add(construction);
    }

    
    
    
    

}
