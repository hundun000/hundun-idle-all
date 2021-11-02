package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import hundun.gdxgame.bugindustry.BugIndustryGame;


public class MenuScreen extends BaseScreen {

    
//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    
    
    Button buttonIntoGameScreen;
    Button buttonIntoSettingScreen;
    Table table;
    public MenuScreen(BugIndustryGame game) {
        super(game);
        
        initScene2d();

    }

    private void initScene2d() {
        
        table = new Table();
        table.setBounds((Gdx.graphics.getWidth() - 100)/2, 10, 100, 500);
        stage.addActor(table);
        
        
        buttonIntoGameScreen = new TextButton("intoGameScreen", game.getButtonSkin());
        buttonIntoGameScreen.setSize(100, 100);
        buttonIntoGameScreen.setPosition(0, 0);
        buttonIntoGameScreen.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getScreenContext().getGameBeeScreen());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        table.addActor(buttonIntoGameScreen);
        
        buttonIntoSettingScreen = new TextButton("intoSettingScreen", game.getButtonSkin());
        buttonIntoSettingScreen.setSize(100, 50);
        buttonIntoSettingScreen.setPosition(0, 100);
        buttonIntoSettingScreen.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getScreenContext().getGameBeeScreen());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        table.addActor(buttonIntoSettingScreen);
        
    }
    
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        menu.setOnAction(s -> {
//            switch (s) {
//                case "Play":
//                    //game.setScreen(game.getScreenContext().getGameScreen());
//                    break;
//                case "Settings":
//                    //game.setScreen(game.getScreenContext().getSettingsScreen());
//                    break;
//                case "Credits":
//                    //game.setScreen(game.getScreenContext().getCreditsScreen());
//                    break;
//                case "Exit":
//                    //game.setScreen(game.getScreenContext().getExitScreen());
//                    break;
//            }
//        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();

//        game.getFont().draw(game.getBatch(), menuText, camera.viewportWidth / 2f - (menuText.width / 2f),
//                camera.viewportHeight - 50);
//
//        menu.render(game.getBatch(), true, true, camera.viewportWidth / 2f,
//                camera.viewportHeight - 100, 0, -30);

        game.getBatch().end();
        
        
        stage.act();
        stage.draw();
    }



    @Override
    public void dispose() {}
}