package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import java.util.List;

import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;

/**
 * @author hundun
 * Created on 2023/03/01
 */
public interface IBuiltinConstructionsLoader {
    List<BaseConstruction> provide(Language language);
}
