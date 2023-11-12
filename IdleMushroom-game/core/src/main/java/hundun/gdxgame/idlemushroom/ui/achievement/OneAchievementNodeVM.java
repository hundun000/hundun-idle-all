package hundun.gdxgame.idlemushroom.ui.achievement;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementAndStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OneAchievementNodeVM extends Table {

    BaseIdleMushroomScreen parent;
    @Setter
    @Getter
    @Null
    AchievementAndStatus achievementAndStatus;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;
    List<String> texts;

    Table descriptionContainer;

    public OneAchievementNodeVM(BaseIdleMushroomScreen parent, AchievementAndStatus achievementAndStatus, boolean wideMode) {
        this.parent = parent;
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        this.achievementAndStatus = achievementAndStatus;

        this.texts = parent.getGame().getIdleGameplayExport().getGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType3Drawable());


        nameStartLabel = new Label("", parent.getGame().getMainSkin());
        nameValueLabel = new Label("", parent.getGame().getMainSkin());

        descriptionLabel = new Label("", parent.getGame().getMainSkin());
        descriptionLabel.setWrap(true);
        descriptionContainer = new Table();
        descriptionContainer.setBackground(parent.getGame().getIdleMushroomTextureManager().getTableType5Drawable());
        descriptionContainer.add(descriptionLabel).grow().center();

        countStartLabel = new Label("", parent.getGame().getMainSkin());
        countValueLabel = new Label("", parent.getGame().getMainSkin());


        if (wideMode) {
            this.add(nameStartLabel).right();
            this.add(nameValueLabel).left().row();
            this.add(descriptionContainer).colspan(2).grow().row();
            this.add(countStartLabel).right();
            this.add(countValueLabel).left().row();
        } else {
            this.add(nameStartLabel).center().row();
            this.add(nameValueLabel).center().row();
            this.add(descriptionContainer).grow().row();
            this.add(countStartLabel).center().row();
            this.add(countValueLabel).center().row();
        }


        nameStartLabel.setText(texts.get(0));
        countStartLabel.setText(texts.get(1));

        updateData();

    }

    public void updateData() {
        
        if (achievementAndStatus != null)
        {
            nameValueLabel.setText(achievementAndStatus.getAchievement().getName());
            descriptionLabel.setText(achievementAndStatus.getAchievement().getDescription());
            countValueLabel.setText(
                    parent.getGame().getIdleMushroomGameDictionary().achievementStatus(
                            parent.getGame().getIdleGameplayExport().getLanguage(),
                            achievementAndStatus.getSaveData().getState()
                    )
            );
        }
        else
        {
            nameValueLabel.setText("");
            descriptionLabel.setText(texts.get(5));
            countValueLabel.setText("");
        }


    }
}
