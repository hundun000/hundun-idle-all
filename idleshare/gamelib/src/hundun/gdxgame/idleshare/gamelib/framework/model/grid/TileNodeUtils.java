package hundun.gdxgame.idleshare.gamelib.framework.model.grid;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.ITileNode.TileNeighborDirection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileNodeUtils {
    public static List<TileNeighborDirection> values = JavaFeatureForGwt.listOf(new TileNeighborDirection[]{
        TileNeighborDirection.LEFT_UP,
                TileNeighborDirection.LEFT_MID,
                TileNeighborDirection.LEFT_DOWN,
                TileNeighborDirection.RIGHT_UP,
                TileNeighborDirection.RIGHT_MID,
                TileNeighborDirection.RIGHT_DOWN
    });

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
            GridPosition position = tileNeighborPosition(target, map, it);
            ITileNode<T> neighbor = map.getValidNodeOrNull(position);
            target.getNeighbors().put(it, neighbor);
        });

    }


    public static <T> GridPosition tileNeighborPosition(ITileNode<T> target, ITileNodeMap<T> map, TileNeighborDirection direction)
    {
        switch (direction) {
            case LEFT_UP:
                return tileLeftUpNeighbor(target, map);
            case LEFT_MID:
                return tileLeftMidNeighbor(target, map);
            case LEFT_DOWN:
                return tileLeftDownNeighbor(target, map);
            case RIGHT_UP:
                return tileRightUpNeighbor(target, map);
            case RIGHT_MID:
                return tileRightMidNeighbor(target, map);
            case RIGHT_DOWN:
                return tileRightDownNeighbor(target, map);
        }
        return null;
    }



    public static <T> GridPosition tileRightMidNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        y = target.getPosition().y;
        x = target.getPosition().x + 1;
        return new GridPosition(x, y);
    }


    public static <T> GridPosition tileRightUpNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        if (target.getPosition().y % 2 == 0)
        {
            y = target.getPosition().y + 1;
            x = target.getPosition().x + 1;
        }
        else
        {
            y = target.getPosition().y + 1;
            x = target.getPosition().x;
        }
        return new GridPosition(x, y);
    }


    public static <T> GridPosition tileRightDownNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        if (target.getPosition().y % 2 == 0)
        {
            y = target.getPosition().y - 1;
            x = target.getPosition().x + 1;
        }
        else
        {
            y = target.getPosition().y - 1;
            x = target.getPosition().x;
        }
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftUpNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        if (target.getPosition().y % 2 == 0)
        {
            y = target.getPosition().y + 1;
            x = target.getPosition().x;
        }
        else
        {
            y = target.getPosition().y + 1;
            x = target.getPosition().x - 1;
        }
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftMidNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        y = target.getPosition().y;
        x = target.getPosition().x - 1;
        return new GridPosition(x, y);
    }

    public static <T> GridPosition tileLeftDownNeighbor(ITileNode<T> target, ITileNodeMap<T> map)
    {
        int y;
        int x;
        if (target.getPosition().y % 2 == 0)
        {
            y = target.getPosition().y - 1;
            x = target.getPosition().x;
        }
        else
        {
            y = target.getPosition().y - 1;
            x = target.getPosition().x - 1;
        }
        return new GridPosition(x, y);
    }




}
