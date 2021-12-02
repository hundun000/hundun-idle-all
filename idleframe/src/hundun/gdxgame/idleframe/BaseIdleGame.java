package hundun.gdxgame.idleframe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import hundun.gdxgame.idleframe.model.ModelContext;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.model.manager.AchievementManager;
import hundun.gdxgame.idleframe.model.manager.AudioPlayManager;
import hundun.gdxgame.idleframe.model.manager.BuffManager;
import hundun.gdxgame.idleframe.model.manager.ConstructionManager;
import hundun.gdxgame.idleframe.model.manager.EventManager;
import hundun.gdxgame.idleframe.model.manager.StorageManager;
import hundun.gdxgame.idleframe.util.FontUtil;
import hundun.gdxgame.idleframe.util.SaveUtils;
import lombok.Getter;

public abstract class BaseIdleGame extends Game {
    public boolean debugMode = false;
    public boolean drawGameImageAndPlayAudio = true;
    public static final int desktopScale = 1;
    public final int LOGIC_WIDTH = 640;
    public final int LOGIC_HEIGHT = 480;
    public static final int GRID_SIZE = 64;
    //private ChildGameConfig childGameConfig;
    
    @Getter
    private SpriteBatch batch;
    @Getter
	private BitmapFont font;

    @Getter
    private ModelContext modelContext;
    @Getter
    private EventManager eventManager;
    @Getter
    private AudioPlayManager audioPlayManager;


    
    @Getter
    private Skin buttonSkin;
    
    
	@Override
	public void create () {
	    FontUtil.init();
	    SaveUtils.init(Gdx.files.internal("save.json").file());
        
	    
		this.batch = new SpriteBatch();
		this.font = FontUtil.KOMIKA;
		
		if (drawGameImageAndPlayAudio) {
		    this.buttonSkin = new Skin(Gdx.files.internal("skins/orange/skin/uiskin.json"));
		} else {
		    this.buttonSkin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
		}
		
		initContexts();
		contextsLazyInit();
	}

	
	public void loadAndHookSave(boolean load) {
	    
	    if (load) {
	        SaveUtils.load(modelContext);
	        // post
	        this.getEventManager().notifyBuffChange(true);
	        this.getEventManager().notifyResourceAmountChange(true);
	    } else {
	        SaveUtils.newSaveStarter(modelContext);
	    }
	    
	    
	    
	    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	        SaveUtils.save(modelContext);
	    }));
	}
	
	
	
	protected void initContexts() {
	    
	    
        this.modelContext = new ModelContext();
        this.eventManager = new EventManager();
        
        modelContext.setStorageManager(new StorageManager(this));
        modelContext.setBuffManager(new BuffManager(this));
        
        modelContext.setConstructionFactory(new BaseConstructionFactory());
        modelContext.setAchievementManager(new AchievementManager(this));
        modelContext.setConstructionManager(new ConstructionManager(this));
        
        this.audioPlayManager = new AudioPlayManager(this);
        
        
	}


	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		audioPlayManager.dispose();
	}


    private void contextsLazyInit() {
        ChildGameConfig childGameConfig = getChildGameConfig();
        
        SaveUtils.lazyInit(childGameConfig.getConstructionStarterLevelMap());
        
        
        modelContext.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        modelContext.getConstructionManager().lazyInit(modelContext, childGameConfig.getAreaShownConstructionIds());
    }
    
    protected abstract ChildGameConfig getChildGameConfig();
}
