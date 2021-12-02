package hundun.gdxgame.idleframe.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import hundun.gdxgame.idleframe.data.ConstructionSaveData;
import hundun.gdxgame.idleframe.data.SaveData;
import hundun.gdxgame.idleframe.model.ModelContext;
import hundun.gdxgame.idleframe.model.construction.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class SaveUtils {
    
    static File saveFile;
    private static ObjectMapper objectMapper;
    
    private static Map<String, Integer> constructionStarterLevelMap;
    
    public static void init(File saveFile) {
        SaveUtils.saveFile = saveFile;
        SaveUtils.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                ;
        
    }
    
    public static void lazyInit(Map<String, Integer> constructionStarterLevelMap) {

        SaveUtils.constructionStarterLevelMap = constructionStarterLevelMap;

    }
    
    private static void appendConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        map.put(construction.getSaveDataKey(), construction.getSaveData());
    }
    
    private static void loadConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        construction.setSaveData(map.getOrDefault(construction.getSaveDataKey(), new ConstructionSaveData()));
        construction.updateModifiedValues();
    }
    
    private static void setStarterConstructionSaveData(BaseConstruction construction) {
        construction.getSaveData().setLevel(constructionStarterLevelMap.getOrDefault(construction.getId(), 0));
        construction.updateModifiedValues();
    }
    
    public static void save(ModelContext modelContext) {
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                Gdx.app.error(SaveUtils.class.getSimpleName(), "saveFile.createNewFile() fail", e);
                e.printStackTrace();
            }
        }
        
        SaveData saveData = new SaveData();
        saveData.setOwnResoueces(modelContext.getStorageManager().getOwnResoueces());
        saveData.setUnlockedResourceTypes(modelContext.getStorageManager().getUnlockedResourceTypes());
        saveData.setBuffAmounts(modelContext.getBuffManager().getBuffAmounts());
        saveData.setUnlockedAchievementNames(modelContext.getAchievementManager().getUnlockedAchievementNames());
        Map<String, ConstructionSaveData> map = new HashMap<>();
        var constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            appendConstructionSaveData(map, construction);
        }
        saveData.setConstructionSaveDataMap(map);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(objectMapper.writeValueAsString(saveData));
            writer.flush();
            writer.close();
            Gdx.app.log(SaveUtils.class.getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(SaveUtils.class.getSimpleName(), "save() error", e);
        }
        

    }
    
    public static boolean hasSave() {
        return saveFile.exists();
    }
    
    /**
     * 作为新存档，也需要修改ModelContext
     */
    public static void newSaveStarter(ModelContext modelContext) {
        var constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            setStarterConstructionSaveData(construction);
        }
    }
    
    
    public static void load(ModelContext modelContext) {
        
        if (!saveFile.exists()) {
            Gdx.app.log(SaveUtils.class.getSimpleName(), "no savefile, load() do nothing");
            return;
        }
        
        SaveData saveData;
        try {
            saveData = objectMapper.readValue(saveFile, SaveData.class);
        } catch (IOException e) {
            Gdx.app.error(SaveUtils.class.getSimpleName(), "load() error", e);
            return;
        }
        
        modelContext.getStorageManager().setOwnResoueces(saveData.getOwnResoueces());
        modelContext.getStorageManager().setUnlockedResourceTypes(saveData.getUnlockedResourceTypes());
        modelContext.getBuffManager().setBuffAmounts(saveData.getBuffAmounts());
        modelContext.getAchievementManager().setUnlockedAchievementNames(saveData.getUnlockedAchievementNames());
        Map<String, ConstructionSaveData> map = saveData.getConstructionSaveDataMap();
        var constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            loadConstructionSaveData(map, construction);
        }

        Gdx.app.log(SaveUtils.class.getSimpleName(), "load() done");
    }
}
