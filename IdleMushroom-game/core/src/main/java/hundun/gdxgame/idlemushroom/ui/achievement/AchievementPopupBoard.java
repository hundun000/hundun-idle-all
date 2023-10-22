package hundun.gdxgame.idlemushroom.ui.achievement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import com.badlogic.gdx.utils.Null;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomPlayScreen;
import hundun.gdxgame.idleshare.core.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementPopupBoard extends Table {

    BaseIdleMushroomPlayScreen parent;


    List<String> texts;

    public AchievementPopupBoard(BaseIdleMushroomPlayScreen parent, List<String> texts) {
        this.parent = parent;
        this.texts = texts;
        this.setBackground(new SpriteDrawable(new Sprite(parent.getGame().getTextureManager().getAchievementMaskBoardTexture())));
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rebuildUi(null);
        this.setVisible(false);

    }

    private void rebuildUi(@Null AbstractAchievement prototype) {
        this.clearChildren();

        Label label = new Label(prototype != null ? prototype.getDescription() : "", parent.getGame().getMainSkin());
        this.add(label).center().row();

        if (prototype != null && prototype.getAwardResourceMap() != null) {
            Label rewardLabel = new Label(texts.get(3), parent.getGame().getMainSkin());
            this.add(rewardLabel).center().row();
            for (ResourcePair entry : prototype.getAwardResourceMap()) {
                ResourceAmountPairNode<IdleMushroomGame> node = new ResourceAmountPairNode<>(parent.getGame(), entry.getType());
                node.update(entry.getAmount());
                this.add(node)
                        .height(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_HEIGHT)
                        .width(parent.getLayoutConst().RESOURCE_AMOUNT_PAIR_NODE_WIDTH);
                this.row();
            }
        }

        Button textButton = new TextButton(texts.get(4), parent.getGame().getMainSkin());
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (prototype != null && prototype.getAwardResourceMap() != null)
                {
                    parent.getGame().getIdleGameplayExport().getGameplayContext().getStorageManager().modifyAllResourceNum(prototype.getAwardResourceMap(), true);
                }
                parent.hideAchievementMaskBoard();
            }
        });
        this.add(textButton).center();
    }


    public void setAchievementPrototype(AbstractAchievement prototype) {

        rebuildUi(prototype);

    }
}
