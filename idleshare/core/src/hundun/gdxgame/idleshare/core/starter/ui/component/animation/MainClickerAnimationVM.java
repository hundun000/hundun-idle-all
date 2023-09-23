package hundun.gdxgame.idleshare.core.starter.ui.component.animation;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class MainClickerAnimationVM<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE>
        extends AbstractAnimationVM<T_GAME, T_SAVE, Void>
{


    BaseConstruction construction;
    TextureRegion[][] regions;
    BaseIdleScreen<T_GAME, T_SAVE> parent;
    
    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;


    
    
    public MainClickerAnimationVM(
            BaseIdleScreen<T_GAME, T_SAVE> parent,
            TextureRegion[][] regions
            ) {
        super(parent.getGame(), null);
        this.parent = parent;
        this.regions = regions;

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (construction != null && construction.getOutputComponent().isTypeClickOutput()) {
                    construction.getOutputComponent().doOutput();
                } else {
                    parent.getGame().getFrontend().log(MainClickerAnimationVM.this.getClass().getSimpleName(),
                            "skip construction doOutput"
                    );
                }
                if (!isRunningState()) {
                    callShow(null);
                }
            }
        });

        this.setTouchable(Touchable.enabled);
        //this.setTransform(true);
        this.construction = parent.getGame().getIdleGameplayExport()
                .getGameplayContext()
                .getConstructionManager()
                .getMainClickConstructionInstances()
        ;

        setAnimation(animationFactory(
                regions,
                0.1f, PlayMode.NORMAL
        ));
        super.resetFrame(false);
    }

    @Override
    public void callShow(Void unused) {


        super.resetFrame(true);
    }

}
