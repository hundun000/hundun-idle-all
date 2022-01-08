package hundun.gdxgame.idleframe.model.entity;
/**
 * @author hundun
 * Created on 2021/12/04
 */
public interface IGameEntityFactory {
    public int calculateResourceDrawNum(String resourceId, long logicAmount);
    public int calculateConstructionDrawNum(String constructionid, long logicAmount, int maxDrawNum);
    public GameEntity newResourceEntity(String resourceId);
    public GameEntity newConstructionEntity(String constructionid, int index);
}
