package hundun.gdxgame.idleshare.core.framework;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.idleshare.core.framework.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.entity.GameEntity;
import hundun.gdxgame.idleshare.core.framework.manager.GameEntityManager;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.event.EventManager.OneSecondResourceChangeEvent;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.StorageManager.ModifyResourceTag;

/**
 * @author hundun
 * Created on 2021/11/16
 * @param <T_GAME>
 */
public class GameImageDrawer<T_GAME extends BaseHundunGame<T_SAVE>, T_SAVE> implements IResourceChangeListener {

    BaseHundunScreen<T_GAME, T_SAVE> parent;
    IGameImageDrawerHolder holder;
    BaseGameEntityFactory gameEntityFactory;
    GameEntityManager manager;


    public interface IGameImageDrawerHolder {
        String getScreenId();

        long getResourceNumOrZero(String resourceId);

        int getConstructionWorkingLevelNumOrZero(String prototypeId);

        Sprite getResourceEntity(String resourceId);

        Sprite getConstructionEntity(String constructionId);
    }


    public GameImageDrawer(BaseHundunScreen<T_GAME, T_SAVE> parent, IGameImageDrawerHolder holder) {
        this.parent = parent;
        this.holder = holder;
        
    }


    public void allEntitiesMoveForFrameAndDraw() {
        parent.getGame().getBatch().begin();

        String gameArea = holder.getScreenId();
        List<String> needDrawConstructionIds = manager.getAreaEntityEffectConfigMap().get(gameArea).getOwnAmountConstructionPrototypeIds();
                manager.destroyNoNeedDrawConstructionIds(needDrawConstructionIds);
        manager.allEntityMoveForFrame();
        
        if (needDrawConstructionIds != null) {
            for (String id : needDrawConstructionIds) {
                List<GameEntity> queue = manager.getGameEntitiesOfConstructionPrototypeIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.getGame().getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        List<String> needDrawByOwnAmountResourceIds = manager.getAreaEntityEffectConfigMap().get(gameArea).getOwnAmountResourceIds();
        if (needDrawByOwnAmountResourceIds != null) {
            for (String id : needDrawByOwnAmountResourceIds) {
                List<GameEntity> queue = manager.getGameEntitiesOfResourceIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.getGame().getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        List<String> needDrawByChangeAmountResourceIds = manager.getAreaEntityEffectConfigMap().get(gameArea).getChangeAmountResourceIds();
        if (needDrawByChangeAmountResourceIds != null) {
            for (String id : needDrawByChangeAmountResourceIds) {
                List<GameEntity> queue = manager.getGameEntitiesOfResourceIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.getGame().getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        parent.getGame().getBatch().end();
    }


    @Override
    public void onResourceChange(OneSecondResourceChangeEvent event) {
        String gameArea = holder.getScreenId();

        manager.areaEntityCheckByOwnAmount(gameArea, gameEntityFactory);
        manager.areaEntityCheckByChangeAmount(gameArea, gameEntityFactory, event.getAllTagData().getSecondChangeMap());
    }


    public void lazyInit(BaseGameEntityFactory gameEntityFactory, GameEntityManager manager) {
        this.gameEntityFactory = gameEntityFactory;
        this.manager = manager;
    }


}
