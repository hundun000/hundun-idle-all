package hundun.gdxgame.idlemushroom.ui.screen;

import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class IdleMushroomScreenContext {

    protected IdleMushroomGame game;
    IdleMushroomMenuScreen menuScreen;
    @Getter
    IdleMushroomMainPlayScreen mainPlayScreen;
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
        this.game = game;
    }

    public void lazyInit() {
        this.menuScreen = new IdleMushroomMenuScreen(game);
        this.mainPlayScreen = new IdleMushroomMainPlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);
        this.achievementScreen = new DemoAchievementScreen(game);

        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MENU, menuScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MAIN, mainPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_WORLD, worldPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_ACHIEVEMENT, achievementScreen);
    }

}
