package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;


public class HexCellVM extends Table {
    static int HEX_SIZE = 128;
    static float hexBaseSizeX = (float) (HEX_SIZE * Math.sqrt(3.0f) / 2);
    static float hexBaseSizeY = (float) (HEX_SIZE * 0.75);
    DemoIdleGame game;

    HexAreaVM hexAreaVM;
    @Getter
    BaseConstruction deskData;
    public WorldPlayScreen parent;

    Label mainLabel;
    Image image;

    public HexCellVM(WorldPlayScreen parent, HexAreaVM hexAreaVM, BaseConstruction deskData) {
        this.game = hexAreaVM.screen.getGame();
        this.hexAreaVM = hexAreaVM;
        this.deskData = deskData;


        this.image = new Image();
        image.setBounds(
                0,
                0,
                hexBaseSizeX,
                HEX_SIZE
        );
        this.addActor(image);
        /*this.setBackground(new TextureRegionDrawable(new TextureRegion(TextureFactory.getSimpleBoardBackground(
                this.game.getScreenContext().getLayoutConst().DESK_WIDTH,
                this.game.getScreenContext().getLayoutConst().DESK_HEIGHT
        ))));*/

        this.mainLabel = new Label("", game.getMainSkin());
        this.add(mainLabel);

        updateUI();
    }



    public void updateUI(){

        //this.mainLabel.setText(deskData.getId());
        image.setDrawable(new TextureRegionDrawable(game.getTextureManager().getConstructionHexImage(deskData.getPrototypeId())));
        Vector2 uiPosition = calculatePosition(deskData.getSaveData().getPosition().getX(), deskData.getSaveData().getPosition().getY());
        this.setBounds(
                uiPosition.x,
                uiPosition.y,
                hexBaseSizeX,
                HEX_SIZE
        );
    }

    private static Vector2 calculatePosition(int gridX, int gridY)
    {

        Vector2 newposition = new Vector2(HexAreaVM.roomWidth / 2.0f, HexAreaVM.roomHeight / 2.0f);
        newposition.y += hexBaseSizeY * gridY;
        newposition.x += hexBaseSizeX * (gridX - (Math.abs(gridY) % 2) / 2.0f);
        return newposition;
    }


}