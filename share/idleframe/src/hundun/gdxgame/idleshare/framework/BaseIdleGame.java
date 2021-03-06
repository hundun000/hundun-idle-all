package hundun.gdxgame.idleshare.framework;

import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.data.StarterData;
import hundun.gdxgame.idleshare.framework.model.ModelContext;
import hundun.gdxgame.idleshare.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.framework.model.manager.AbstractTextureManager;
import hundun.gdxgame.idleshare.framework.model.manager.AchievementManager;
import hundun.gdxgame.idleshare.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.idleshare.framework.model.manager.BuffManager;
import hundun.gdxgame.idleshare.framework.model.manager.ConstructionManager;
import hundun.gdxgame.idleshare.framework.model.manager.EventManager;
import hundun.gdxgame.idleshare.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.framework.model.manager.StorageManager;
import hundun.gdxgame.idleshare.framework.util.save.ISaveTool;
import hundun.gdxgame.idleshare.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.framework.util.text.TextFormatTool;


public abstract class BaseIdleGame extends Game {
    public boolean debugMode = false;
    public boolean drawGameImageAndPlayAudio = true;
    public int desktopScale = 1;
    public final int LOGIC_WIDTH;
    public final int LOGIC_HEIGHT;
    protected String DEFAULT_SKIN_FILA_PATH = "skins/default/skin/uiskin.json";
    protected String skinFilePath;

    private SpriteBatch batch;
    // ------ replace-lombok ------
    public SpriteBatch getBatch() {
        return batch;
    }

    private ModelContext modelContext;
    // ------ replace-lombok ------
    public ModelContext getModelContext() {
        return modelContext;
    }

    private EventManager eventManager;
    // ------ replace-lombok ------
    public EventManager getEventManager() {
        return eventManager;
    }

    private AudioPlayManager audioPlayManager;
    // ------ replace-lombok ------
    public AudioPlayManager getAudioPlayManager() {
        return audioPlayManager;
    }

    protected AbstractTextureManager textureManager;
    // ------ replace-lombok ------
    public AbstractTextureManager getTextureManager() {
        return textureManager;
    }

    protected IGameDictionary gameDictionary;
    // ------ replace-lombok ------
    public IGameDictionary getGameDictionary() {
        return gameDictionary;
    }

    private Skin buttonSkin;
    // ------ replace-lombok ------
    public Skin getButtonSkin() {
        return buttonSkin;
    }

    private ISaveTool saveTool;
    // ------ replace-lombok ------
    public ISaveTool getSaveTool() {
        return saveTool;
    }

    protected TextFormatTool textFormatTool;
    // ------ replace-lombok ------
    public TextFormatTool getTextFormatTool() {
        return textFormatTool;
    }


    private StarterData starterData;

    public BaseIdleGame(int LOGIC_WIDTH, int LOGIC_HEIGHT, ISaveTool saveTool) {
        this.LOGIC_WIDTH = LOGIC_WIDTH;
        this.LOGIC_HEIGHT = LOGIC_HEIGHT;
        this.saveTool = saveTool;
        this.textFormatTool = new TextFormatTool();
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
     * ?????????????????????????????????ModelContext
     */
    public void newSaveStarter(ModelContext modelContext) {
        Collection<BaseConstruction> constructions = modelContext.getConstructionFactory().getConstructions();
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
