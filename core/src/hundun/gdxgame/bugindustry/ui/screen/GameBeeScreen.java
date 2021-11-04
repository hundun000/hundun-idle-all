package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.ui.other.GameAreaChangeButton;
import hundun.gdxgame.bugindustry.ui.other.StorageInfoBoard;
import hundun.gdxgame.bugindustry.ui.other.ConstructionInfoBorad;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class GameBeeScreen extends BaseScreen {

    public Pixmap tableBackgroundPixmap;
    public TextureRegionDrawable tableBackgroundDrawable;
    public Pixmap tableBackgroundPixmap2;
    public TextureRegionDrawable tableBackgroundDrawable2;
    
    StorageInfoBoard storageInfoTable;
    ConstructionInfoBorad constructionInfoBorad;
    Table backTable;
    @Setter
    @Getter
    GameArea area;
    
    public GameBeeScreen(BugIndustryGame game) {
        super(game);
        
    }
    

    
    private void initScene2d() {
        
        tableBackgroundPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        tableBackgroundPixmap.setColor(0.8f, 0.8f, 0.8f, 1.0f);
        tableBackgroundPixmap.fill();
        tableBackgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(tableBackgroundPixmap)));
        tableBackgroundPixmap2 = new Pixmap(1,1, Pixmap.Format.RGB565);
        tableBackgroundPixmap2.setColor(0.75f, 0.75f, 0.75f, 1.0f);
        tableBackgroundPixmap2.fill();
        tableBackgroundDrawable2 = new TextureRegionDrawable(new TextureRegion(new Texture(tableBackgroundPixmap2)));
        
        backTable = new Table();
        backTable.setBackground(tableBackgroundDrawable2);
        backTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        var areaChangeButtonL = GameAreaChangeButton.create(this, "GameAreaChangeButton_L.png", false);
        backTable.addActor(areaChangeButtonL);
        var areaChangeButtonR = GameAreaChangeButton.create(this, "GameAreaChangeButton_R.png", true);
        backTable.addActor(areaChangeButtonR);
        backTable.debugAll();
        stage.addActor(backTable);
        
        storageInfoTable = new StorageInfoBoard(this);
        stage.addActor(storageInfoTable);
        
        constructionInfoBorad = new ConstructionInfoBorad(this);
        stage.addActor(constructionInfoBorad);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        this.area = GameArea.FOREST;
        
        initScene2d();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        
        stage.act();
        stage.draw();
    }

    private void update() {
        storageInfoTable.onRenderFrame();
        boolean onLogicFrame = true;
        if (onLogicFrame) {
            constructionInfoBorad.onLogicFrame();
        }
        
    }


    @Override
    public void dispose() {
        tableBackgroundPixmap.dispose();
        
        tableBackgroundPixmap2.dispose();

    }

}
