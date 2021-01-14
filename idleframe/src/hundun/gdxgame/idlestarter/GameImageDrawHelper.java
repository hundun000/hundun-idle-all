package hundun.gdxgame.idlestarter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IAmountChangeEventListener;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.entity.IGameEntityFactory;
import hundun.gdxgame.idleframe.model.manager.GameEntityManager;

/**
 * @author hundun
 * Created on 2021/11/16
 * @param <T_GAME>
 */
public class GameImageDrawHelper<T_GAME extends BaseIdleGame> implements IAmountChangeEventListener {
    
    BasePlayScreen<T_GAME> parent;
    IGameEntityFactory gameEntityFactory;
    
    
    //private Queue<GameEntity> beeEntities = new ConcurrentLinkedQueue<>();
    
    public GameImageDrawHelper(BasePlayScreen<T_GAME> parent, Camera camera, IGameEntityFactory gameEntityFactory) {
        this.parent = parent;
        
        this.gameEntityFactory = gameEntityFactory;
        parent.game.getEventManager().registerListener(this);
    }
    
    
    public void drawAll() {
        parent.game.getBatch().begin();
        GameEntityManager manager = parent.game.getModelContext().getGameEntityManager();
        
        manager.allEntityMoveForFrame();
        
        for (var entry : manager.getGameEntitiesOfConstructionIds().entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
            });
        }
        
        for (var entry : manager.getGameEntitiesOfResourceIds().entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
            });

        }
        
        parent.game.getBatch().end();
    }


    
    @Override
    public void onResourceChange(boolean fromLoad) {
        GameEntityManager manager = parent.game.getModelContext().getGameEntityManager();
        String gameArea = parent.getArea();
        
        manager.areaEntityCheckAmount(gameArea, gameEntityFactory);
        
    }


}
