package hundun.gdxgame.idleshare.core.starter.ui.screen.menu;

import java.util.List;

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
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;




public abstract class BaseIdleMenuScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends BaseHundunScreen<T_GAME, T_SAVE> {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    int BUTTON_WIDTH = 100;
    int BUTTON_BIG_HEIGHT = 100;
    int BUTTON_SMALL_HEIGHT = 75;

    final InputListener buttonContinueGameInputListener;
    final InputListener buttonNewGameInputListener;

    Label titleLabel;
    Image backImage;
    TextButton buttonContinueGame;
    TextButton buttonNewGame;
    TextButton buttonIntoSettingScreen;

    public BaseIdleMenuScreen(T_GAME game,
            String titleText,
            InputListener buttonContinueGameInputListener, 
            InputListener buttonNewGameInputListener) {
        super(game, game.getSharedViewport());
        
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        

    }

    private void initScene2d() {
        List<String> memuScreenTexts = game.getIdleGameplayExport().getGameDictionary().getMemuScreenTexts(game.getIdleGameplayExport().getLanguage());
        this.titleLabel = new Label(
                JavaFeatureForGwt.stringFormat("     %s     ", memuScreenTexts.get(0)), 
                game.getMainSkin());
        titleLabel.setFontScale(1.5f);
        this.backImage = new Image(game.getTextureManager().getMenuTexture());
        
        buttonContinueGame = new TextButton(memuScreenTexts.get(2), game.getMainSkin());
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = new TextButton(memuScreenTexts.get(1), game.getMainSkin());
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