package hundun.gdxgame.bugindustry.ui.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.ResourceType;

import hundun.gdxgame.idleframe.model.entity.GameEntity;
import hundun.gdxgame.idleframe.model.entity.IGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.RandomMoveEntity;
import hundun.gdxgame.idlestarter.ui.StarterPlayScreenLayoutConst;
import hundun.gdxgame.idlestarter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.idlestarter.ui.component.StorageInfoBoard;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory implements IGameEntityFactory {
    public float FLY_MIN_X;
    public float FLY_MAX_X;
    public float FLY_MIN_Y;
    public float FLY_MAX_Y;
    
    public int TREE_MIN_X;
    public int TREE_MAX_X;
    public int TREE_MIN_Y;
    public int TREE_MAX_Y;
    
    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 5;
    
    ResourceType type;
    int baseX;
    int baseY;
    //TextureRegion textureRegion;
    
    //private Texture beeTexture;
    //private Texture beehiveTexture;
    
    BugIndustryGame game;
    
    public GameEntityFactory(BugIndustryGame game) {
        this.game = game;
        
        FLY_MAX_X = Gdx.graphics.getWidth();
        FLY_MIN_X = BEE_WIDTH;
        FLY_MAX_Y = Gdx.graphics.getHeight() - (StarterPlayScreenLayoutConst.STORAGE_BOARD_BORDER_HEIGHT);
        FLY_MIN_Y = FLY_MAX_Y - 200;
        
        TREE_MAX_X = Gdx.graphics.getWidth() - scale * Construction_WIDTH;
        TREE_MIN_X = 0;
        TREE_MAX_Y = Gdx.graphics.getHeight() - (StarterPlayScreenLayoutConst.STORAGE_BOARD_BORDER_HEIGHT) - scale * Construction_HEIGHT;
        TREE_MIN_Y = TREE_MAX_Y - 100;
        //this.beehiveTexture = new Texture(Gdx.files.internal("beehive.png"));
        Gdx.app.log(this.getClass().getSimpleName(), "TREE_MAX_Y = " + TREE_MAX_Y + ", TREE_MIN_Y = " + TREE_MIN_Y);
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case ConstructionId.WOOD_SELL_HOUSE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 0 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        false,
                        id);
            case ConstructionId.SMALL_BEEHIVE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 0 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        true,
                        id);
            case ConstructionId.WOOD_BOARD_SELL_HOUSE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 1 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        false,
                        id); 
            case ConstructionId.MID_BEEHIVE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 1 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        true,
                        id); 
            case ConstructionId.BEE_SELL_HOUSE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 2 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        false,
                        id); 
            case ConstructionId.BIG_BEEHIVE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 2 * ConstructionEntity_BASE_X_PAD - ConstructionEntity_X_PAD * index,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        true,
                        id); 
            case ConstructionId.HONEY_SELL_HOUSE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 3 * ConstructionEntity_BASE_X_PAD,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        false,
                        id);
            case ConstructionId.QUEEN_BEEHIVE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 3 * ConstructionEntity_BASE_X_PAD,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        true,
                        id); 
            case ConstructionId.BEEWAX_SELL_HOUSE:
                return newConstructionEntity(
                        ConstructionEntity_BASE_X + 4 * ConstructionEntity_BASE_X_PAD,
                        ConstructionEntity_BASE_Y - ConstructionEntity_Y_PAD * index,
                        false,
                        id); 
            case ConstructionId.WOOD_KEEPING:
                int randX = (int) (TREE_MIN_X + Math.random() * (TREE_MAX_X - TREE_MIN_X));
                int randY = (int) (TREE_MIN_Y + Math.random() * (TREE_MAX_Y - TREE_MIN_Y));
                return newConstructionEntity(
                        randX,
                        randY,
                        true,
                        id);
            default:
                return null;
                //throw new UnsupportedOperationException("fail newConstructionEntity for id = " + id);
        }
        
    }
    static final int scale = 2;
    static final int BEE_WIDTH = 64;
    static final int BEE_HEIGHT = 64;
    static final int SELL_Construction_WIDTH = 48;
    static final int SELL_Construction_HEIGHT = 32;
    static final int Construction_WIDTH = 32;
    static final int Construction_HEIGHT = 64;
    static final int ConstructionEntity_BASE_X = 80;
    static final int ConstructionEntity_BASE_X_PAD = 90;
    static final int ConstructionEntity_BASE_Y = 250;
    static final int ConstructionEntity_X_PAD = 15;
    static final int ConstructionEntity_Y_PAD = 30;
    private GameEntity newConstructionEntity(int x, int y, boolean sizeType1, String constructionId) {
        return newConstructionEntity(x, y, 
                scale * (sizeType1 ? Construction_WIDTH : SELL_Construction_WIDTH), 
                scale * (sizeType1 ? SELL_Construction_HEIGHT : SELL_Construction_HEIGHT), 
                constructionId);
    }
    
    private GameEntity newConstructionEntity(int x, int y, int drawWidth, int drawHeight, String constructionId) {
        
        GameEntity entity = new GameEntity();
        entity.setTexture(new Sprite(game.getTextureManager().getConstructionTexture(constructionId)));
        entity.setX(x);
        entity.setY(y);
        entity.setDrawWidth(drawWidth);
        entity.setDrawHeight(drawHeight);
        entity.setRandomMove(false);
        return entity;
    }
    
    
    private GameEntity newBeeEntity() {
        GameEntity entity = new RandomMoveEntity(FLY_MAX_X, FLY_MAX_X, FLY_MAX_X, FLY_MAX_X, FLY_MAX_X);
        entity.setTexture(new Sprite(game.getTextureManager().getResourceIcon(ResourceType.BEE)));
        entity.setX((FLY_MAX_X - FLY_MIN_X) / 2);
        entity.setY((FLY_MAX_Y - FLY_MIN_Y) / 2);
        entity.setDrawWidth(BEE_WIDTH);
        entity.setDrawHeight(BEE_HEIGHT);
        entity.setRandomMove(true);
        entity.setRandomMoveCount(0);
        entity.checkRandomeMoveSpeedChange();
        return entity;
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
            case ResourceType.BEE:
                return newBeeEntity();
            default:
                return null;
        }
    }

    
    
}
