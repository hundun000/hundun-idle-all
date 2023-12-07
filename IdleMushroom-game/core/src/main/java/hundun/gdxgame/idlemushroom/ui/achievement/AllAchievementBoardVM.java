package hundun.gdxgame.idlemushroom.ui.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.logic.DemoAchievementLoader;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllAchievementBoardVM extends Table {

    BaseIdleMushroomScreen parent;

    Table childTable;

    protected List<OneAchievementNodeVM> nodes = new ArrayList<>();

    public AllAchievementBoardVM(BaseIdleMushroomScreen parent)
    {
        this.parent = parent;
        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType2Drawable());

        childTable = new Table();
        //childTable.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType2Drawable());
        ScrollPane scrollPane = new ScrollPane(childTable, parent.getGame().getMainSkin());
        scrollPane.setScrollingDisabled(true, false);
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
                .stream()
                .sorted(Comparator.comparingInt(o -> DemoAchievementLoader.achievementSortWeightMap.get(o.getAchievement().getId())))
                .forEach(it -> {
                    OneAchievementNodeVM constructionView = new OneAchievementNodeVM(parent, it, true);
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
