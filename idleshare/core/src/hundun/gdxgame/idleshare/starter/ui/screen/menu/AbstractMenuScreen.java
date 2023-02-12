package hundun.gdxgame.idleshare.starter.ui.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.corelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;




public abstract class AbstractMenuScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends BaseHundunScreen<T_GAME, T_SAVE> {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    int BUTTON_WIDTH = 100;
    int BUTTON_BIG_HEIGHT = 100;
    int BUTTON_SMALL_HEIGHT = 75;

    final InputListener buttonContinueGameInputListener;
    final InputListener buttonNewGameInputListener;

    String titleText;
    Label titleLabel;
    Image backImage;
    TextButton buttonContinueGame;
    TextButton buttonNewGame;
    TextButton buttonIntoSettingScreen;

    public AbstractMenuScreen(T_GAME game,
            String titleText,
            InputListener buttonContinueGameInputListener, 
            InputListener buttonNewGameInputListener) {
        super(game, game.getSharedViewport());
        this.titleText = titleText;
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        

    }

    private void initScene2d() {

        this.titleLabel = new Label(
                JavaFeatureForGwt.stringFormat("     %s     ", titleText), 
                game.getMainSkin());
        titleLabel.setFontScale(1.5f);
        this.backImage = new Image(game.getTextureManager().getMenuTexture());
        
        buttonContinueGame = new TextButton("Continue game", game.getMainSkin());
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = new TextButton("New game", game.getMainSkin());
        buttonNewGame.addListener(buttonNewGameInputListener);
        
        
        backUiStage.clear();
        uiRootTable.clear();
        
        backUiStage.addActor(backImage);

        uiRootTable.add(titleLabel)
                .row();
        
        if (game.getSaveHandler().gameHasSave()) {
            uiRootTable.add(buttonContinueGame)
                .height(BUTTON_BIG_HEIGHT)
                .fillY()
                .padTop(10)
                .row();
        }
        
        uiRootTable.add(buttonNewGame)
            .height(game.getSaveHandler().gameHasSave() ? BUTTON_SMALL_HEIGHT : BUTTON_BIG_HEIGHT)
            .fillY()
            .padTop(10)
            .row();
        
        uiRootTable.debugAll();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(uiStage);
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);
        
        initScene2d();
    }

    @Override
    public void dispose() {}

    @Override
    protected void create() {

    }
}