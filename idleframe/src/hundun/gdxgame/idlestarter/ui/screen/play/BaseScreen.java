package hundun.gdxgame.idlestarter.ui.screen.play;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hundun.gdxgame.idleframe.BaseIdleGame;

/**
 * @author hundun
 * Created on 2021/11/02
 * @param <T_GAME>
 */
public abstract class BaseScreen<T_GAME extends BaseIdleGame> implements Screen {
    public final T_GAME game;
    protected final Stage uiStage;
    
    
    
    public BaseScreen(T_GAME game) {
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(game.LOGIC_WIDTH, game.LOGIC_HEIGHT);
        FitViewport viewport = new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, camera);
        this.uiStage = new Stage(viewport, game.getBatch());
    }
    
    
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    
    @Override
    public void resize(int width, int height) {
        this.uiStage.getViewport().update(width, height, true);
    }
}
