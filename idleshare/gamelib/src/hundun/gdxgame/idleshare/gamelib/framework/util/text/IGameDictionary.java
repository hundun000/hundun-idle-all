package hundun.gdxgame.idleshare.gamelib.framework.util.text;

import java.util.List;

/**
 * @author hundun
 * Created on 2022/01/11
 */



public interface IGameDictionary {
    String constructionIdToShowName(Language language, String constructionId);
    String constructionIdToDetailDescroptionConstPart(Language language, String constructionId);
    List<String> getMemuScreenTexts(Language language);
}
