package hundun.gdxgame.idledemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.idledemo.logic.GameDictionary;
import hundun.gdxgame.idledemo.logic.TextureManager;
import hundun.gdxgame.idledemo.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.ConstructionId;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.ScreenId;
import hundun.gdxgame.idledemo.ui.screen.PlayScreen;
import hundun.gdxgame.idledemo.ui.screen.ScreenContext;
import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleframe.util.save.ISaveTool;
import hundun.gdxgame.idleframe.util.text.TextFormatTool;
import hundun.gdxgame.idlestarter.ui.screen.menu.MenuScreen;


public class IdleDemoGame extends BaseIdleGame {


    private ScreenContext screenContext;
    // ------ replace-lombok ------
    public ScreenContext getScreenContext() {
        return screenContext;
    }
    
    private static class TernaryTextFormatTool extends TextFormatTool {
        @Override
        public String format(long value) {
            String ternaryText = Long.toString(value, 3);
            long fakeDecimal = Long.parseLong(ternaryText, 10);
            return super.format(fakeDecimal);
        }
    }
    
    
    public IdleDemoGame(ISaveTool saveTool) {
        super(640, 480, saveTool);
        //this.skinFilePath = "skins/orange/skin/uiskin.json";
        desktopScale = 1;
        drawGameImageAndPlayAudio = true;
        debugMode = false;
        
        // special overwrite
        this.textFormatTool = new TernaryTextFormatTool();
    }
    
    @Override
    protected ChildGameConfig getChildGameConfig() {
        return new IdleDemoGameConfig(this);
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
        
        this.gameDictionary = new GameDictionary();
        this.textureManager = new TextureManager();
        
        this.screenContext = new ScreenContext();
        screenContext.setMenuScreen(new MenuScreen<>(
                this,
                ScreenId.MENU,
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        IdleDemoGame.this.loadAndHookSave(true);
                        IdleDemoGame.this.setScreen(IdleDemoGame.this.getScreenContext().getGameBeeScreen());
                        IdleDemoGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                },
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        IdleDemoGame.this.loadAndHookSave(false);
                        IdleDemoGame.this.setScreen(IdleDemoGame.this.getScreenContext().getGameBeeScreen());
                        IdleDemoGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
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
