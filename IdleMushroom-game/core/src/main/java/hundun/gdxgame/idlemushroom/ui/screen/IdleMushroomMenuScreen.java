package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.ProxyManager.ProxyConfig;
import hundun.gdxgame.idlemushroom.logic.id.IdleMushroomScreenId;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hundun
 * Created on 2023/02/16
 */
public class IdleMushroomMenuScreen extends BaseHundunScreen<IdleMushroomGame, RootSaveData> {
    @Getter
    IdleMushroomGame idleMushroomGame;


    final ChangeListener buttonContinueGameInputListener;
    final ChangeListener buttonNewGameInputListener;

    Image backImage;
    TextButton buttonContinueGame;
    TextButton buttonNewGame;
    TextButton buttonIntoSettingScreen;
    LanguageSwitchBoard languageSwitchBoardVM;

    public IdleMushroomMenuScreen(IdleMushroomGame game) {
        super(game, game.getSharedViewport());
        this.idleMushroomGame = game;
        this.buttonContinueGameInputListener = new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getProxyManager().lazyInit(ProxyConfig.releaseInstance());
                game.getSaveHandler().gameplayLoadOrStarter(true);
                game.getScreenManager().pushScreen(IdleMushroomScreenId.SCREEN_MAIN, null);
                game.getAudioPlayManager().intoScreen(IdleMushroomScreenId.SCREEN_MAIN);
                game.getIdleGameplayExport().getGameplayContext().getEventManager().notifyGameStart();
            }
        };
        this.buttonNewGameInputListener = new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getProxyManager().lazyInit(ProxyConfig.releaseInstance());
                game.getSaveHandler().gameplayLoadOrStarter(false);
                game.getScreenManager().pushScreen(IdleMushroomScreenId.SCREEN_MAIN, null);
                game.getAudioPlayManager().intoScreen(IdleMushroomScreenId.SCREEN_MAIN);
                game.getIdleGameplayExport().getGameplayContext().getEventManager().notifyGameStart();
            }
        };
    }

    private void initScene2d() {
        List<String> memuScreenTexts = idleMushroomGame.getIdleMushroomGameDictionary().getMenuScreenTexts(game.getIdleGameplayExport().getLanguage());
        this.backImage = new Image(game.getTextureManager().getMenuTexture());
        backImage.setFillParent(true);

        buttonContinueGame = new TextButton(memuScreenTexts.get(2), game.getMainSkin());
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = new TextButton(memuScreenTexts.get(1), game.getMainSkin());
        buttonNewGame.addListener(buttonNewGameInputListener);



        backUiStage.clear();
        uiRootTable.clear();

        backUiStage.addActor(backImage);

        uiRootTable.add(new Image(game.getTextureManager().getTitleImage()))
                .row();
        IdleMushroomPlayScreenLayoutConst layoutConst = this.getIdleMushroomGame().getIdleMushroomPlayScreenLayoutConst();

        if (game.getSaveHandler().hasContinuedGameplaySave()) {
            uiRootTable.add(buttonContinueGame)
                    .width(layoutConst.menuButtonWidth)
                    .height(layoutConst.menuButtonHeight * 1.5f)
                    .padTop(10)
                    .row();
        }

        uiRootTable.add(buttonNewGame)
                .width(layoutConst.menuButtonWidth)
                .height(layoutConst.menuButtonHeight)
                .padTop(10)
                .row();

        if (game.debugMode) {
            Button buttonProxyNewGame = new TextButton(memuScreenTexts.get(1) + " proxy", game.getMainSkin());
            ChangeListener buttonProxyNewGameInputListener = new ChangeListener(){
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.getProxyManager().lazyInit(ProxyConfig.devInstance());
                    game.getSaveHandler().gameplayLoadOrStarter(false);
                    game.getScreenManager().pushScreen(IdleMushroomScreenId.SCREEN_MAIN, null);
                    game.getAudioPlayManager().intoScreen(IdleMushroomScreenId.SCREEN_MAIN);
                    game.getIdleGameplayExport().getGameplayContext().getEventManager().notifyGameStart();
                }
            };
            buttonProxyNewGame.addListener(buttonProxyNewGameInputListener);
            uiRootTable.add(buttonProxyNewGame)
                    .width(layoutConst.menuButtonWidth)
                    .height(layoutConst.menuButtonHeight)
                    .padTop(10)
                    .row();

        }

        this.languageSwitchBoardVM = new LanguageSwitchBoard(
                this,
                Language.values(),
                game.getIdleGameplayExport().getLanguage(),
                memuScreenTexts.get(3),
                memuScreenTexts.get(4),
                it -> {
                    game.getIdleGameplayExport().setLanguage(it);
                    game.getSaveHandler().gameSaveCurrent();
                }
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

        game.getLogicFrameHelper().setLogicFramePause(true);
        initScene2d();
        Gdx.app.log(this.getClass().getSimpleName(), "show done");
    }

    @Override
    public void dispose() {}

    @Override
    protected void create() {

    }

    @Override
    public void onLogicFrame() {

    }

    public static class LanguageSwitchBoard extends Table {

        IdleMushroomMenuScreen parent;
        @Getter
        SelectBox<String> selectBox;
        Label restartHintLabel;
        private final Map<Language, String> languageShowNameMap;

        LanguageSwitchBoard(IdleMushroomMenuScreen parent,
                            Language[] values,
                            Language current,
                            String startText,
                            String hintText,
                            Consumer<Language> onSelect
        ) {
            this.parent = parent;
            this.setBackground(parent.getIdleMushroomGame().getTextureManager().getTableType1Drawable());
            this.languageShowNameMap = parent.getGame().getIdleMushroomGameDictionary().getLanguageShowNameMap();
            this.add(new Label(startText, parent.getGame().getMainSkin()));


            this.selectBox = new SelectBox<>(parent.getGame().getMainSkin());
            selectBox.setItems(Stream.of(values)
                    .map(it -> languageShowNameMap.get(it))
                    .collect(Collectors.toList())
                    .toArray(new String[] {})
            );
            selectBox.setSelected(languageShowNameMap.get(current));
            selectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    restartHintLabel.setVisible(true);
                    Language language = languageShowNameMap.entrySet().stream()
                            .filter(x -> x.getValue().equals(selectBox.getSelected()))
                            .findFirst()
                            .get().getKey();
                    onSelect.accept(language);
                }
            });
            this.add(selectBox).row();

            this.restartHintLabel = new Label(hintText, parent.getGame().getMainSkin());
            restartHintLabel.setVisible(false);
            this.add(restartHintLabel);


        }



    }
}
