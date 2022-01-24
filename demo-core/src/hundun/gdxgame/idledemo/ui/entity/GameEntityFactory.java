package hundun.gdxgame.idledemo.ui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.idledemo.BugIndustryGame;
import hundun.gdxgame.idledemo.logic.ConstructionId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.entity.RandomMoveEntity;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.idlestarter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory extends BaseGameEntityFactory {
    
    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 5;
    
    public GameEntityFactory(PlayScreenLayoutConst layoutConst, BasePlayScreen<BugIndustryGame> parent) {
        super(layoutConst, parent);
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case ConstructionId.COOKIE_CLICK_PROVIDER: 
                return this.columnStableConstructionEntity(id, index, 0);
            case ConstructionId.COOKIE_AUTO_PROVIDER:
                return this.columnStableConstructionEntity(id, index, 1);
            case ConstructionId.COOKIE_SELLER:
                return this.columnStableConstructionEntity(id, index, 2); 
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
            case ResourceType.COOKIE:
                return this.randomMoveResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MIN_Y, layoutConst.EXPECTED_DRAW_MAX_Y, FLY_UNION_SPEED);
            default:
                return null;
        }
    }


    
    
}
