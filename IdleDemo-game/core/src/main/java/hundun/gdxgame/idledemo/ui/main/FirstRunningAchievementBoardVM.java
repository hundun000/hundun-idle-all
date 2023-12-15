package hundun.gdxgame.idledemo.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.shared.BaseIdleDemoScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AchievementManager.AchievementInfoPackage;

import java.util.List;

public class FirstRunningAchievementBoardVM extends Table {

    BaseIdleDemoScreen parent;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;

    List<String> texts;

    public FirstRunningAchievementBoardVM(BaseIdleDemoScreen parent)
    {
        this.parent = parent;
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

    public void updateData()
    {
        AchievementInfoPackage data = parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getAchievementInfoPackage();
        if (data.getFirstRunningAchievement() != null)
        {
            nameValueLabel.setText(data.getFirstRunningAchievement().getAchievement().getName());
            descriptionLabel.setText(data.getFirstRunningAchievement().getAchievement().getDescription());
        }
        else
        {
            nameValueLabel.setText("");
            descriptionLabel.setText(texts.get(5));
        }

        countValueLabel.setText(JavaFeatureForGwt.stringFormat(
                "已完成：%s；未解锁：%s",
                data.getCompletedSize(),
                data.getLockedSize()
        ));


    }

}
