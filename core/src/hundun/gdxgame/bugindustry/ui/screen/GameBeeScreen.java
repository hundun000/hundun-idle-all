package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.BugIndustryGame;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class GameBeeScreen extends BaseScreen {

    Pixmap tableBackgroundPixmap;
    TextureRegionDrawable tableBackgroundDrawable;
    
    Table storageInfoTable;
    Table upgradeTable;
    Table backTable;
    
    public GameBeeScreen(BugIndustryGame game) {
        super(game);
        initScene2d();
    }
    
    
    private void initScene2d() {
        
        tableBackgroundPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        tableBackgroundPixmap.setColor(0.8f, 0.8f, 0.8f, 1.0f);
        tableBackgroundPixmap.fill();
        tableBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(tableBackgroundPixmap)));
        
        
        storageInfoTable = new Table();
        storageInfoTable.setBackground(tableBackgroundDrawable);
        storageInfoTable.setBounds(10, Gdx.graphics.getHeight() - 10 - 100, Gdx.graphics.getWidth() - 20, 100);
        stage.addActor(storageInfoTable);
        
        upgradeTable = new Table();
        upgradeTable.setBackground(tableBackgroundDrawable);
        upgradeTable.setBounds(10, 10, Gdx.graphics.getWidth() - 20, 100);
        stage.addActor(upgradeTable);
        
        upgradeTable = new Table();
        upgradeTable.setBackground(tableBackgroundDrawable);
        upgradeTable.setBounds(10, 10, Gdx.graphics.getWidth() - 20, 100);
        stage.addActor(upgradeTable);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        tableBackgroundPixmap.dispose();
        tableBackgroundPixmap.dispose();
        
    }

}
