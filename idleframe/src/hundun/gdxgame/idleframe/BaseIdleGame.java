package hundun.gdxgame.idleframe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idleframe.data.SaveData;
import hundun.gdxgame.idleframe.data.StarterData;
import hundun.gdxgame.idleframe.model.ModelContext;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.manager.AchievementManager;
import hundun.gdxgame.idleframe.model.manager.AudioPlayManager;
import hundun.gdxgame.idleframe.model.manager.BuffManager;
import hundun.gdxgame.idleframe.model.manager.ConstructionManager;
import hundun.gdxgame.idleframe.model.manager.EventManager;
import hundun.gdxgame.idleframe.model.manager.GameEntityManager;
import hundun.gdxgame.idleframe.model.manager.StorageManager;
import hundun.gdxgame.idleframe.model.manager.AbstractTextureManager;
import hundun.gdxgame.idleframe.util.IGameDictionary;
import hundun.gdxgame.idleframe.util.save.ISaveTool;
import hundun.gdxgame.idleframe.util.save.PreferencesSaveTool;
import lombok.Getter;

public abstract class BaseIdleGame extends Game {
    public boolean debugMode = false;
    public boolean drawGameImageAndPlayAudio = true;
    public int desktopScale = 1;
    public final int LOGIC_WIDTH;
    public final int LOGIC_HEIGHT;
    protected String DEFAULT_SKIN_FILA_PATH = "skins/default/skin/uiskin.json";
    protected String skinFilePath;
    
    @Getter
    private SpriteBatch batch;

    @Getter
    private ModelContext modelContext;
    @Getter
    private EventManager eventManager;
    @Getter
    private AudioPlayManager audioPlayManager;
    
    @Getter
    protected AbstractTextureManager textureManager;
    @Getter
    protected IGameDictionary gameDictionary;
    
    @Getter
    private Skin buttonSkin;
    @Getter
    private ISaveTool saveTool;
    
    private StarterData starterData;
    
    public BaseIdleGame(int LOGIC_WIDTH, int LOGIC_HEIGHT, ISaveTool saveTool) {
        this.LOGIC_WIDTH = LOGIC_WIDTH;
        this.LOGIC_HEIGHT = LOGIC_HEIGHT;
        this.saveTool = saveTool;
    }
    
	@Override
	public void create() {

		this.batch = new SpriteBatch();
		
		saveTool.lazyInitOnGameCreate();
		initContexts();
		contextsLazyInit();
	}

	public abstract List<String> getGameAreaValues();
	
	public void loadAndHookSave(boolean load) {
	    
	    if (load) {
	        saveTool.load(modelContext);
	        // post
	        //this.getEventManager().notifyBuffChange(true);
	        //this.getEventManager().notifyResourceAmountChange(true);
	    } else {
	        this.newSaveStarter(modelContext);
	    }
	    
	    
	    
	    
	}
	
    /**
     * 作为新存档，也需要修改ModelContext
     */
    public void newSaveStarter(ModelContext modelContext) {
        var constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            construction.getSaveData().setLevel(starterData.getConstructionStarterLevelMap().getOrDefault(construction.getId(), 0));
            if (starterData.getConstructionStarterWorkingLevelMap().getOrDefault(construction.getId(), false)) {
                construction.getSaveData().setWorkingLevel(starterData.getConstructionStarterLevelMap().getOrDefault(construction.getId(), 0));
            }
            construction.updateModifiedValues();
        }
    }

	
	protected void initContexts() {
	    
	    if (drawGameImageAndPlayAudio && skinFilePath != null) {
            this.buttonSkin = new Skin(Gdx.files.internal(skinFilePath));
        } else {
            this.buttonSkin = new Skin(Gdx.files.internal(DEFAULT_SKIN_FILA_PATH));
        }
	    
        this.modelContext = new ModelContext();
        this.eventManager = new EventManager();
        
        modelContext.setStorageManager(new StorageManager(this));
        modelContext.setBuffManager(new BuffManager(this));
        
        modelContext.setConstructionFactory(new BaseConstructionFactory());
        modelContext.setAchievementManager(new AchievementManager(this));
        modelContext.setConstructionManager(new ConstructionManager(this));
        modelContext.setGameEntityManager(new GameEntityManager(this));
        
        this.audioPlayManager = new AudioPlayManager(this);
        
        
	}


	
	@Override
	public void dispose () {
		batch.dispose();
		audioPlayManager.dispose();
	}


    private void contextsLazyInit() {
        ChildGameConfig childGameConfig = getChildGameConfig();
        
        this.starterData = childGameConfig.getStarterData();
        
        modelContext.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        modelContext.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        modelContext.getGameEntityManager().lazyInit(childGameConfig.getAreaShowEntityByOwnAmountConstructionIds(), childGameConfig.getAreaShowEntityByOwnAmountResourceIds(), childGameConfig.getAreaShowEntityByChangeAmountResourceIds());
        modelContext.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
        audioPlayManager.lazyInit(childGameConfig.getScreenIdToFilePathMap());
        
        
    }
    
    protected abstract ChildGameConfig getChildGameConfig();
}
