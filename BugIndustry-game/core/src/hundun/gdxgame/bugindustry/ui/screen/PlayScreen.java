package hundun.gdxgame.bugindustry.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.bugindustry.ui.entity.BugGameEntityFactory;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idlestarter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.idlestarter.ui.component.BackgroundImageBox;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.GameImageDrawer;
import hundun.gdxgame.idlestarter.ui.component.PopupInfoBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idlestarter.ui.component.board.construction.impl.scroll.ScrollConstructionControlBoard;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class PlayScreen extends BasePlayScreen<BugIndustryGame> {

    public PlayScreen(BugIndustryGame game) {
        super(game, GameArea.BEE_FARM, new PlayScreenLayoutConst(game.LOGIC_WIDTH, game.LOGIC_HEIGHT));
    }
    
    @Override
    protected void lazyInitLogicContext() {
        BugGameEntityFactory bugGameEntityFactory = new BugGameEntityFactory(this.layoutConst, this);
        gameImageDrawer = new GameImageDrawer<>(this, bugGameEntityFactory);
        
        logicFrameListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }

    @Override
    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<BugIndustryGame>(this);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        uiRootTable.add(storageInfoTable).height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT).row();
        
        gameAreaControlBoard = new GameAreaControlBoard<BugIndustryGame>(this, GameArea.values);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();
        
        constructionControlBoard = new ScrollConstructionControlBoard(this);
        uiRootTable.add(constructionControlBoard).height(layoutConst.CONSTRUCION_BOARD_BORDER_HEIGHT);
        
        uiRootTable.debugCell();
    }
    
    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<BugIndustryGame>(this);
        backUiStage.addActor(backgroundImageBox);
        
        
        popUpInfoBoard = new PopupInfoBoard<BugIndustryGame>(this);
        popupRootTable.add(popUpInfoBoard).bottom().expand().row();
        // empty image for hold the space
        popupRootTable.add(new Image()).height(game.LOGIC_HEIGHT / 4);
        
        achievementMaskBoard = new AchievementMaskBoard<BugIndustryGame>(this);
        popupUiStage.addActor(achievementMaskBoard);
    }


    @Override
    public void dispose() {


    }


}
