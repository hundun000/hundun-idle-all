package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
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
    String name;
    String wikiText;
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

    LevelDescriptionPackage levelDescriptionProvider;

    private ProficiencyDescriptionPackage proficiencyDescriptionProvider;

    List<String> extraTexts;

    public static class Helper {
        public static String getWorkingLevelDescription(BaseConstruction construction) {
            boolean reachedMaxLevel = construction.getSaveData().getLevel() == construction.getLevelComponent().maxLevel;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(JavaFeatureForGwt.stringFormat(
                    construction.getDescriptionPackage().getLevelDescriptionProvider().getLevelPart(),
                    construction.saveData.getLevel()
            ));
            if (reachedMaxLevel) {
                stringBuilder.append(" ");
                stringBuilder.append(
                        construction.getDescriptionPackage().getLevelDescriptionProvider().getReachedMaxLevelPart()
                );
            }
            if (construction.getLevelComponent().isTypeWorkingLevelChangeable()) {
                stringBuilder.append(" ");
                stringBuilder.append(JavaFeatureForGwt.stringFormat(
                        construction.getDescriptionPackage().getLevelDescriptionProvider().getActiveLevelPart(),
                        construction.saveData.getWorkingLevel()
                ));
            }
            return stringBuilder.toString();
        }


        public static String getProficiencyDescription(BaseConstruction construction) {
            boolean reachedMaxProficiency = construction.getSaveData().getLevel() == construction.proficiencyComponent.maxProficiency;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(JavaFeatureForGwt.stringFormat(
                    construction.getDescriptionPackage().getProficiencyDescriptionProvider().getProficiencyPart(),
                    construction.saveData.getProficiency()
            ));
            if (reachedMaxProficiency) {
                stringBuilder.append(" ");
                stringBuilder.append(
                        construction.getDescriptionPackage().getProficiencyDescriptionProvider().getReachedMaxProficiencyPart()
                );
            }
            return stringBuilder.toString();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LevelDescriptionPackage {
        String levelPart;
        String reachedMaxLevelPart;
        String activeLevelPart;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProficiencyDescriptionPackage {
        String proficiencyPart;
        String reachedMaxProficiencyPart;
    }
}
