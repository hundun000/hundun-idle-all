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
@Builder
public class DescriptionPackage {
    String buttonDescroption;

    String outputCostDescriptionStart;
    String outputGainDescriptionStart;

    String upgradeCostDescriptionStart;
    String upgradeMaxLevelDescription;

    private String transformButtonDescroption;
    private String transformCostDescriptionStart;
    private String upgradeMaxLevelHasTransferDescription;

    private String destroyButtonDescroption;
    private String destroyGainDescriptionStart;
    private String destroyCostDescriptionStart;

    ILevelDescroptionProvider levelDescroptionProvider;

    private IProficiencyDescroptionProvider proficiencyDescroptionProvider;

    public static interface ILevelDescroptionProvider {
        String provide(int level, int workingLevel, boolean reachMaxLevel);
    }
    public static interface IProficiencyDescroptionProvider {
        String provide(int proficiency, Boolean reachMaxProficiency);
    }
}
