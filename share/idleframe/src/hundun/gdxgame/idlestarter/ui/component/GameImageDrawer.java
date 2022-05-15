package hundun.gdxgame.idlestarter.ui.component;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleframe.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.manager.GameEntityManager;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/16
 * @param <T_GAME>
 */
public class GameImageDrawer<T_GAME extends BaseIdleGame> implements IOneFrameResourceChangeListener {

    BasePlayScreen<T_GAME> parent;
    BaseGameEntityFactory gameEntityFactory;


    public GameImageDrawer(BasePlayScreen<T_GAME> parent, BaseGameEntityFactory gameEntityFactory) {
        this.parent = parent;

        this.gameEntityFactory = gameEntityFactory;
        parent.game.getEventManager().registerListener(this);
    }


    public void allEntitiesMoveForFrameAndDraw() {
        parent.game.getBatch().begin();
        GameEntityManager manager = parent.game.getModelContext().getGameEntityManager();

        manager.allEntityMoveForFrame();
        String gameArea = parent.getArea();

        List<String> needDrawConstructionIds = manager.getAreaShowEntityByOwnAmountConstructionIds().get(gameArea);
        if (needDrawConstructionIds != null) {
            for (String id : needDrawConstructionIds) {
                Queue<GameEntity> queue = manager.getGameEntitiesOfConstructionIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        List<String> needDrawByOwnAmountResourceIds = manager.getAreaShowEntityByOwnAmountConstructionIds().get(gameArea);
        if (needDrawByOwnAmountResourceIds != null) {
            for (String id : needDrawByOwnAmountResourceIds) {
                Queue<GameEntity> queue = manager.getGameEntitiesOfResourceIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        List<String> needDrawByChangeAmountResourceIds = manager.getAreaShowEntityByChangeAmountResourceIds().get(gameArea);
        if (needDrawByChangeAmountResourceIds != null) {
            for (String id : needDrawByChangeAmountResourceIds) {
                Queue<GameEntity> queue = manager.getGameEntitiesOfResourceIds().get(id);
                if (queue == null) {
                    continue;
                }
                queue.forEach(entity -> {
                    parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
                });
            }
        }

        parent.game.getBatch().end();
    }


    @Override
    public void onResourceChange(Map<String, Long> changeMap) {
        GameEntityManager manager = parent.game.getModelContext().getGameEntityManager();
        String gameArea = parent.getArea();

        manager.areaEntityCheckByOwnAmount(gameArea, gameEntityFactory);
        manager.areaEntityCheckByChangeAmount(gameArea, gameEntityFactory, changeMap);
    }


}
