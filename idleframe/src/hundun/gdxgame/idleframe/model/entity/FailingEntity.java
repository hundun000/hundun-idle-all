package hundun.gdxgame.idleframe.model.entity;

public class FailingEntity extends GameEntity {

    int removeY;
    
    public FailingEntity(int removeY) {
        super();
        this.removeY = removeY;
    }

    @Override
    public boolean checkRemove() {
        return this.y <= removeY;
    }
}
