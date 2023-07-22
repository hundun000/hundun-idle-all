package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.RootSaveData;
import hundun.gdxgame.idledemo.ui.entity.GameEntityFactory;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class DemoPlayScreen extends BaseIdlePlayScreen<DemoIdleGame, RootSaveData> {

    public DemoPlayScreen(DemoIdleGame game) {
        super(game, GameArea.AREA_COOKIE, customLayoutConst(game));
    }
    
    private static PlayScreenLayoutConst customLayoutConst(DemoIdleGame game) {
        PlayScreenLayoutConst layoutConst = new PlayScreenLayoutConst(game.getWidth(), game.getHeight());
        NinePatch ninePatch = new NinePatch(game.getTextureManager().getDefaultBoardNinePatchTexture(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize()
                );
        layoutConst.simpleBoardBackground = new NinePatchDrawable(ninePatch);
        layoutConst.simpleBoardBackgroundMiddle = new TextureRegionDrawable(game.getTextureManager().getDefaultBoardNinePatchMiddle());
        return layoutConst;
    }
    



    protected void lazyInitLogicContext() {
        super.lazyInitLogicContext();
        
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer.lazyInit(gameEntityFactory);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        gameAreaControlBoard.lazyInit(GameArea.values);
    }




}
