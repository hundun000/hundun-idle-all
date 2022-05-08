package hundun.gdxgame.idleframe.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import hundun.gdxgame.idleframe.data.ConstructionSaveData;
import hundun.gdxgame.idleframe.data.SaveData;
import hundun.gdxgame.idleframe.model.ModelContext;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class PreferencesSaveUtils {
    
    static Preferences preferences;
    private static final String ROOT_KEY = "root";
    private static ObjectMapper objectMapper;
    
    public static void init(String preferencesName) {
        PreferencesSaveUtils.preferences = Gdx.app.getPreferences(preferencesName);;
        PreferencesSaveUtils.objectMapper = new ObjectMapper()
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
            preferences.putString(ROOT_KEY, objectMapper.writeValueAsString(saveData));
            preferences.flush();
            Gdx.app.log(PreferencesSaveUtils.class.getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(PreferencesSaveUtils.class.getSimpleName(), "save() error", e);
        }
        
    }
    
    public static boolean hasSave() {
        return preferences != null && preferences.contains(ROOT_KEY);
    }
    

    
    
    public static void load(ModelContext modelContext) {
        
        if (!hasSave()) {
            Gdx.app.log(PreferencesSaveUtils.class.getSimpleName(), "no savefile, load() do nothing");
            return;
        }
        
        SaveData saveData;
        try {
            String date = preferences.getString(ROOT_KEY);
            saveData = objectMapper.readValue(date, SaveData.class);
        } catch (IOException e) {
            Gdx.app.error(PreferencesSaveUtils.class.getSimpleName(), "load() error", e);
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

        Gdx.app.log(PreferencesSaveUtils.class.getSimpleName(), "load() done");
    }
}
