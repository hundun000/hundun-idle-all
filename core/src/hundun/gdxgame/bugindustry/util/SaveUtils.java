package hundun.gdxgame.bugindustry.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import hundun.gdxgame.bugindustry.data.ConstructionSaveData;
import hundun.gdxgame.bugindustry.data.SaveData;
import hundun.gdxgame.bugindustry.model.ModelContext;
import hundun.gdxgame.bugindustry.model.construction.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class SaveUtils {
    
    static File saveFile;
    private static ObjectMapper objectMapper;
    
    public static void init(File saveFile) {
        SaveUtils.saveFile = saveFile;
        SaveUtils.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                ;
    }
    
    private static void appendConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        map.put(construction.getSaveDataKey(), construction.getSaveData());
    }
    
    private static void loadConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        construction.setSaveData(map.getOrDefault(construction.getSaveDataKey(), new ConstructionSaveData()));
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
        saveData.setOwnResoueces(modelContext.getStorageModel().getOwnResoueces());
        saveData.setBuffAmounts(modelContext.getBuffManager().getBuffAmounts());
        Map<String, ConstructionSaveData> map = new HashMap<>();
        appendConstructionSaveData(map, modelContext.getWoodGatherConstruction());
        appendConstructionSaveData(map, modelContext.getBeeGatherConstruction());
        appendConstructionSaveData(map, modelContext.getSmallBeehiveConstruction());
        appendConstructionSaveData(map, modelContext.getHoneyBuffConstruction());
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
        
        modelContext.getStorageModel().setOwnResoueces(saveData.getOwnResoueces());
        modelContext.getBuffManager().setBuffAmounts(saveData.getBuffAmounts());
        Map<String, ConstructionSaveData> map = saveData.getConstructionSaveDataMap();
        loadConstructionSaveData(map, modelContext.getWoodGatherConstruction());
        loadConstructionSaveData(map, modelContext.getBeeGatherConstruction());
        loadConstructionSaveData(map, modelContext.getSmallBeehiveConstruction());
        loadConstructionSaveData(map, modelContext.getHoneyBuffConstruction());
        
        // post
        modelContext.getBuffManager().notifyListeners();
        
        Gdx.app.log(SaveUtils.class.getSimpleName(), "load() done");
    }
}
