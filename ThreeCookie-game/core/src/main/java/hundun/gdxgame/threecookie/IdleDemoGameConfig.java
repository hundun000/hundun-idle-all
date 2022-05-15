package hundun.gdxgame.threecookie;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleshare.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.framework.data.StarterData;
import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.framework.util.JavaHighVersionFeature;
import hundun.gdxgame.threecookie.logic.BuffId;
import hundun.gdxgame.threecookie.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.threecookie.logic.ConstructionId;
import hundun.gdxgame.threecookie.logic.GameArea;
import hundun.gdxgame.threecookie.logic.ResourceType;
import hundun.gdxgame.threecookie.logic.ScreenId;
import hundun.gdxgame.threecookie.ui.screen.PlayScreen;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class IdleDemoGameConfig extends ChildGameConfig {
    
    public IdleDemoGameConfig(IdleDemoGame game) {

        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
                ConstructionId.COOKIE_CLICK_PROVIDER
        ));
        areaShownConstructionIds.put(GameArea.AREA_BUILDING, JavaHighVersionFeature.arraysAsList(
                ConstructionId.COOKIE_AUTO_PROVIDER,
                ConstructionId.COOKIE_SELLER
        ));
        areaShownConstructionIds.put(GameArea.AREA_WIN, JavaHighVersionFeature.arraysAsList(
                ConstructionId.WIN_PROVIDER
        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COIN
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COOKIE
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.COOKIE_SELLER, 1);
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.COOKIE_SELLER, Boolean.FALSE);
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
        Map<String, String> screenIdToFilePathMap = JavaHighVersionFeature.mapOf(
                ScreenId.MENU, "audio/Loop-Menu.wav",
                ScreenId.PLAY, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaHighVersionFeature.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaHighVersionFeature.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }

    
}
