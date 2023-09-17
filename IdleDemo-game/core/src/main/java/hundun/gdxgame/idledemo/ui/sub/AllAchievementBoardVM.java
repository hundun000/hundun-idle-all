package hundun.gdxgame.idledemo.ui.sub;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementInfoPackage;

import java.util.List;

public class AllAchievementBoardVM<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {

    BaseIdleScreen<T_GAME, T_SAVE> parent;


    Label descriptionLabel;


    List<String> texts;

    public AllAchievementBoardVM(BaseIdleScreen<T_GAME, T_SAVE> parent)
    {
        this.parent = parent;
        this.texts = parent.getGame().getIdleGameplayExport().getGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(new TextureRegionDrawable(parent.getGame().getTextureManager().getDefaultBoardNinePatchTexture()));

        descriptionLabel = new Label("", parent.getGame().getMainSkin());
        this.add(descriptionLabel).center().row();

        updateData();
    }

    public void updateData()
    {
        StringBuilder builder = new StringBuilder();
        parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getModels().values()
                .forEach(it -> {
                    builder.append(it.getAchievement().getName())
                            .append("\t")
                            .append(it.getSaveData().getState())
                            .append("\n")
                    ;
                });

        descriptionLabel.setText(builder.toString());
    }

}
