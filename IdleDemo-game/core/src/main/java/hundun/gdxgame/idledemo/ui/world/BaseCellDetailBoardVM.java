package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;

import java.util.ArrayList;
import java.util.List;

public class BaseCellDetailBoardVM extends Table implements ILogicFrameListener {
    protected BaseDemoPlayScreen parent;



    protected List<CellDetailInnerBoardVM> contents = new ArrayList<>();

    public void onLogicFrame()
    {
        contents.forEach(it -> {
                it.update();
            });
    }

    public void postPrefabInitialization(BaseDemoPlayScreen parent)
    {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        this.setBackground(new TextureRegionDrawable(parent.getGame().getTextureManager().getDefaultBoardNinePatchTexture()));

    }
}
