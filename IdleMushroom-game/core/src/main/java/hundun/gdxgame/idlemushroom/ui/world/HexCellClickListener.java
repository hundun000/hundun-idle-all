package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomWorldPlayScreen;


public class HexCellClickListener extends ClickListener {
    IdleMushroomGame game;
    IdleMushroomWorldPlayScreen screen;
    private final HexCellVM vm;

    public HexCellClickListener(IdleMushroomWorldPlayScreen screen, HexCellVM vm) {
        this.game = screen.getGame();
        this.screen = screen;
        this.vm = vm;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (screen.isDisableHexAreaInput()) {
            return;
        }
        screen.onCellClicked(vm);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vm.getDeskData().getId()
            + " has been clicked."
        );
        game.getFrontend().log(this.getClass().getSimpleName(), stringBuilder.toString());
    }
}
