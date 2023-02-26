package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionPackage {
    String outputCostDescriptionStart;
    String outputGainDescriptionStart;
    String upgradeCostDescriptionStart;
    String upgradeMaxLevelDescription;
    String buttonDescroption;
    ILevelDescroptionProvider levelDescroptionProvider;

    public static interface ILevelDescroptionProvider {

        

        String provide(int level, int workingLevel, boolean reachMaxLevel);
    }

}
