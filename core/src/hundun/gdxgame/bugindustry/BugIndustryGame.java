package hundun.gdxgame.bugindustry;

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

import hundun.gdxgame.bugindustry.data.SaveData;
import hundun.gdxgame.bugindustry.model.ModelContext;
import hundun.gdxgame.bugindustry.model.construction.ConstructionFactory;
import hundun.gdxgame.bugindustry.model.manager.AchievementManager;
import hundun.gdxgame.bugindustry.model.manager.BuffManager;
import hundun.gdxgame.bugindustry.model.manager.EventManager;
import hundun.gdxgame.bugindustry.model.manager.StorageManager;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;
import hundun.gdxgame.bugindustry.ui.screen.MenuScreen;
import hundun.gdxgame.bugindustry.ui.screen.ScreenContext;
import hundun.gdxgame.bugindustry.util.FontUtil;
import hundun.gdxgame.bugindustry.util.SaveUtils;
import lombok.Getter;

public class BugIndustryGame extends Game {
    public static final int LOGIC_WIDTH = 640;
    public static final int LOGIC_HEIGHT = 480;
    public static final int GRID_SIZE = 64;
    
    @Getter
    private SpriteBatch batch;
    @Getter
	private BitmapFont font;
    @Getter
	private ScreenContext screenContext;
    @Getter
    private ModelContext modelContext;
    @Getter
    private EventManager eventManager;

    @Getter
    private Skin buttonSkin;
    
    
    
	@Override
	public void create () {
	    FontUtil.init();
	    SaveUtils.init(Gdx.files.internal("save.json").file());
	    
		this.batch = new SpriteBatch();
		this.font = FontUtil.KOMIKA;
		this.buttonSkin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
		initContexts();
		
		//loadAndHookSave();
		
		setScreen(screenContext.getMenuScreen());
	}
	
	public void loadAndHookSave(boolean load) {
	    
	    if (load) {
	        SaveUtils.load(modelContext);
	     // post
	        this.getEventManager().notifyBuffChange(true);
	        this.getEventManager().notifyResourceAmountChange();
	    }
	    
	    
	    
	    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	        SaveUtils.save(modelContext);
	    }));
	}
	
	
	
	private void initContexts() {
	    this.screenContext = new ScreenContext();
        screenContext.setMenuScreen(new MenuScreen(this));
        screenContext.setGameBeeScreen(new GameScreen(this));
        
        this.modelContext = new ModelContext();
        this.eventManager = new EventManager();
        modelContext.setStorageManager(new StorageManager(this));
        modelContext.setBuffManager(new BuffManager(this));
        modelContext.setConstructionFactory(new ConstructionFactory(this));
        modelContext.setAchievementManager(new AchievementManager(this));
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
