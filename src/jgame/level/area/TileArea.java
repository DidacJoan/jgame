package jgame.level.area;

import java.util.Collection;
import jgame.math.Vec2Int;

/**
 *
 * @author Hector
 */
public interface TileArea
{
    public Collection<Vec2Int> getSubtiles(Vec2Int subtileDimension);
}
