package hundun.gdxgame.bugindustry.model.manager;
/**
 * @author hundun
 * Created on 2021/11/12
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.BugIndustryGame;
import hundun.gdxgame.bugindustry.model.AchievementPrototype;
import hundun.gdxgame.bugindustry.model.construction.BuffId;
import hundun.gdxgame.bugindustry.model.resource.ResourceType;
import hundun.gdxgame.bugindustry.ui.IAmountChangeEventListener;
import lombok.Getter;
import lombok.Setter;

public class AchievementManager implements IAmountChangeEventListener {
    BugIndustryGame game;
    
    Map<String, AchievementPrototype> prototypes = new HashMap<>();
    @Setter
    @Getter
    Set<String> unlockedAchievementNames = new HashSet<>();
    
    public AchievementManager(BugIndustryGame game) {
        this.game = game;
        initPrototypes();
        game.getEventManager().registerListener(this);
    }
    
    private void initPrototypes() {
        addPrototype(new AchievementPrototype("Game win", "You win the game!",
                null,
                Map.of(ResourceType.WIN_TROPHY, 1)
                ));
    }
    
    private void addPrototype(AchievementPrototype prototype) {
        prototypes.put(prototype.getName(), prototype);
    }
    
    private boolean checkRequiredResources(Map<ResourceType, Integer> requiredResources) {
        if (requiredResources == null) {
            return true;
        }
        for (var entry : requiredResources.entrySet()) {
            long own = game.getModelContext().getStorageManager().getResourceNumOrZero(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkRequiredBuffs(Map<BuffId, Integer> map) {
        if (map == null) {
            return true;
        }
        for (var entry : map.entrySet()) {
            int own = game.getModelContext().getBuffManager().getBuffAmoutOrDefault(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    private void checkAllAchievementUnlock() {
        Gdx.app.log(this.getClass().getSimpleName(), "checkAllAchievementUnlock");
        for (AchievementPrototype prototype : prototypes.values()) {
            if (unlockedAchievementNames.contains(prototype.getName())) {
                continue;
            }
            boolean resourceMatched = checkRequiredResources(prototype.getRequiredResources());
            boolean buffMatched = checkRequiredBuffs(prototype.getRequiredBuffs());
            boolean allMatched = resourceMatched && buffMatched;
            if (allMatched) {
                unlockedAchievementNames.add(prototype.getName());
                game.getScreenContext().getGameBeeScreen().onAchievementUnlock(prototype);
            }
        }
    }
    
    
    @Override
    public void onBuffChange(boolean fromLoad) {
        if (!fromLoad) {
            checkAllAchievementUnlock();
        }
    }
    
    @Override
    public void onResourceChange(boolean fromLoad) {
        if (!fromLoad) {
            checkAllAchievementUnlock();
        }
    }
    
    
    
}
