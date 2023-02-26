package hundun.gdxgame.idleshare.core.starter.ui.screen.menu;

import java.util.function.Consumer;
import java.util.function.Function;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2023/03/02
 */
public class LanguageSwitchBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table {
    
    BaseIdleMenuScreen<T_GAME, T_SAVE> parent;
    @Getter
    SelectBox<Language> selectBox;
    Label restartHintLabel;
    
    LanguageSwitchBoard(BaseIdleMenuScreen<T_GAME, T_SAVE> parent,
            Language[] values,
            Language current,
            String startText,
            String hintText,
            Consumer<Language> onSelect
            ) {
        this.parent = parent;
        this.setBackground(DrawableFactory.getSimpleBoardBackground());
        
        
        this.add(new Label(startText, parent.getGame().getMainSkin()));


        this.selectBox = new SelectBox<>(parent.getGame().getMainSkin());
        selectBox.setItems(values);
        selectBox.setSelected(current);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restartHintLabel.setVisible(true);
                onSelect.accept(selectBox.getSelected());
            }
        });
        this.add(selectBox).row();
        
        this.restartHintLabel = new Label(hintText, parent.getGame().getMainSkin());
        restartHintLabel.setVisible(false);
        this.add(restartHintLabel);
        
        
    }
    
    
    
}
