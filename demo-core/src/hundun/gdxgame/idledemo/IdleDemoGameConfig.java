package hundun.gdxgame.idledemo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idledemo.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.idledemo.logic.ConstructionId;
import hundun.gdxgame.idledemo.logic.GameArea;
import hundun.gdxgame.idledemo.logic.ResourceType;
import hundun.gdxgame.idledemo.logic.ScreenId;
import hundun.gdxgame.idledemo.ui.screen.PlayScreen;
import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idlestarter.ConstructionsFileLoader;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class IdleDemoGameConfig extends ChildGameConfig {
    
    public IdleDemoGameConfig(IdleDemoGame game) {

        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_0, Arrays.asList(
                ConstructionId.COOKIE_CLICK_PROVIDER,
                ConstructionId.COOKIE_AUTO_PROVIDER,
                ConstructionId.COOKIE_SELLER
        ));
        areaShownConstructionIds.put(GameArea.AREA_1, Arrays.asList(
                ConstructionId.WIN_PROVIDER
        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.AREA_0, Arrays.asList(
            ResourceType.COOKIE
        ));
        this.setAreaShowEntityResourceIds(areaShownResourceIds);
        
        var constructionStarterLevelMap = Map.of(ConstructionId.COOKIE_SELLER, 1);
        this.setConstructionStarterLevelMap(constructionStarterLevelMap);
        
        var constructionStarterWorkingLevelMap = Map.of(ConstructionId.COOKIE_SELLER, Boolean.FALSE);
        this.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
         
        Map<String, String> screenIdToFilePathMap = Map.of(
                ScreenId.MENU, "audio/Loop-Menu.wav",
                ScreenId.PLAY, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        var achievementPrototypes = Arrays.asList(
                new AchievementPrototype("Game win", "You win the game!",
                        null,
                        Map.of(ResourceType.WIN_TROPHY, 1)
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }
}
