package hundun.gdxgame.idledemo.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.BaseIdleGame;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BaseIdleScreen<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE>
        extends BaseHundunScreen<T_GAME, T_SAVE> {

    public static final int LOGIC_FRAME_PER_SECOND = 30;

    @Getter
    protected final PlayScreenLayoutConst layoutConst;

    protected boolean hidden;
    // ====== need child lazy-init start ======


    // ----- not ui ------


    protected List<ILogicFrameListener> logicFrameListeners;
    protected List<IGameAreaChangeListener> gameAreaChangeListeners;

    @Getter
    protected final String screenId;

    public BaseIdleScreen(T_GAME game, String screenId, PlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.screenId = screenId;
        this.layoutConst = layoutConst;
        this.logicFrameHelper = new LogicFrameHelper(LOGIC_FRAME_PER_SECOND);
        this.logicFrameListeners = new ArrayList<>();
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

    @Override
    public void dispose() {
    }




    protected abstract void lazyInitLogicContext();
    


    protected abstract void lazyInitUiRootContext();

    protected abstract void lazyInitBackUiAndPopupUiContent();



    @Override
    protected void onLogicFrame() {
        super.onLogicFrame();

        for (ILogicFrameListener logicFrameListener : logicFrameListeners) {
            logicFrameListener.onLogicFrame();
        }
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(provideDefaultInputProcessor());

        updateUIForShow();

        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(game.getLastScreenId(), this.getScreenId());
        }

        this.hidden = false;
        Gdx.app.log(this.getClass().getSimpleName(), "show done");
    }

    @Override
    public void hide() {
        super.hide();

        this.hidden = true;
    }

    protected abstract void updateUIForShow();

    protected abstract InputProcessor provideDefaultInputProcessor();


}
