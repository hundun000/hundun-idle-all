package hundun.gdxgame.idledemo.ui.shared;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard extends Table {

    BaseIdleDemoScreen parent;

    public PopupInfoBoard(BaseIdleDemoScreen parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        this.setVisible(false);
    }


    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }

    private void rebuildCells(BaseConstruction construction) {
        this.clearChildren();

        add(wapperContainer(new Label(construction.getDescriptionPackage().getWikiText(), parent.getGame().getMainSkin())))
            .colspan(3)
            .left()
            .row();

        buildOnePack(
                construction.getDescriptionPackage().getOutputCostDescriptionStart(),
                construction.getOutputComponent().getOutputCostPack()
        );

        buildOnePack(
                construction.getDescriptionPackage().getOutputGainDescriptionStart(),
                construction.getOutputComponent().getOutputGainPack()
        );

        if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
            buildOnePack(
                    construction.getDescriptionPackage().getUpgradeCostDescriptionStart(),
                    construction.getUpgradeComponent().getUpgradeCostPack()
            );
        } else if (construction.getUpgradeComponent().getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE_HAS_TRANSFER) {
            this.add(wapperContainer(new Label(construction.getDescriptionPackage().getUpgradeCostDescriptionStart(), parent.getGame().getMainSkin())));
            this.add(wapperContainer(new Label(construction.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin())));
            this.row();
        }
        
        

        if (parent.getGame().debugMode) {
            this.debug();
        }
    }

    private void buildOnePack(String descriptionStart, ResourcePack pack) {
        if (pack != null && pack.getModifiedValues() != null) {
            this.add(wapperContainer(new Label(descriptionStart, parent.getGame().getMainSkin())));
            for (ResourcePair entry : pack.getModifiedValues()) {
                ResourceAmountPairNode node = new ResourceAmountPairNode(parent.getGame(), entry.getType());
                node.update(entry.getAmount());
                this.add(wapperContainer(node))
                        .height(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_HEIGHT)
                        .width(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_WIDTH);
            }
            this.row();
        }
    }


    public void update(BaseConstruction model) {
        rebuildCells(model);
    }


}
