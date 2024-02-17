package hundun.gdxgame.idlemushroom.ui.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.ui.screen.IdleMushroomWorldPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.TileNodeUtils.HexMode;
import lombok.Getter;


public class HexCellVM extends Table {

    static final HexMode hexMode = HexMode.ODD_Q;

    public enum MaskMode {
        NONE,
        SELECTED,
        SELECTED_DISTANCE_1,
    }

    static int HEX_SIZE = 128;
    static int SQR_3_DIV_2_HEX_SIZE = 111;
    static int TABLE_WIDTH = HEX_SIZE;
    static int TABLE_HEIGHT = SQR_3_DIV_2_HEX_SIZE;
    static int MAIN_IMAGE_WIDTH = 128;
    static int MAIN_IMAGE_HEIGHT = 192;
    static int HIT_BOX_X = 4;
    static int HIT_BOX_Y = 4;
    static int HIT_BOX_WIDTH = MAIN_IMAGE_WIDTH - HIT_BOX_X * 2;
    static int HIT_BOX_HEIGHT = SQR_3_DIV_2_HEX_SIZE - HIT_BOX_Y * 2;
    static int LEVEL_STATE_IMAGE_SIZE = 32;
    static float hexBaseSizeX = (float) (HEX_SIZE);
    static float hexBaseSizeY = (float) (SQR_3_DIV_2_HEX_SIZE);

    IdleMushroomGame game;

    HexAreaVM hexAreaVM;
    @Getter
    BaseConstruction deskData;
    @Getter
    MaskMode maskMode;
    public IdleMushroomWorldPlayScreen parent;

    Label mainLabel;
    @Getter
    Image mainImage;
    @Getter
    Image hightLightImage;
    @Getter
    Image levelStateImage;

    final Drawable cachedHexCellCanUpgradeIcon;
    final Drawable cachedHexCellMaxLevelIcon;
    public HexCellVM(IdleMushroomWorldPlayScreen parent, HexAreaVM hexAreaVM, BaseConstruction deskData, boolean isSelectedConstruction) {
        this.parent = parent;
        this.game = hexAreaVM.screen.getGame();
        this.hexAreaVM = hexAreaVM;
        this.deskData = deskData;

        this.mainImage = new Image();
        mainImage.setBounds(
                0,
                0,
                MAIN_IMAGE_WIDTH,
                MAIN_IMAGE_HEIGHT
        );
        this.addActor(mainImage);

        this.hightLightImage = new Image();
        hightLightImage.setBounds(
                0,
                0,
                MAIN_IMAGE_WIDTH,
                MAIN_IMAGE_HEIGHT
        );
        this.addActor(hightLightImage);

        this.levelStateImage = new Image();
        levelStateImage.setBounds(
                TABLE_WIDTH * 0.65f - LEVEL_STATE_IMAGE_SIZE * 0.5f,
                TABLE_HEIGHT * 0.15f,
                LEVEL_STATE_IMAGE_SIZE,
                LEVEL_STATE_IMAGE_SIZE
        );
        this.addActor(levelStateImage);

        /*this.setBackground(new TextureRegionDrawable(new TextureRegion(TextureFactory.getSimpleBoardBackground(
                this.game.getScreenContext().getLayoutConst().DESK_WIDTH,
                this.game.getScreenContext().getLayoutConst().DESK_HEIGHT
        ))));*/

        this.mainLabel = new Label("", game.getMainSkin());
        mainLabel.setPosition(
                TABLE_WIDTH / 2.0f,
                TABLE_HEIGHT / 2.0f
        );
        this.addActor(mainLabel);





        //this.mainLabel.setText(deskData.getId());
        //this.setBackground(new TextureRegionDrawable(game.getTextureManager().getConstructionHexImage(deskData.getPrototypeId())));
        mainImage.setDrawable(new TextureRegionDrawable(game.getTextureManager().getConstructionHexImage(deskData.getPrototypeId())));
        Vector2 uiPosition = calculatePosition(deskData.getSaveData().getPosition().getX(), deskData.getSaveData().getPosition().getY());
        this.setBounds(
                uiPosition.x,
                uiPosition.y,
                TABLE_WIDTH,
                TABLE_HEIGHT
        );

        cachedHexCellCanUpgradeIcon = new TextureRegionDrawable(game.getTextureManager().getHexCellCanUpgradeIcon());
        cachedHexCellMaxLevelIcon = new TextureRegionDrawable(game.getTextureManager().getHexCellMaxLevelIcon());
        updateMaskMode(MaskMode.NONE);
    }

    public static Vector2 calculatePosition(int gridX, int gridY)
    {

        Vector2 newposition = new Vector2(HexAreaVM.roomWidth / 2.0f, HexAreaVM.roomHeight / 2.0f);
        if (hexMode == HexMode.ODD_Q) {
            float yOffset = hexBaseSizeY * (Math.abs(gridX) % 2) * -0.5f;
            newposition.y += hexBaseSizeY * (-gridY) + yOffset;
            newposition.x += hexBaseSizeX * gridX * 0.75f;
        }
        return newposition;
    }


    public void updateMaskMode(@Null MaskMode maskMode) {
        this.maskMode = maskMode;
        if (maskMode == MaskMode.SELECTED) {
            this.hightLightImage.setDrawable(new TextureRegionDrawable(parent.getGame().getTextureManager().getConstructionHexHighLightImage1()));
        } else if (maskMode == MaskMode.SELECTED_DISTANCE_1) {
            this.hightLightImage.setDrawable(new TextureRegionDrawable(parent.getGame().getTextureManager().getConstructionHexHighLightImage2()));
        } else {
            this.hightLightImage.setDrawable(null);
        }
        if (game.debugMode) {
            this.mainLabel.setText(JavaFeatureForGwt.stringFormat(
                    "(%s, %s)",
                    deskData.getSaveData().getPosition().getX(),
                    deskData.getSaveData().getPosition().getY()
            ) );
        }
    }


    public void onLogicFrame() {
        if (deskData.getUpgradeComponent().canUpgrade()) {
            levelStateImage.setDrawable(cachedHexCellCanUpgradeIcon);
        } else if (deskData.getLevelComponent().isReachMaxLevel() && deskData.getUpgradeComponent().getUpgradeState() != UpgradeState.NO_UPGRADE) {
            levelStateImage.setDrawable(cachedHexCellMaxLevelIcon);
        } else {
            levelStateImage.setDrawable(null);
        }

    }
}
