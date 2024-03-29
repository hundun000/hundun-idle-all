package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idledemo.ui.shared.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldConstructionPrototypeCellDetailNode extends BaseCellDetailNodeVM {
    BaseDemoPlayScreen parent;
    BaseConstruction construction;
    GridPosition position;
    Label constructionNameLabel;

    List<WorldBuyConstructionInfoNodeVM> children = new ArrayList<>();



    public WorldConstructionPrototypeCellDetailNode(
            BaseDemoPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCTION_BUTTON_AREA_WIDTH;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        // ------ this ------
        this.add(constructionNameLabel).width(CHILD_WIDTH).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }








    private void update() {
        // ------ update show-state ------
        if (construction == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getDescriptionPackage().getName() + " set to its view");
        }
        // ------ update text ------

        constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                "%s (%s, %s)",
                construction.getDescriptionPackage().getWikiText(),
                position.getX(),
                position.getY()
        ));

    }


    @Override
    public void subLogicFrame() {
        update();
        children.forEach(it -> it.subLogicFrame());
    }

    public void updateAsConstructionPrototype(
            BaseConstruction construction,
            GridPosition position
    ) {
        this.construction = construction;
        this.position = position;

        construction.getExistenceComponent().getBuyCandidateConfigs().forEach(constructionBuyCandidateConfig -> {
            WorldBuyConstructionInfoNodeVM vm = new WorldBuyConstructionInfoNodeVM(
                    parent,
                    construction,
                    constructionBuyCandidateConfig
            );
            vm.update();
            this.add(vm);
            children.add(vm);
        });

        update();
    }
}
