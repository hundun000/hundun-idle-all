package hundun.gdxgame.idleshare.gamelib.framework.model.grid;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public interface ITileNode<T> {
    GridPosition getPosition();
    void setPosition(GridPosition value);
    Map<TileNeighborDirection, ITileNode<T>> getNeighbors();
    void setNeighbors(Map<TileNeighborDirection, ITileNode<T>> value);
    public enum TileNeighborDirection
    {
        LEFT_UP,
        LEFT_MID,
        LEFT_DOWN,
        RIGHT_UP,
        RIGHT_MID,
        RIGHT_DOWN
    }
}
