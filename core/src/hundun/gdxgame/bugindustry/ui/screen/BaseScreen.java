package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import lombok.AllArgsConstructor;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@AllArgsConstructor
public abstract class BaseScreen implements Screen {
    protected final BugIndustryGame game;
    protected final OrthographicCamera camera;
    protected final Stage stage;
    
    public BaseScreen(BugIndustryGame game) {
        this.game = game;
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        var viewport = new StretchViewport(camera.viewportWidth, camera.viewportHeight, camera);
        this.stage = new Stage(viewport, game.getBatch());
    }
    
    
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
}
