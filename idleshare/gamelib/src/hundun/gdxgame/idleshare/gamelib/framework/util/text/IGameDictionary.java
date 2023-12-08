package hundun.gdxgame.idleshare.gamelib.framework.util.text;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/01/11
 */



public interface IGameDictionary {
    List<String> getMenuScreenTexts(Language language);
    List<String> getAchievementTexts(Language language);
    Map<Language, String> getLanguageShowNameMap();
    List<String> getStageSelectMaskBoardTexts(Language language);
}
