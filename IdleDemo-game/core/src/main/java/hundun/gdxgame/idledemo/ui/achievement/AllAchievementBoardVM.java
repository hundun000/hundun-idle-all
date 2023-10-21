package hundun.gdxgame.idledemo.ui.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;

import java.util.ArrayList;
import java.util.List;

public class AllAchievementBoardVM<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {

    BaseIdleScreen<T_GAME, T_SAVE> parent;

    Table childTable;

    protected List<OneAchievementNodeVM<T_GAME, T_SAVE>> nodes = new ArrayList<>();

    public AllAchievementBoardVM(BaseIdleScreen<T_GAME, T_SAVE> parent)
    {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        childTable = new Table();
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);
        ScrollPane scrollPane = new ScrollPane(childTable, parent.getGame().getMainSkin());
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);
        this.add(scrollPane)
                .minWidth(parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_NODE_WIDTH + parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_SCROLL_BORDER_OFFSET)
                .minHeight(parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_NODE_HEIGHT * 2 + parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_SCROLL_BORDER_OFFSET * 2)
        ;

        initChild();
        updateData();
    }


    protected void initChild() {

        nodes.clear();
        childTable.clearChildren();

        parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getModels().values()
                .forEach(it -> {
                    OneAchievementNodeVM<T_GAME, T_SAVE> constructionView = new OneAchievementNodeVM<>(parent, it);
                    nodes.add(constructionView);
                    childTable.add(constructionView)
                            .width(parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_NODE_WIDTH)
                            .height(parent.getLayoutConst().ALL_ACHIEVEMENT_BOARD_NODE_HEIGHT)
                            .row();
                });

    }

    public void updateData()
    {
        nodes.forEach(it -> it.updateData());
    }

}
