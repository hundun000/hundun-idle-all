package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCellDetailBoardVM extends Table implements ILogicFrameListener {
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
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

    }
}
