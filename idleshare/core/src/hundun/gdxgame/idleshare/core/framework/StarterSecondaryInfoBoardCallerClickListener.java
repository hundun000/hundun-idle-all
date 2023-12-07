package hundun.gdxgame.idleshare.core.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;

import java.util.function.Supplier;

public class StarterSecondaryInfoBoardCallerClickListener extends ClickListener {
    Supplier<BaseConstruction> modelGetter;
    ISecondaryInfoBoardCallback<BaseConstruction> callback;

    public StarterSecondaryInfoBoardCallerClickListener(Supplier<BaseConstruction> modelGetter, ISecondaryInfoBoardCallback<BaseConstruction> callback) {
        this.modelGetter = modelGetter;
        this.callback = callback;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (modelGetter.get() != null) {
            callback.showAndUpdateGuideInfo(modelGetter.get());
        }
        Gdx.app.log(StarterSecondaryInfoBoardCallerClickListener.class.getSimpleName(), "this clicked event");
        super.clicked(event, x, y);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (modelGetter.get() != null && pointer == -1) {
            callback.showAndUpdateGuideInfo(modelGetter.get());
        }
        super.enter(event, x, y, pointer, fromActor);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (pointer == -1) {
            callback.hideAndCleanGuideInfo();
        }
        super.exit(event, x, y, pointer, toActor);
    }
}
