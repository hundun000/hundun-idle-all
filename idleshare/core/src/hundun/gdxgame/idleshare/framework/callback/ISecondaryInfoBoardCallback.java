package hundun.gdxgame.idleshare.framework.callback;

import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public interface ISecondaryInfoBoardCallback<T> {
    void showAndUpdateGuideInfo(T model);
    void hideAndCleanGuideInfo();
}
