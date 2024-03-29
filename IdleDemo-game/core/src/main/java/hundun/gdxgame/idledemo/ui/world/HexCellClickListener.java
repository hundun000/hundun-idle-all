package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idledemo.IdleDemoGame;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;



public class HexCellClickListener extends ClickListener {
    IdleDemoGame game;
    BaseDemoPlayScreen screen;
    private final HexCellVM vm;

    public HexCellClickListener(BaseDemoPlayScreen screen, HexCellVM vm) {
        this.game = screen.getGame();
        this.screen = screen;
        this.vm = vm;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        screen.onCellClicked(vm);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vm.getDeskData().getId()
            + " has been clicked."
        );
        game.getFrontend().log(this.getClass().getSimpleName(), stringBuilder.toString());
    }
}
