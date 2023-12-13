package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.idlemushroom.ui.achievement.OneAchievementNodeVM;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementInfoPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementState;

import java.util.List;

public class FirstRunningAchievementBoardVM extends Table {

    BaseIdleMushroomScreen parent;


    OneAchievementNodeVM node;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;

    List<String> texts;

    public FirstRunningAchievementBoardVM(BaseIdleMushroomScreen parent)
    {
        this.parent = parent;
        this.texts = parent.getGame().getIdleMushroomGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());


        this.node = new OneAchievementNodeVM(parent, null, false);
        this.add(node).grow();
        updateData();
    }

    public void updateData()
    {
        if (node.getAchievementAndStatus() == null
                || node.getAchievementAndStatus().getSaveData().getState() != AchievementState.RUNNING) {
            AchievementInfoPackage data = parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getAchievementInfoPackage();
            node.setAchievementAndStatus(data.getFirstRunningAchievement());
        }
        node.updateData();
    }

}
