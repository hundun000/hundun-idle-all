package hundun.gdxgame.idledemo.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.ScreenId;
import hundun.gdxgame.idledemo.ui.entity.GameEntityFactory;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.idlestarter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.GameImageDrawer;
import hundun.gdxgame.idlestarter.ui.component.PopupInfoBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idlestarter.ui.component.board.construction.impl.fixed.FixedConstructionControlBoard;
import hundun.gdxgame.idlestarter.ui.component.board.construction.impl.scroll.ScrollConstructionControlBoard;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class PlayScreen extends BasePlayScreen<IdleDemoGame> {

    public PlayScreen(IdleDemoGame game) {
        super(game, ScreenId.PLAY, GameArea.AREA_COOKIE, customLayoutConst(game));
    }
    
    private static PlayScreenLayoutConst customLayoutConst(IdleDemoGame game) {
        PlayScreenLayoutConst layoutConst = new PlayScreenLayoutConst(game.LOGIC_WIDTH, game.LOGIC_HEIGHT);
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
    
    @Override
    protected void lazyInitLogicContext() {
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer = new GameImageDrawer<>(this, gameEntityFactory);
        
        logicFrameListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }

    @Override
    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<IdleDemoGame>(this);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        uiRootTable.add(storageInfoTable).height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT).fill().row();
        
        gameAreaControlBoard = new GameAreaControlBoard<IdleDemoGame>(this, GameArea.values);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();
        // impl switchable
        constructionControlBoard = new FixedConstructionControlBoard(this);
        uiRootTable.add(constructionControlBoard).height(layoutConst.CONSTRUCION_BOARD_BORDER_HEIGHT).fill();
        
        if (game.debugMode) {
            uiRootTable.debugCell();
        }
    }
    
    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<IdleDemoGame>(this);
        backUiStage.addActor(backgroundImageBox);
        
        
        popUpInfoBoard = new PopupInfoBoard<IdleDemoGame>(this);
        popupRootTable.add(popUpInfoBoard).bottom().expand().row();
        // empty image for hold the space
        popupRootTable.add(new Image()).height(game.LOGIC_HEIGHT / 4);
        
        achievementMaskBoard = new AchievementMaskBoard<IdleDemoGame>(this);
        popupUiStage.addActor(achievementMaskBoard);
    }


    @Override
    public void dispose() {


    }


}
