package hundun.gdxgame.bugindustry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.BugGameDictionary;
import hundun.gdxgame.bugindustry.logic.BugTextureManager;
import hundun.gdxgame.bugindustry.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.bugindustry.logic.ScreenId;
import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.bugindustry.ui.screen.ScreenContext;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.framework.util.save.ISaveTool;
import hundun.gdxgame.idleshare.starter.ui.screen.menu.MenuScreen;
import lombok.Getter;

public class BugIndustryGame extends BaseIdleGame {

    @Getter
    private ScreenContext screenContext;
    
    
    
    public BugIndustryGame(ISaveTool saveTool) {
        super(640, 480, saveTool);
        this.skinFilePath = "skins/orange/skin/uiskin.json";
        drawGameImageAndPlayAudio = false;
        debugMode = false;
    }
    
    @Override
    protected ChildGameConfig getChildGameConfig() {
        return new BugIndustryGameConfig(this);
    }
    
    @Override
    public void create () {
        super.create();
       
        setScreen(screenContext.getMenuScreen());
        getAudioPlayManager().intoScreen(screenContext.getMenuScreen().getScreenId());
    }
    
    @Override
    protected void initContexts() {
        super.initContexts();
        
        this.gameDictionary = new BugGameDictionary();
        this.textureManager = new BugTextureManager();
        
        this.screenContext = new ScreenContext();
        screenContext.setMenuScreen(new MenuScreen<>(
                this,
                ScreenId.MENU,
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        BugIndustryGame.this.loadAndHookSave(true);
                        BugIndustryGame.this.setScreen(BugIndustryGame.this.getScreenContext().getGameBeeScreen());
                        BugIndustryGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                },
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        BugIndustryGame.this.loadAndHookSave(false);
                        BugIndustryGame.this.setScreen(BugIndustryGame.this.getScreenContext().getGameBeeScreen());
                        BugIndustryGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
        ));
        screenContext.setGameBeeScreen(new PlayScreen(this));
    }

    @Override
    public List<String> getGameAreaValues() {
        return GameArea.values;
    }
    
}
