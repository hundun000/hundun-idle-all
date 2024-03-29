package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.ui.shared.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig.ConstructionBuyCandidateConfig;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

public class WorldBuyConstructionInfoNodeVM extends Table  {
    BaseDemoPlayScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;
    TextButton buyButton;
    ConstructionBuyCandidateConfig constructionBuyCandidateConfig;

    public WorldBuyConstructionInfoNodeVM(
            BaseDemoPlayScreen parent,
            BaseConstruction model,
            ConstructionBuyCandidateConfig constructionBuyCandidateConfig
    ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;
        this.constructionBuyCandidateConfig = constructionBuyCandidateConfig;
        this.model = model;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCTION_BUTTON_AREA_WIDTH;


        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        this.buyButton = new TextButton("buy", parent.getGame().getMainSkin());
        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getGame().getIdleGameplayExport().getGameplayContext()
                        .getConstructionManager()
                        .buyInstanceOfPrototype(constructionBuyCandidateConfig, model.getPosition());
            }
        });


        // ------ this ------
        this.add(constructionNameLabel).width(CHILD_WIDTH).row();
        this.add(buyButton).width(CHILD_WIDTH).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }

    public void update() {
        // ------ update show-state ------
        if (model == null) {
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
        constructionNameLabel.setText(
                parent.getGame().getIdleGameplayExport().getGameplayContext().getConstructionFactory()
                        .getPrototype(constructionBuyCandidateConfig.getPrototypeId())
                        .getDescriptionPackage()
                        .getName()
        );

        // ------ update clickable-state ------
        boolean canBuyInstanceOfPrototype = parent.getGame().getIdleGameplayExport().getGameplayContext()
                .getConstructionManager()
                .canBuyInstanceOfPrototype(constructionBuyCandidateConfig, model.getPosition());
        if (!canBuyInstanceOfPrototype) {
            buyButton.setDisabled(false);
            buyButton.getLabel().setColor(Color.WHITE);
        } else {
            buyButton.setDisabled(true);
            buyButton.getLabel().setColor(Color.RED);
        }
    }


    public void subLogicFrame() {
        update();
    }
}
