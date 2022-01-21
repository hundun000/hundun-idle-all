package hundun.gdxgame.idlestarter.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.listener.IAchievementUnlockListener;
import hundun.gdxgame.idleframe.listener.IGameAreaChangeListener;
import hundun.gdxgame.idleframe.listener.ILogicFrameListener;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;

import hundun.gdxgame.idlestarter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.idlestarter.ui.component.PopupInfoBoard;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BasePlayScreen<T_GAME extends BaseIdleGame> 
        extends BaseScreen<T_GAME> 
        implements IAchievementUnlockListener {

    private static final float LOGIC_FRAME_LENGTH = 1 / 30f; 
    private int clockCount = 0;
    private float logicFramAccumulator;
    @Setter
    @Getter
    private boolean logicFramePause;
    @Getter
    private String area;
    
    protected AchievementMaskBoard<T_GAME> achievementMaskBoard;
    protected PopupInfoBoard<T_GAME> popUpInfoBoard;
    
    protected List<ILogicFrameListener> logicFrameListeners = new ArrayList<>();
    protected List<IGameAreaChangeListener> gameAreaChangeListeners = new ArrayList<>();
    
    protected Stage popupUiStage;
    protected Stage backUiStage;
    
    public BasePlayScreen(T_GAME game) {
        super(game);
        game.getEventManager().registerListener(this);
    }
    
    protected void logicFrameCheck(float delta) {
        logicFramAccumulator += delta;
        if (logicFramAccumulator >= LOGIC_FRAME_LENGTH) {
            logicFramAccumulator -= LOGIC_FRAME_LENGTH;
            if (!logicFramePause) {
                clockCount++;

                for (ILogicFrameListener logicFrameListener : logicFrameListeners) {
                    logicFrameListener.onLogicFrame();
                }
            }
        }
    }

    public void setAreaAndNotifyChildren(String current) {
        String last = this.area;
        this.area = current;
        
        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(last, current);
        }
        
    }
    
    public void hideAchievementMaskBoard() {
        Gdx.app.log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(uiStage);
        setLogicFramePause(false);
    }

    public void onAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        setLogicFramePause(true);
    }
    
    
    public static Drawable createTwoColorBoard(int width, int height, float grayColor, int colorStartX) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
        pixmap.setColor(grayColor, grayColor, grayColor, 1.0f);
        pixmap.fillRectangle(colorStartX , 0, width - colorStartX, height);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        return drawable;
    }

    public static Drawable createBorderBoard(int width, int height, float grayColor, int borderWidth) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
        pixmap.setColor(grayColor + 0.1f, grayColor + 0.1f, grayColor + 0.1f, 1.0f);
        pixmap.fill();
        pixmap.setColor(grayColor, grayColor, grayColor, 1.0f);
        pixmap.fillRectangle(borderWidth, borderWidth, width - borderWidth * 2, height - borderWidth * 2);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        return drawable;
    }
    
    public void showAndUpdateGuideInfo(BaseConstruction model) {
        popUpInfoBoard.setVisible(true);
        popUpInfoBoard.update(model);
    }
    
    public void hideAndCleanGuideInfo() {
        popUpInfoBoard.setVisible(false);
        //popUpInfoBoard.setText("GUIDE_TEXT");
    }

}
