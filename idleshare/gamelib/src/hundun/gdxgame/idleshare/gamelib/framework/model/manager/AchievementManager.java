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
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import lombok.*;

public class AchievementManager implements IBuffChangeListener, IOneFrameResourceChangeListener, IGameStartListener {
    IdleGameplayContext gameContext;

    Map<String, AchievementAndStatus> models = new HashMap<>();




    @AllArgsConstructor
    @Getter
    public static class AchievementInfoPackage
    {
        AchievementAndStatus firstRunningAchievement;
        int total;
        int completedSize;
        int lockedSize;
        List<AchievementAndStatus> sortedAchievementList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AchievementAndStatus {
        AbstractAchievement achievement;
        AchievementSaveData saveData;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AchievementSaveData {
        AchievementState state;

    }

    public enum AchievementState {
        RUNNING,
        LOCKED,
        COMPLETED,
        ;

    }

    public AchievementManager(IdleGameplayContext gameContext) {
        this.gameContext = gameContext;
        gameContext.getEventManager().registerListener(this);
    }

    public AchievementInfoPackage getAchievementInfoPackage()
    {
        List<AchievementAndStatus> allAchievementList = models.values().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(o2.getSaveData().getState().ordinal(), o1.getSaveData().getState().ordinal()))
                .collect(Collectors.toList());
                ;
        AchievementAndStatus firstRunningAchievement = models.values().stream()
                .filter(it -> it.getSaveData().getState() == AchievementState.RUNNING)
                .findFirst()
                .orElse(null);

        Set<String> completedAchievementIds = models.values().stream()
                .filter(it -> it.getSaveData().getState() == AchievementState.COMPLETED)
                .map(it -> it.getAchievement().getId())
                .collect(Collectors.toSet());
        Set<String> lockedAchievementIds = models.values().stream()
                .filter(it -> it.getSaveData().getState() == AchievementState.LOCKED)
                .map(it -> it.getAchievement().getId())
                .collect(Collectors.toSet());
        return new AchievementInfoPackage(
                firstRunningAchievement,
                models.size(),
                completedAchievementIds.size(),
                lockedAchievementIds.size(),
                allAchievementList
        );
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

    private void checkAllAchievementStateChange() {
        //Gdx.app.log(this.getClass().getSimpleName(), "checkAllAchievementUnlock");
        for (AchievementAndStatus achievementAndStatus : models.values()) {
            if (achievementAndStatus.getSaveData().getState() != AchievementState.RUNNING) {
                continue;
            }
            boolean completed = achievementAndStatus.getAchievement().checkComplete();
            if (completed) {
                achievementAndStatus.getSaveData().setState(AchievementState.COMPLETED);
                if (achievementAndStatus.getAchievement().getNextAchievementIds() != null) {
                    achievementAndStatus.getAchievement().getNextAchievementIds().forEach(nextAchievementId -> {
                        AchievementAndStatus nextAchievementAndStatus = models.get(nextAchievementId);
                        if (nextAchievementAndStatus.getSaveData().getState() == AchievementState.LOCKED) {
                            nextAchievementAndStatus.getSaveData().setState(AchievementState.RUNNING);
                            gameContext.getEventManager().notifyAchievementComplete(
                                    nextAchievementAndStatus.getAchievement(),
                                    nextAchievementAndStatus.getSaveData().getState()
                            );
                        }
                    });
                }
                gameContext.getEventManager().notifyAchievementComplete(
                        achievementAndStatus.getAchievement(),
                        achievementAndStatus.getSaveData().getState()
                );
            }
        }
    }


    @Override
    public void onBuffChange() {
        checkAllAchievementStateChange();
    }

    public void subApplyGameplaySaveData(
            BaseConstructionFactory constructionFactory,
            Map<String, AbstractAchievement> achievementProviderMap,
            Map<String, AchievementSaveData> statusDataMap
    ) {
        achievementProviderMap.values().forEach(it -> {
            AchievementSaveData statusData;
            if (statusDataMap.containsKey(it.getId())) {
                statusData = statusDataMap.get(it.getId());
            } else {
                statusData = new AchievementSaveData(AchievementState.LOCKED);
            }
            it.lazyInitDescription(gameContext);
            models.put(it.getId(), new AchievementAndStatus(it, statusData));
        });
    }

    public Map<String, AchievementSaveData> getAchievementSaveDataMap() {
        return models.values().stream()
                .collect(Collectors.toMap(
                        it -> it.getAchievement().getId(),
                        it -> it.getSaveData()
                ));
    }

    @Override
    public void onResourceChange(Map<String, Long> changeMap, Map<String, List<Long>> deltaHistoryMap) {
        checkAllAchievementStateChange();
    }

    @Override
    public void onGameStart() {
        checkAllAchievementStateChange();
    }



}
