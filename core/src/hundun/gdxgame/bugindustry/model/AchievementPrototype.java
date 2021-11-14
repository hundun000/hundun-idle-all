package hundun.gdxgame.bugindustry.model;

import java.util.Map;

import hundun.gdxgame.bugindustry.model.construction.BuffId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2021/11/12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AchievementPrototype {
    String name;
    String description;
    Map<BuffId, Integer> requiredBuffs;
    Map<ResourceType, Integer> requiredResources;
}
