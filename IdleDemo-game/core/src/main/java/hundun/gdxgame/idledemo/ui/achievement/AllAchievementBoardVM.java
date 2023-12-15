package hundun.gdxgame.idledemo.ui.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;

import java.util.ArrayList;
import java.util.List;

public class AllAchievementBoardVM extends Table {

    BaseIdleDemoScreen parent;

    Table childTable;

    protected List<OneAchievementNodeVM> nodes = new ArrayList<>();

    public AllAchievementBoardVM(BaseIdleDemoScreen parent)
    {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        childTable = new Table();
        childTable.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
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

        parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getAchievementInfoPackage().getSortedAchievementList()
                .forEach(it -> {
                    OneAchievementNodeVM constructionView = new OneAchievementNodeVM(parent, it);
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
