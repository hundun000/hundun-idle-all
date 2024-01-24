package hundun.gdxgame.idleshare.gamelib.framework.model.grid;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode.TileNeighborDirection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.redblobgames.com/grids/hexagons/">hexagons sim online</a>
 */
public class TileNodeUtils {

    public enum HexMode {
        ODD_R,
        ODD_Q
    }

    public static HexMode hexMode = HexMode.ODD_Q;

    public static List<TileNeighborDirection> values = JavaFeatureForGwt.listOf(
            TileNeighborDirection.LEFT_UP,
            TileNeighborDirection.LEFT_MID,
            TileNeighborDirection.LEFT_DOWN,
            TileNeighborDirection.RIGHT_UP,
            TileNeighborDirection.RIGHT_MID,
            TileNeighborDirection.RIGHT_DOWN
    );

    public static <T> void updateNeighborsAllStep(ITileNode<T> target, ITileNodeMap<T> map) {
        // update self
        updateNeighborsOneStep(target, map);
        // update new neighbors
        target.getNeighbors().values().stream()
                .filter(it -> it != null)
                .forEach(it -> updateNeighborsOneStep(it, map));
    }

    public static <T> void updateNeighborsOneStep(ITileNode<T> target, ITileNodeMap<T> map)
    {

        target.setNeighbors(new HashMap<>());

        values.forEach(it -> {
            GridPosition position = tileNeighborPosition(target.getPosition(), it);
            ITileNode<T> neighbor = map.getValidNodeOrNull(position);
            target.getNeighbors().put(it, neighbor);
        });

    }


    public static <T> GridPosition tileNeighborPosition(GridPosition gridPosition, TileNeighborDirection direction)
    {
        switch (direction) {
            case LEFT_UP:
                return tileLeftUpNeighbor(gridPosition);
            case LEFT_MID:
                return tileLeftMidNeighbor(gridPosition);
            case LEFT_DOWN:
                return tileLeftDownNeighbor(gridPosition);
            case RIGHT_UP:
                return tileRightUpNeighbor(gridPosition);
            case RIGHT_MID:
                return tileRightMidNeighbor(gridPosition);
            case RIGHT_DOWN:
                return tileRightDownNeighbor(gridPosition);
        }
        return null;
    }



    public static <T> GridPosition tileRightMidNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            y = gridPosition.y;
            x = gridPosition.x + 1;
        } else {
            // hexMode == HexMode.ODD_Q
            boolean evenCol = gridPosition.x % 2 == 0;
            if (evenCol)
            {
                y = gridPosition.y;
                x = gridPosition.x + 1;
            }
            else
            {
                y = gridPosition.y + 1;
                x = gridPosition.x + 1;
            }
        }
        return new GridPosition(x, y);
    }


    public static <T> GridPosition tileRightUpNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            boolean evenRow = gridPosition.y % 2 == 0;
            if (evenRow) {
                y = gridPosition.y - 1;
                x = gridPosition.x + 1;
            } else {
                y = gridPosition.y - 1;
                x = gridPosition.x;
            }
        } else {
            // hexMode == HexMode.ODD_Q
            boolean evenCol = gridPosition.x % 2 == 0;
            if (evenCol) {
                y = gridPosition.y - 1;
                x = gridPosition.x + 1;
            }
            else
            {
                y = gridPosition.y;
                x = gridPosition.x + 1;
            }
        }
        return new GridPosition(x, y);
    }


    public static <T> GridPosition tileRightDownNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            boolean evenRow = gridPosition.y % 2 == 0;
            if (evenRow) {
                y = gridPosition.y + 1;
                x = gridPosition.x + 1;
            } else {
                y = gridPosition.y + 1;
                x = gridPosition.x;
            }
        } else {
            // hexMode == HexMode.ODD_Q
            y = gridPosition.y + 1;
            x = gridPosition.x;
        }
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftUpNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            boolean evenRow = gridPosition.y % 2 == 0;
            if (evenRow) {
                y = gridPosition.y + 1;
                x = gridPosition.x;
            } else {
                y = gridPosition.y + 1;
                x = gridPosition.x - 1;
            }
        } else {
            // hexMode == HexMode.ODD_Q
            y = gridPosition.y - 1;
            x = gridPosition.x;
        }
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftMidNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            y = gridPosition.y;
            x = gridPosition.x - 1;
        } else {
            // hexMode == HexMode.ODD_Q
            boolean evenCol = gridPosition.x % 2 == 0;
            if (evenCol) {
                y = gridPosition.y - 1;
                x = gridPosition.x - 1;
            }
            else
            {
                y = gridPosition.y;
                x = gridPosition.x - 1;
            }
        }
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftDownNeighbor(GridPosition gridPosition)
    {
        int y;
        int x;
        if (hexMode == HexMode.ODD_R) {
            boolean evenRow = gridPosition.y % 2 == 0;
            if (evenRow) {
                y = gridPosition.y - 1;
                x = gridPosition.x;
            } else {
                y = gridPosition.y - 1;
                x = gridPosition.x - 1;
            }
        } else {
            // hexMode == HexMode.ODD_Q
            boolean evenCol = gridPosition.x % 2 == 0;
            if (evenCol) {
                y = gridPosition.y;
                x = gridPosition.x - 1;
            }
            else
            {
                y = gridPosition.y + 1;
                x = gridPosition.x - 1;
            }
        }
        return new GridPosition(x, y);
    }




}
