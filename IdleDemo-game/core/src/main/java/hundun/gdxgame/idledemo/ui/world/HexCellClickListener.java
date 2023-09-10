package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;



public class HexCellClickListener extends ClickListener {
    DemoIdleGame game;
    BaseDemoPlayScreen screen;
    private final HexCellVM vm;

    public HexCellClickListener(BaseDemoPlayScreen screen, HexCellVM vm) {
        this.game = screen.getGame();
        this.screen = screen;
        this.vm = vm;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        screen.onDeskClicked(vm);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vm.getDeskData().getId()
            + " has been clicked."
        );
        game.getFrontend().log(this.getClass().getSimpleName(), stringBuilder.toString());
    }
}
