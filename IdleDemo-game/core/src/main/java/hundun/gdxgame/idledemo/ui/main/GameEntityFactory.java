package hundun.gdxgame.idledemo.ui.main;

import hundun.gdxgame.idledemo.logic.DemoConstructionPrototypeId;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.ui.screen.MainPlayScreen;
import hundun.gdxgame.idleshare.core.framework.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory extends BaseGameEntityFactory {
    
    private static final int HIDEN_FRAME_RANGE = 5;

    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 15;
    IdleDemoGameEntityFactoryLayoutConst layoutConst;
    @Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class IdleDemoGameEntityFactoryLayoutConst {
        public int EXPECTED_DRAW_MIN_X;
        public int EXPECTED_DRAW_MAX_X;
        public int EXPECTED_DRAW_MIN_Y;
        public int EXPECTED_DRAW_MAX_Y;
    }

    public GameEntityFactory(IdleDemoGameEntityFactoryLayoutConst layoutConst, MainPlayScreen parent) {
        super(parent);
        this.layoutConst = layoutConst;
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case DemoConstructionPrototypeId.COOKIE_SIMPLE_AUTO_PROVIDER:
                return this.rowStableConstructionEntity(id, index, 1);
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
            case ResourceType.COOKIE:
                return this.failingResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MAX_Y, layoutConst.EXPECTED_DRAW_MIN_Y, FLY_UNION_SPEED, FLY_UNION_SPEED * 0.2, HIDEN_FRAME_RANGE);
                //return this.randomMoveResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MIN_Y, layoutConst.EXPECTED_DRAW_MAX_Y, FLY_UNION_SPEED);
            default:
                return null;
        }
    }


    
    
}
