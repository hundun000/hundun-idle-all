package hundun.gdxgame.idleshare.core.starter.ui.component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.framework.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleshare.core.framework.model.entity.GameEntity;
import hundun.gdxgame.idleshare.core.framework.model.manager.GameEntityManager;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;

/**
 * @author hundun
 * Created on 2021/11/16
 * @param <T_GAME>
 */
public class GameImageDrawer<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> implements IOneFrameResourceChangeListener {

    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;
    BaseGameEntityFactory gameEntityFactory;


    public GameImageDrawer(BaseIdlePlayScreen<T_GAME, T_SAVE> parent) {
        this.parent = parent;

        parent.getGame().getIdleGameplayExport().eventManagerRegisterListener(this);
    }


    public void allEntitiesMoveForFrameAndDraw() {
        parent.getGame().getBatch().begin();
        GameEntityManager manager = parent.getGame().getManagerContext().getGameEntityManager();

        manager.allEntityMoveForFrame();
        String gameArea = parent.getArea();

        List<String> needDrawConstructionIds = manager.getAreaShowEntityByOwnAmountConstructionIds().get(gameArea);
        if (needDrawConstructionIds != null) {
            for (String id : needDrawConstructionIds) {
                List<GameEntity> queue = manager.getGameEntitiesOfConstructionIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.getGame().getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        List<String> needDrawByOwnAmountResourceIds = manager.getAreaShowEntityByOwnAmountResourceIds().get(gameArea);
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

        List<String> needDrawByChangeAmountResourceIds = manager.getAreaShowEntityByChangeAmountResourceIds().get(gameArea);
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
    public void onResourceChange(Map<String, Long> changeMap) {
        GameEntityManager manager = parent.getGame().getManagerContext().getGameEntityManager();
        String gameArea = parent.getArea();

        manager.areaEntityCheckByOwnAmount(gameArea, gameEntityFactory);
        manager.areaEntityCheckByChangeAmount(gameArea, gameEntityFactory, changeMap);
    }


    public void lazyInit(BaseGameEntityFactory gameEntityFactory) {
        this.gameEntityFactory = gameEntityFactory;
    }


}
