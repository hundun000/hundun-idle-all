package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.logic.RootSaveData;
import hundun.gdxgame.idleshare.core.framework.model.manager.AbstractIdleScreenContext;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class IdleMushroomScreenContext extends AbstractIdleScreenContext<IdleMushroomGame, RootSaveData> {

    DemoMenuScreen menuScreen;
    @Getter
    MainPlayScreen mainPlayScreen;
    WorldPlayScreen worldPlayScreen;
    DemoAchievementScreen achievementScreen;



    public static class IdleMushroomPlayScreenLayoutConst extends PlayScreenLayoutConst {

        public float questionMarkAreaSize = 32;
        public int EPOCH_PART_CHILD_WIDTH = 150;
        public int SELLER_PART_CHILD_WIDTH = 150;
        public int EpochInfoArea_CHILD_WIDTH = 200;
        public float menuButtonWidth = 200;
        public float menuButtonHeight = 75;

        public IdleMushroomPlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
            super(gameLogicWidth, gameLogicHeight);
            CONSTRUCION_CHILD_WIDTH = 150;
        }

    }

    public IdleMushroomScreenContext(IdleMushroomGame game) {
        super(game);
    }

    @Override
    public void lazyInit() {
        this.menuScreen = new DemoMenuScreen(game);
        this.mainPlayScreen = new MainPlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);
        this.achievementScreen = new DemoAchievementScreen(game);

        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MENU, menuScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MAIN, mainPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_WORLD, worldPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_ACHIEVEMENT, achievementScreen);
    }

}
