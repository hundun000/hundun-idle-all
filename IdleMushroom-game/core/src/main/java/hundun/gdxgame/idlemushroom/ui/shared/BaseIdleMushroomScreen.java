package hundun.gdxgame.idlemushroom.ui.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomScreenContext.IdleMushroomPlayScreenLayoutConst;
import hundun.gdxgame.idlemushroom.ui.shared.wiki.SharedWikiPopupInfoBoard;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseIdleMushroomScreen extends BaseHundunScreen<IdleMushroomGame, RootSaveData>
        implements ISecondaryInfoBoardCallback<Object>
{
    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final IdleMushroomPlayScreenLayoutConst layoutConst;

    protected boolean hidden;
    protected List<IGameAreaChangeListener> gameAreaChangeListeners;

    @Getter
    protected final String screenId;
    protected boolean enableLogicFrameOnShow = true;
    protected StorageInfoBoard storageInfoTable;
    protected SharedWikiPopupInfoBoard secondaryInfoBoard;
    protected BuffInfoBoard buffInfoBoard;
    protected BackgroundImageBox backgroundImageBox;
    protected IdleMushroomGameAreaControlBoard gameAreaControlBoard;

    protected Table leftSideGroup;
    protected Table middleGroup;

    protected static int UI_ROOT_TABLE_COLSPAN_SIZE = 3;

    public static class BackgroundImageBox extends Container<Image> implements IGameAreaChangeListener{
        BaseIdleMushroomScreen parent;

        public BackgroundImageBox(BaseIdleMushroomScreen parent) {
            this.parent = parent;
            this.setFillParent(true);
            //this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }

        @Override
        public void onGameAreaChange(String last, String current) {
            Drawable image = new TextureRegionDrawable(parent.getGame().getTextureManager().getBackgroundTexture(current));
            this.setBackground(image);
        }

    }

    public BaseIdleMushroomScreen(IdleMushroomGame game, String screenId, IdleMushroomPlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.screenId = screenId;
        this.layoutConst = layoutConst;
        this.gameAreaChangeListeners = new ArrayList<>();
    }

    @Override
    protected void create() {

        lazyInitBackUiAndPopupUiContent();

        lazyInitUiRootContext();

        lazyInitLogicContext();

        if (game.debugMode) {
            uiRootTable.debugAll();
            popupRootTable.debugCell();
        }
    }


    protected void lazyInitLogicContext() {

        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(gameAreaControlBoard);

        gameAreaControlBoard.lazyInit(game.getControlBoardScreenIds());

        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(this);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(storageInfoTable);
        this.getGame().getIdleGameplayExport().getGameplayContext().getEventManager().registerListener(buffInfoBoard);
    }

    protected void lazyInitUiRootContext() {
        storageInfoTable = new StorageInfoBoard(this);
        uiRootTable.add(storageInfoTable)
                .height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT / 2.0f)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
                .row();
        buffInfoBoard = new BuffInfoBoard(this);
        uiRootTable.add(buffInfoBoard)
                .height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT / 2.0f)
                .fill()
                .colspan(UI_ROOT_TABLE_COLSPAN_SIZE)
                .row();


        leftSideGroup = new Table();
        uiRootTable.add(leftSideGroup)
                .growY();

        middleGroup = new Table();
        uiRootTable.add(middleGroup).grow();

        gameAreaControlBoard = new IdleMushroomGameAreaControlBoard(this);
        gameAreaControlBoard.top();
        uiRootTable.add(gameAreaControlBoard)
                .growY()
                .row();
    }

    protected void lazyInitBackUiAndPopupUiContent() {

        this.secondaryInfoBoard = new SharedWikiPopupInfoBoard(this.getGame());
        popupRootTable.add(secondaryInfoBoard).center().expand();

        this.backgroundImageBox = new BackgroundImageBox(this);
        backUiStage.addActor(backgroundImageBox);

    }

    @Override
    public void dispose() {
    }


    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(provideDefaultInputProcessor());

        updateUIForShow();

        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(game.getLastScreenId(), this.getScreenId());
        }
        game.getLogicFrameHelper().setLogicFramePause(!enableLogicFrameOnShow);

        this.hidden = false;
        Gdx.app.log(this.getClass().getSimpleName(), "show done");
    }

    @Override
    public void hide() {
        super.hide();

        this.hidden = true;
    }

    protected void updateUIForShow() {
        buffInfoBoard.updateViewData(new HashMap<>(0));
    };

    protected abstract InputProcessor provideDefaultInputProcessor();

    @Override
    public void showAndUpdateGuideInfo(Object model) {
        secondaryInfoBoard.setVisible(true);
        secondaryInfoBoard.update(model);
    }

    @Override
    public void hideAndCleanGuideInfo() {
        secondaryInfoBoard.setVisible(false);
        //popUpInfoBoard.setText("GUIDE_TEXT");
    }
}
