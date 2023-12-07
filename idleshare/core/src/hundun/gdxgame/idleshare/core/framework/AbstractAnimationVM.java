package hundun.gdxgame.idleshare.core.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.corelib.base.BaseHundunGame;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public abstract class AbstractAnimationVM<T_GAME extends BaseHundunGame<T_SAVE>, T_SAVE, T_CALL_ARG> extends Table {
    @Null
    private final IAnimationCallback callback;
    
    protected final T_GAME game;
    
    @Getter
    private boolean runningState;
    @Setter(value = AccessLevel.PROTECTED)
    private Animation<Drawable> animation;
    private float stateTime;
    
    public AbstractAnimationVM(T_GAME game, @Null IAnimationCallback callback) {
        this.game = game;
        this.callback = callback;
    }
    
    public abstract void callShow(T_CALL_ARG arg);
    
    public void resetFrame(boolean runningState) {
        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        this.runningState = runningState;
        stateTime = 0f; 
        Drawable currentFrame = animation.getKeyFrame(stateTime);
        this.setBackground(currentFrame);
    }
    
    public void updateFrame(float delta, SpriteBatch spriteBatch) {
        stateTime += delta; // Accumulate elapsed animation time

        if (!isRunningState()) {
            return;
        }

        if (!animation.isAnimationFinished(stateTime)) {
            Drawable currentFrame = animation.getKeyFrame(stateTime);
            float rate = 1.0f * animation.getKeyFrameIndex(stateTime) / animation.getKeyFrames().length;
            this.setBackground(currentFrame);
            //this.setScale(1.0f + 1.0f * rate);
        } else {
            resetFrame(false);
            if (callback != null) {
                callback.onAnimationDone();
            }

        }

    }
    
    public static Animation<Drawable> animationFactory(TextureAtlas atlas, String id, float frameDuration, PlayMode playMode) {
        Array<AtlasRegion> regionArray = atlas.findRegions(id);
        Array<Drawable> drawableArray = new Array<>(true, regionArray.size, Drawable.class);
        for (int i = 0; i < regionArray.size; i++) {
            drawableArray.add(new TextureRegionDrawable(regionArray.get(i)));
        }
        return new Animation<Drawable>(frameDuration, drawableArray, playMode);
    }

    public static Animation<Drawable> animationFactory(TextureRegion[][] regions, float frameDuration, PlayMode playMode) {

        Array<Drawable> drawableArray = new Array<>(Drawable.class);
        for (TextureRegion[] subRegions : regions) {
            for (TextureRegion subRegion : subRegions) {
                drawableArray.add(new TextureRegionDrawable(subRegion));
            }
        }
        return new Animation<Drawable>(frameDuration, drawableArray, playMode);
    }
    
    public static Animation<Drawable> animationFactoryBySumTime(
            TextureAtlas atlas, String id, float second, PlayMode playMode
    ) {
        Array<AtlasRegion> regionArray = atlas.findRegions(id);
        Array<Drawable> drawableArray = new Array<>(true, regionArray.size, Drawable.class);
        for (int i = 0; i < regionArray.size; i++) {
            drawableArray.add(new TextureRegionDrawable(regionArray.get(i)));
        }
        float frameDuration = second / regionArray.size;
        return new Animation<Drawable>(frameDuration, drawableArray, playMode);
    }
    
    public static Animation<Drawable> animationFactory(float frameDuration, Texture sheet, int FRAME_COLS, int FRAME_ROWS) {
        
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / FRAME_COLS,
                sheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        Array<Drawable> drawableArray = new Array<>(true, FRAME_COLS * FRAME_ROWS, Drawable.class);
        
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                drawableArray.add(new TextureRegionDrawable(tmp[i][j]));
            }
        }
        return new Animation<Drawable>(frameDuration, drawableArray, PlayMode.NORMAL);
    }
    
    public static interface IAnimationCallback {
        void onAnimationDone();
    }
}
