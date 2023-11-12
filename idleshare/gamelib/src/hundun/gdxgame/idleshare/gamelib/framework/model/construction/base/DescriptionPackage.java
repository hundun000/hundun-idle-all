package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/11/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescriptionPackage {
    String clickOutputButtonText;
    String upgradeButtonText;

    String outputCostDescriptionStart;
    String outputGainDescriptionStart;

    String upgradeCostDescriptionStart;
    String upgradeMaxLevelDescription;

    private String transformButtonText;
    private String transformCostDescriptionStart;
    private String upgradeMaxLevelHasTransferDescription;

    private String destroyButtonText;
    private String destroyGainDescriptionStart;
    private String destroyCostDescriptionStart;

    ILevelDescriptionProvider levelDescriptionProvider;

    private IProficiencyDescroptionProvider proficiencyDescriptionProvider;

    List<String> extraTexts;

    public static interface ILevelDescriptionProvider {
        String provide(int level, int workingLevel, boolean reachMaxLevel);
    }
    public static interface IProficiencyDescroptionProvider {
        String provide(int proficiency, Boolean reachMaxProficiency);
    }
}
