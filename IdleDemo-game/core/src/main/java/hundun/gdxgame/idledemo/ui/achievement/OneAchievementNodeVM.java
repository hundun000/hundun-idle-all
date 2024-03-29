package hundun.gdxgame.idledemo.ui.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementAndStatus;

import java.util.List;

public class OneAchievementNodeVM extends Table {

    BaseIdleDemoScreen parent;

    AchievementAndStatus achievementAndStatus;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;
    List<String> texts;

    public OneAchievementNodeVM(BaseIdleDemoScreen parent, AchievementAndStatus achievementAndStatus) {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        this.achievementAndStatus = achievementAndStatus;

        this.texts = parent.getGame().getDemoGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());


        nameStartLabel = new Label("", parent.getGame().getMainSkin());
        this.add(nameStartLabel).center();
        nameValueLabel = new Label("", parent.getGame().getMainSkin());
        this.add(nameValueLabel).center().row();
        descriptionLabel = new Label("", parent.getGame().getMainSkin());
        this.add(descriptionLabel).center().row();
        countStartLabel = new Label("", parent.getGame().getMainSkin());
        this.add(countStartLabel).center().row();
        countValueLabel = new Label("", parent.getGame().getMainSkin());
        this.add(countValueLabel).center().row();

        nameStartLabel.setText(texts.get(0));
        countStartLabel.setText(texts.get(1));

        updateData();

    }

    public void updateData() {
        
        if (achievementAndStatus != null)
        {
            nameValueLabel.setText(achievementAndStatus.getAchievement().getName());
            descriptionLabel.setText(achievementAndStatus.getAchievement().getDescription());
        }
        else
        {
            nameValueLabel.setText("");
            descriptionLabel.setText(texts.get(5));
        }

        countValueLabel.setText(JavaFeatureForGwt.stringFormat(
                "state：%s",
                achievementAndStatus.getSaveData().getState()
        ));
    }
}
