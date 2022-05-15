package hundun.gdxgame.bugindustry.ui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.framework.model.entity.GameEntity;
import hundun.gdxgame.idleshare.framework.model.entity.RandomMoveEntity;
import hundun.gdxgame.idleshare.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idleshare.starter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idleshare.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idleshare.starter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class BugGameEntityFactory extends BaseGameEntityFactory {
    
    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 5;
    
    public BugGameEntityFactory(PlayScreenLayoutConst layoutConst, BasePlayScreen<BugIndustryGame> parent) {
        super(layoutConst, parent);
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case ConstructionId.WOOD_SELL_HOUSE: 
            case ConstructionId.SMALL_BEEHIVE:
                return this.columnStableConstructionEntity(id, index, 0);
            case ConstructionId.WOOD_BOARD_SELL_HOUSE:
            case ConstructionId.MID_BEEHIVE:
                return this.columnStableConstructionEntity(id, index, 1);
            case ConstructionId.BEE_SELL_HOUSE:
            case ConstructionId.BIG_BEEHIVE:
                return this.columnStableConstructionEntity(id, index, 2); 
            case ConstructionId.HONEY_SELL_HOUSE:
            case ConstructionId.QUEEN_BEEHIVE:
                return this.columnStableConstructionEntity(id, index, 3);
            case ConstructionId.BEEWAX_SELL_HOUSE:
                return this.columnStableConstructionEntity(id, index, 4);
            case ConstructionId.WOOD_KEEPING:
                return this.randomPositionStableConstructionEntity(id, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MIN_Y, layoutConst.EXPECTED_DRAW_MAX_Y);
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
    public GameEntity newResourceEntity(String resourceId, int index) {
        switch (resourceId) {
            case ResourceType.BEE:
                return this.randomMoveResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MIN_Y, layoutConst.EXPECTED_DRAW_MAX_Y, FLY_UNION_SPEED);
            default:
                return null;
        }
    }


    
    
}
