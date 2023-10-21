package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.AbstractConstructionPrototype;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldConstructionPrototypeCellDetailNode extends BaseCellDetailNodeVM {
    BaseDemoPlayScreen screen;
    AbstractConstructionPrototype constructionPrototype;
    GridPosition position;
    Label constructionNameLabel;

    List<WorldBuyConstructionInfoNodeVM> children = new ArrayList<>();



    public WorldConstructionPrototypeCellDetailNode(
            BaseDemoPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.screen = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);

        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));
    }








    private void update() {
        // ------ update show-state ------
        if (constructionPrototype == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
        }
        // ------ update text ------

        constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                "%s (%s, %s)",
                screen.getGame()
                        .getIdleGameplayExport()
                        .getGameplayContext()
                        .getGameDictionary()
                        .constructionPrototypeIdToDetailDescriptionConstPart(
                                screen.getGame().getIdleGameplayExport().getLanguage(),
                                constructionPrototype.getPrototypeId()
                        ),
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
            AbstractConstructionPrototype constructionPrototype,
            GridPosition position
    ) {
        this.constructionPrototype = constructionPrototype;
        this.position = position;

        List<String> buyCandidateConstructionPrototypeIds = screen.getGame().getIdleGameplayExport().getGameplayContext()
                .getConstructionManager()
                .getByCandidatePrototypeIds();
        buyCandidateConstructionPrototypeIds.forEach(constructionPrototypeId -> {
            WorldBuyConstructionInfoNodeVM vm = new WorldBuyConstructionInfoNodeVM(
                    screen,
                    construction,
                    constructionPrototypeId
            );
            vm.update();
            this.add(vm);
            children.add(vm);
        });

        update();
    }

    public static class WorldBuyConstructionInfoNodeVM extends Table {
        BaseDemoPlayScreen parent;
        BaseConstruction model;
        Label constructionNameLabel;
        TextButton buyButton;
        String buyTargetConstructionPrototypeId;

        public WorldBuyConstructionInfoNodeVM(
                BaseDemoPlayScreen parent,
                BaseConstruction model,
                String buyTargetConstructionPrototypeId
        ) {
            super();
            final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
            this.parent = parent;
            this.buyTargetConstructionPrototypeId = buyTargetConstructionPrototypeId;
            this.model = model;

            int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
            int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
            int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

            this.constructionNameLabel = new Label(buyTargetConstructionPrototypeId, parent.getGame().getMainSkin());
            constructionNameLabel.setWrap(true);

            this.buyButton = new TextButton("buy", parent.getGame().getMainSkin());
            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.getGame().getIdleGameplayExport().getGameplayContext()
                            .getConstructionManager()
                            .buyInstanceOfPrototype(buyTargetConstructionPrototypeId, model.getPosition());
                }
            });


            // ------ this ------
            this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
            this.add(buyButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
            this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
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
                //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
            }
            // ------ update text ------
            constructionNameLabel.setText(
                    parent.getGame()
                            .getIdleGameplayExport()
                            .getGameplayContext()
                            .getGameDictionary()
                            .constructionPrototypeIdToShowName(
                                    parent.getGame().getIdleGameplayExport().getLanguage(),
                                    buyTargetConstructionPrototypeId
                            )
            );

            // ------ update clickable-state ------
            boolean canBuyInstanceOfPrototype = parent.getGame().getIdleGameplayExport().getGameplayContext()
                    .getConstructionManager()
                    .canBuyInstanceOfPrototype(buyTargetConstructionPrototypeId, model.getPosition());
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
}
