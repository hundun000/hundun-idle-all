package hundun.gdxgame.bugindustry.model;
/**
 * @author hundun
 * Created on 2021/11/05
 */
public enum GameArea {
    BEE,
    FOREST
    ;

    public GameArea next() {
        switch (this) {
            case BEE:
                return FOREST;
            case FOREST:
                return BEE;
            default:
                return this;
        }
    }

    public GameArea previous() {
        switch (this) {
        case BEE:
            return FOREST;
        case FOREST:
            return BEE;
        default:
            return this;
    }
    }
}
