package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;

import java.util.ArrayList;
import java.util.List;

public class BaseCellDetailBoardVM extends Table implements ILogicFrameListener {
    protected BaseDemoPlayScreen screen;



    protected List<BaseCellDetailNodeVM> contents = new ArrayList<>();

    public void onLogicFrame()
    {
        contents.forEach(it -> {
                it.subLogicFrame();
            });
    }

    public void postPrefabInitialization(BaseDemoPlayScreen parent)
    {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.screen = parent;
        this.setBackground(new TextureRegionDrawable(parent.getGame().getTextureManager().getDefaultBoardNinePatchTexture()));

    }
}
