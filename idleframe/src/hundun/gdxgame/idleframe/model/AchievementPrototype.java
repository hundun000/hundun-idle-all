package hundun.gdxgame.idleframe.model;

import java.util.Map;

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
    Map<String, Integer> requiredBuffs;
    Map<String, Integer> requiredResources;
}
