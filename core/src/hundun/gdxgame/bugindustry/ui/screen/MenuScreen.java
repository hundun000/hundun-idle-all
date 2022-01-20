package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.idleframe.util.SaveUtils;
import hundun.gdxgame.idlestarter.ui.BaseScreen;



public class MenuScreen extends BaseScreen<BugIndustryGame> {

    
//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    int BUTTON_WIDTH = 100;
    int BUTTON_BIG_HEIGHT = 100;
    int BUTTON_SMALL_HEIGHT = 75;
    
    Button buttonContinueGame;
    Button buttonNewGame;
    Button buttonIntoSettingScreen;
    Table table;
    public MenuScreen(BugIndustryGame game) {
        super(game);
        
        initScene2d();

    }

    private void initScene2d() {
        
        Image backImage = new Image(game.getTextureManager().getMenuTexture());
        if (game.drawGameImageAndPlayAudio) {
            uiStage.addActor(backImage);
        }
        
        table = new Table();
        table.setBounds((Gdx.graphics.getWidth() - BUTTON_WIDTH)/2, 0, BUTTON_WIDTH, Gdx.graphics.getHeight() / 2);
        uiStage.addActor(table);
        
        buttonContinueGame = new TextButton("Continue Game", game.getButtonSkin());
        //buttonContinueGame.setSize(100, 100);
        //buttonContinueGame.setPosition(0, 0);
        buttonContinueGame.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.loadAndHookSave(true);
                game.setScreen(game.getScreenContext().getGameBeeScreen());
                game.getAudioPlayManager().intoScreen(PlayScreen.class.getSimpleName());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        if (SaveUtils.hasSave()) {
            table.add(buttonContinueGame)
                .height(BUTTON_BIG_HEIGHT)
                .fill()
                .pad(10)
                .row();
        }
        
        
        
        buttonNewGame = new TextButton("New Game", game.getButtonSkin());
        buttonNewGame.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.loadAndHookSave(false);
                game.setScreen(game.getScreenContext().getGameBeeScreen());
                game.getAudioPlayManager().intoScreen(PlayScreen.class.getSimpleName());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        table.add(buttonNewGame)
            .height(SaveUtils.hasSave() ? BUTTON_SMALL_HEIGHT : BUTTON_BIG_HEIGHT)
            .fill()
            .pad(10)
            .row();
        
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
        
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        

        
        
        uiStage.act();
        uiStage.draw();
    }



    @Override
    public void dispose() {}
}