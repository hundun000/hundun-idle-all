package hundun.gdxgame.idlestarter.ui.component;

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
import hundun.gdxgame.idleframe.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.manager.GameEntityManager;
import hundun.gdxgame.idlestarter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/16
 * @param <T_GAME>
 */
public class GameImageDrawer<T_GAME extends BaseIdleGame> implements IAmountChangeEventListener {
    
    BasePlayScreen<T_GAME> parent;
    BaseGameEntityFactory gameEntityFactory;
    
    
    //private Queue<GameEntity> beeEntities = new ConcurrentLinkedQueue<>();
    
    public GameImageDrawer(BasePlayScreen<T_GAME> parent, BaseGameEntityFactory gameEntityFactory) {
        this.parent = parent;
        
        this.gameEntityFactory = gameEntityFactory;
        parent.game.getEventManager().registerListener(this);
    }
    
    
    public void drawAll() {
        parent.game.getBatch().begin();
        GameEntityManager manager = parent.game.getModelContext().getGameEntityManager();
        
        manager.allEntityMoveForFrame();
        String gameArea = parent.getArea();
        
        for (var entry : manager.getGameEntitiesOfConstructionIds().entrySet()) {
            boolean needDraw = manager.getAreaShowEntityConstructionIds().containsKey(gameArea) 
                    && manager.getAreaShowEntityConstructionIds().get(gameArea).contains(entry.getKey());
            if (!needDraw) {
                continue;
            }
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                parent.game.getBatch().draw(entity.getTexture(), entity.getX(), entity.getY(), (entity.isTextureFlipX() ? -1 : 1) * entity.getDrawWidth(), entity.getDrawHeight());
            });
        }
        
        for (var entry : manager.getGameEntitiesOfResourceIds().entrySet()) {
            boolean needDraw = manager.getAreaShowEntityResourceIds().containsKey(gameArea) 
                    && manager.getAreaShowEntityResourceIds().get(gameArea).contains(entry.getKey());
            if (!needDraw) {
                continue;
            }
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
