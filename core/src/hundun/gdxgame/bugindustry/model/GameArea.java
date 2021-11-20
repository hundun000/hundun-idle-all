package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */
public enum GameArea {
    BEE_FARM,
    BEE_BUFF,
    FOREST_FARM,
    SHOP,
    ;
    
    

    public GameArea switchFarmAndBuff() {
        switch (this) {
            case BEE_FARM:
                return BEE_BUFF;
            case BEE_BUFF:
                return BEE_FARM;    
            default:
                return null;
        }
    }

    public GameArea switchCatogory() {
        switch (this) {
            case BEE_FARM:
            case BEE_BUFF:
                return FOREST_FARM;
            case FOREST_FARM:
                return BEE_FARM;
            default:
                return null;
        }
    }
}
