package hundun.gdxgame.idleshare.gamelib.framework.model.construction.base;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.LevelDescriptionPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.DescriptionPackage.ProficiencyDescriptionPackage;


/**
 * @author hundun
 * Created on 2023/03/01
 */
public class DescriptionPackageFactory {

    public static LevelDescriptionPackage.LevelDescriptionPackageBuilder EN_LEVEL_IMP = LevelDescriptionPackage.builder()
            .levelPart("lv: {0}")
            .reachedMaxLevelPart("(max)")
            .activeLevelPart("active: {0}")
            ;

    public static ProficiencyDescriptionPackage.ProficiencyDescriptionPackageBuilder EN_PROFICIENCY_IMP = ProficiencyDescriptionPackage.builder()
            .proficiencyPart("efficiency: {0)")
            ;

    public static LevelDescriptionPackage.LevelDescriptionPackageBuilder CN_LEVEL_IMP = LevelDescriptionPackage.builder()
            .levelPart("等级{0}")
            .reachedMaxLevelPart("(最大)")
            .activeLevelPart("启用: {0}")
            ;

    public static ProficiencyDescriptionPackage.ProficiencyDescriptionPackageBuilder CN_PROFICIENCY_IMP = ProficiencyDescriptionPackage.builder()
            .proficiencyPart("效率: {0)")
            ;

}
