package hundun.gdxgame.idleshare.core.starter.ui.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.export.IdleGameplayExport.ConstructionExportData;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent.UpgradeState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;

    public PopupInfoBoard(BaseIdlePlayScreen<T_GAME, T_SAVE> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        this.setBackground(parent.getLayoutConst().simpleBoardBackground);
        this.setVisible(false);
    }


    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }

    private void rebuildCells(ConstructionExportData model) {
        this.clearChildren();

        add(wapperContainer(new Label(model.getDetailDescroptionConstPart(), parent.getGame().getMainSkin())))
            .colspan(3)
            .left()
            .row();

        buildOnePack(model.getOutputCostPack());

        buildOnePack(model.getOutputGainPack());

        if (model.getUpgradeState() == UpgradeState.HAS_NEXT_UPGRADE) {
            buildOnePack(model.getUpgradeCostPack());
        } else if (model.getUpgradeState() == UpgradeState.REACHED_MAX_UPGRADE) {
            this.add(wapperContainer(new Label(model.getUpgradeCostPack().getDescriptionStart(), parent.getGame().getMainSkin())));
            this.add(wapperContainer(new Label(model.getDescriptionPackage().getUpgradeMaxLevelDescription(), parent.getGame().getMainSkin())));
            this.row();
        }
        
        

        if (parent.getGame().debugMode) {
            this.debug();
        }
    }

    private void buildOnePack(ResourcePack pack) {
        if (pack != null && pack.getModifiedValues() != null) {
            this.add(wapperContainer(new Label(pack.getDescriptionStart(), parent.getGame().getMainSkin())));
            for (ResourcePair entry : pack.getModifiedValues()) {
                ResourceAmountPairNode<T_GAME> node = new ResourceAmountPairNode<>(parent.getGame(), entry.getType());
                node.update(entry.getAmount());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
    }


    public void update(ConstructionExportData model) {
        rebuildCells(model);
    }


}
