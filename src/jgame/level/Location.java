package jgame.level;

import jgame.utils.Dir;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 * Represents a location in the level.
 * @author hector
 */
public class Location {
    private Vec2Int tile;
    private Vec2 pos;
    private Dir facing;
    
    public Location(Vec2Int tile, Vec2 pos, Dir facing)
    {
        this.tile = tile;
        this.pos = pos;
        this.facing = facing;
    }
    
    public Vec2 getPos()
    {
        return pos;
    }
    
    public int getTileX()
    {
        return tile.x;
    }
    
    public int getTileY()
    {
        return tile.y;
    }
    
    public Dir getFacing()
    {
        return facing;
    }
}
