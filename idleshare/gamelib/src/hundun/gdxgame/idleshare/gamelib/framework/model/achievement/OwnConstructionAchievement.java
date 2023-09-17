package hundun.gdxgame.idleshare.gamelib.framework.model.achievement;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OwnConstructionAchievement extends AbstractAchievement {
    public Map<String, Entry<Integer, Integer>> requireds;


    public OwnConstructionAchievement(
            String id, String name, String description, String congratulationText,
            Map<String, Entry<Integer, Integer>> requireds,
            List<ResourcePair> awardResourceMap,
            String nextAchievementId
    )

    {
        super(id, name, description, congratulationText, awardResourceMap, nextAchievementId);
        this.requireds = requireds;
    }

    @Override
    public boolean checkComplete()
    {
        List<BaseConstruction> allConstructions = gameplayContext.getConstructionManager().getConstructions();

        for (Entry<String, Entry<Integer, Integer>> requiredEntry : requireds.entrySet())
        {
            int requiredAmount = requiredEntry.getValue().getKey();
            int requiredLevel = requiredEntry.getValue().getValue();
            boolean matched = allConstructions.stream()
                    .filter(it -> it.getPrototypeId().equals(requiredEntry.getKey()))
                    .filter(it -> it.getSaveData().getLevel() >= requiredLevel)
                    .count() >= requiredAmount;
            if (!matched)
            {
                return false;
            }
        }

        return true;
    }

    public static class Companion {
        /**
         * textMap.value : String name, String description, String congratulationText <br>
         * requireds.value: key requiredAmount ; value requiredLevel;
         */
        public static void quickAddOwnConstructionAchievement(
                Map<String, AbstractAchievement> map,
                String id,
                Map<String, List<String>> textMap,
                Map<String, Entry<Integer, Integer>> requireds,
                String nextAchievementId,
                ResourcePair... awardResourceMap
        )
        {
            AbstractAchievement achievement =  new OwnConstructionAchievement(
                    id,
                    textMap.get(id).get(0), textMap.get(id).get(1), textMap.get(id).get(2),
                    requireds,
                    JavaFeatureForGwt.listOf(awardResourceMap),
                    nextAchievementId
            );
            map.put(id, achievement);
        }
    }
}
