package hundun.gdxgame.idleframe.model.entity;
/**
 * @author hundun
 * Created on 2021/12/04
 */
public interface IGameEntityFactory {
    int calculateResourceDrawNum(String resourceId, long logicAmount);
    int calculateConstructionDrawNum(String constructionid, long logicAmount, int maxDrawNum);
    GameEntity newResourceEntity(String resourceId);
    GameEntity newConstructionEntity(String constructionid, int index);
}
