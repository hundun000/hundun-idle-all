package hundun.gdxgame.idleshare.gamelib.framework.model.grid;

public interface ITileNodeMap<T> {

    ITileNode<T> getValidNodeOrNull(GridPosition position);
}
