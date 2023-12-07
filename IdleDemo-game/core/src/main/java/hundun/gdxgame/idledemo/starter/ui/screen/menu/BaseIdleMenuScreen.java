package hundun.gdxgame.idledemo.starter.ui.screen.menu;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.BaseIdleGame;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;




public abstract class BaseIdleMenuScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> 
        extends BaseHundunScreen<T_GAME, T_SAVE> {


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
    LanguageSwitchBoard<T_GAME, T_SAVE> languageSwitchBoardVM;

    public BaseIdleMenuScreen(T_GAME game,
            InputListener buttonContinueGameInputListener, 
            InputListener buttonNewGameInputListener) {
        super(game, game.getSharedViewport());
        
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        

    }

    private void initScene2d() {
        List<String> memuScreenTexts = game.getIdleGameplayExport().getGameDictionary().getMenuScreenTexts(game.getIdleGameplayExport().getLanguage());
        this.titleLabel = new Label(
                JavaFeatureForGwt.stringFormat("     %s     ", memuScreenTexts.get(0)), 
                game.getMainSkin());
        titleLabel.setFontScale(1.5f);
        this.backImage = new Image(game.getTextureManager().getMenuTexture());
        backImage.setFillParent(true);

        buttonContinueGame = new TextButton(memuScreenTexts.get(2), game.getMainSkin());
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = new TextButton(memuScreenTexts.get(1), game.getMainSkin());
        buttonNewGame.addListener(buttonNewGameInputListener);
        
        
        backUiStage.clear();
        uiRootTable.clear();
        
        backUiStage.addActor(backImage);

        uiRootTable.add(titleLabel)
                .row();
        
        if (game.getSaveHandler().hasContinuedGameplaySave()) {
            uiRootTable.add(buttonContinueGame)
                .height(BUTTON_BIG_HEIGHT)
                .fillY()
                .padTop(10)
                .row();
        }
        
        uiRootTable.add(buttonNewGame)
            .height(game.getSaveHandler().hasContinuedGameplaySave() ? BUTTON_SMALL_HEIGHT : BUTTON_BIG_HEIGHT)
            .fillY()
            .padTop(10)
            .row();

        this.languageSwitchBoardVM = new LanguageSwitchBoard<>(this,
                Language.values(),
                game.getIdleGameplayExport().getLanguage(),
                memuScreenTexts.get(3),
                memuScreenTexts.get(4),
                it -> game.getIdleGameplayExport().setLanguage(it)
        );
        uiRootTable.add(languageSwitchBoardVM)
                .padTop(10);
        
        if (game.debugMode) {
            uiRootTable.debugAll();
        }
        
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