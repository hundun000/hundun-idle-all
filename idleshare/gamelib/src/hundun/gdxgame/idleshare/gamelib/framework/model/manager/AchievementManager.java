package hundun.gdxgame.idleshare.gamelib.framework.model.manager;
/**
 * @author hundun
 * Created on 2021/11/12
 */

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hundun.gdxgame.gamelib.starter.listerner.IGameStartListener;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.AbstractAchievement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class AchievementManager implements IBuffChangeListener, IOneFrameResourceChangeListener, IGameStartListener {
    IdleGameplayContext gameContext;

    Map<String, AbstractAchievement> prototypes = new HashMap<>();
    private List<String> totalAchievementIds = new ArrayList<>();
    private List<String> achievementQueue = new ArrayList<>();
    @Getter
    @Setter
    Set<String> unlockedAchievementIds = new HashSet<>();

    @AllArgsConstructor
    @Getter
    public static class AchievementInfoPackage
    {
        AbstractAchievement firstLockedAchievement;
        int total;
        int unLockedSize;
        List<AbstractAchievement> allAchievementList;
        Set<String> unlockedAchievementIds;
    }

    public AchievementManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
        gameContext.getEventManager().registerListener(this);
    }

    public AchievementInfoPackage getAchievementInfoPackage()
    {
        List<AbstractAchievement> allAchievementList = achievementQueue.stream()
                .map(it -> prototypes.get(it))
                .collect(Collectors.toList());

        AbstractAchievement firstLockedAchievement = allAchievementList.stream()
                .filter(it -> !unlockedAchievementIds.contains(it.getId()))
                .findFirst()
                .orElse(null);
        return new AchievementInfoPackage(
                firstLockedAchievement,
                totalAchievementIds.size(),
                unlockedAchievementIds.size(),
                allAchievementList,
                unlockedAchievementIds
        );
    }

    public void addPrototype(AbstractAchievement prototype) {
        prototypes.put(prototype.getId(), prototype);
        prototype.lazyInitDescription(gameContext);
    }

    private boolean checkRequiredResources(Map<String, Integer> requiredResources) {
        if (requiredResources == null) {
            return true;
        }
        for (Entry<String, Integer> entry : requiredResources.entrySet()) {
            long own = gameContext.getStorageManager().getResourceNumOrZero(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRequiredBuffs(Map<String, Integer> map) {
        if (map == null) {
            return true;
        }
        for (Entry<String, Integer> entry : map.entrySet()) {
            int own = gameContext.getBuffManager().getBuffAmoutOrDefault(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void checkAllAchievementUnlock() {
        //Gdx.app.log(this.getClass().getSimpleName(), "checkAllAchievementUnlock");
        for (AbstractAchievement prototype : prototypes.values()) {
            if (unlockedAchievementIds.contains(prototype.getId())) {
                continue;
            }
            boolean resourceMatched = prototype.checkUnlock();
            if (resourceMatched) {
                unlockedAchievementIds.add(prototype.getId());
                gameContext.getEventManager().notifyAchievementUnlock(prototype);
            }
        }
    }


    @Override
    public void onBuffChange() {
        checkAllAchievementUnlock();
    }

    public void lazyInit(Map<String, AbstractAchievement> achievementProviderMap, List<String> achievementPrototypeIds) {
        achievementPrototypeIds.forEach(it -> addPrototype(achievementProviderMap.get(it)));
        this.totalAchievementIds = achievementPrototypeIds;
        this.achievementQueue = new ArrayList<>(achievementPrototypeIds);
    }

    @Override
    public void onResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        checkAllAchievementUnlock();
    }

    @Override
    public void onGameStart() {
        checkAllAchievementUnlock();
    }



}
