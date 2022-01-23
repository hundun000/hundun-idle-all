package hundun.gdxgame.bugindustry.ui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.entity.IGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.RandomMoveEntity;
import hundun.gdxgame.idlestarter.model.entity.StarterGameEntityFactoryHelper;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory implements IGameEntityFactory {
    public int FLY_MIN_X;
    public int FLY_MAX_X;
    public int FLY_MIN_Y;
    public int FLY_MAX_Y;
    
    public int TREE_MIN_X;
    public int TREE_MAX_X;
    public int TREE_MIN_Y;
    public int TREE_MAX_Y;
    
    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 5;
    
    
    BugIndustryGame game;
    
    StarterGameEntityFactoryHelper strategy;
    
    public GameEntityFactory(BugIndustryGame game, BasePlayScreen<BugIndustryGame> parent, StarterGameEntityFactoryHelper strategy) {
        this.game = game;
        this.strategy = strategy;
        
        FLY_MAX_X = Gdx.graphics.getWidth();
        FLY_MIN_X = 0;
        FLY_MAX_Y = Gdx.graphics.getHeight() - (parent.getLayoutConst().STORAGE_BOARD_BORDER_HEIGHT);
        FLY_MIN_Y = FLY_MAX_Y - 200;
        
        TREE_MAX_X = Gdx.graphics.getWidth();
        TREE_MIN_X = 0;
        TREE_MAX_Y = Gdx.graphics.getHeight() - (parent.getLayoutConst().STORAGE_BOARD_BORDER_HEIGHT);
        TREE_MIN_Y = TREE_MAX_Y - 100;
        //this.beehiveTexture = new Texture(Gdx.files.internal("beehive.png"));
        Gdx.app.log(this.getClass().getSimpleName(), "TREE_MAX_Y = " + TREE_MAX_Y + ", TREE_MIN_Y = " + TREE_MIN_Y);
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case ConstructionId.WOOD_SELL_HOUSE: 
            case ConstructionId.SMALL_BEEHIVE:
                return strategy.columnStableEntity(id, index, 0);
            case ConstructionId.WOOD_BOARD_SELL_HOUSE:
            case ConstructionId.MID_BEEHIVE:
                return strategy.columnStableEntity(id, index, 1);
            case ConstructionId.BEE_SELL_HOUSE:
            case ConstructionId.BIG_BEEHIVE:
                return strategy.columnStableEntity(id, index, 2); 
            case ConstructionId.HONEY_SELL_HOUSE:
            case ConstructionId.QUEEN_BEEHIVE:
                return strategy.columnStableEntity(id, index, 3);
            case ConstructionId.BEEWAX_SELL_HOUSE:
                return strategy.columnStableEntity(id, index, 4);
            case ConstructionId.WOOD_KEEPING:
                return strategy.randomPositionStableEntity(id, TREE_MIN_X, TREE_MAX_X, TREE_MIN_Y, TREE_MAX_Y);
            default:
                // no need GameEntity
                return null;
        }
        
    }




    @Override
    public int calculateResourceDrawNum(String resourceId, long logicAmount) {
        return (int) Math.min(RESOURCE_MAX_DRAW_NUM, logicAmount);
    }

    @Override
    public int calculateConstructionDrawNum(String constructionid, long logicAmount, int max) {
        return (int) Math.min(max, logicAmount);
    }

    @Override
    public GameEntity newResourceEntity(String resourceId) {
        switch (resourceId) {
            case ResourceType.BEE:
                return strategy.randomMove(resourceId, FLY_MIN_X, FLY_MAX_X, FLY_MIN_Y, FLY_MAX_Y, FLY_UNION_SPEED);
            default:
                return null;
        }
    }

    
    
}
