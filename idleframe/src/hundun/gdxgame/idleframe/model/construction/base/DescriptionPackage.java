package hundun.gdxgame.idleframe.model.construction.base;

/**
 * @author hundun
 * Created on 2021/11/29
 */
public class DescriptionPackage {
    String outputCostDescriptionStart;
    String outputGainDescriptionStart;
    String upgradeCostDescriptionStart;
    String buttonDescroption;
    String levelDescroption;
    
    // ------ replace-lombok ------
    public DescriptionPackage(String outputCostDescriptionStart, String outputGainDescriptionStart,
            String upgradeCostDescriptionStart, String buttonDescroption, String levelDescroption) {
        super();
        this.outputCostDescriptionStart = outputCostDescriptionStart;
        this.outputGainDescriptionStart = outputGainDescriptionStart;
        this.upgradeCostDescriptionStart = upgradeCostDescriptionStart;
        this.buttonDescroption = buttonDescroption;
        this.levelDescroption = levelDescroption;
    }
    public String getOutputCostDescriptionStart() {
        return outputCostDescriptionStart;
    }
    public void setOutputCostDescriptionStart(String outputCostDescriptionStart) {
        this.outputCostDescriptionStart = outputCostDescriptionStart;
    }
    public String getOutputGainDescriptionStart() {
        return outputGainDescriptionStart;
    }
    public void setOutputGainDescriptionStart(String outputGainDescriptionStart) {
        this.outputGainDescriptionStart = outputGainDescriptionStart;
    }
    public String getUpgradeCostDescriptionStart() {
        return upgradeCostDescriptionStart;
    }
    public void setUpgradeCostDescriptionStart(String upgradeCostDescriptionStart) {
        this.upgradeCostDescriptionStart = upgradeCostDescriptionStart;
    }
    public String getButtonDescroption() {
        return buttonDescroption;
    }
    public void setButtonDescroption(String buttonDescroption) {
        this.buttonDescroption = buttonDescroption;
    }
    public String getLevelDescroption() {
        return levelDescroption;
    }
    public void setLevelDescroption(String levelDescroption) {
        this.levelDescroption = levelDescroption;
    }
    
    
}
