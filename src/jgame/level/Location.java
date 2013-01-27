package jgame.level;

import jgame.entity.Dir;
import jgame.math.Vec2;

/**
 * Represents a location in the level.
 * @author hector
 */
public class Location {
    private Vec2 pos;
    private Dir facing;
    
    public Location(Vec2 pos, Dir facing)
    {
        this.pos = pos;
        this.facing = facing;
    }
    
    public Vec2 getPos()
    {
        return pos;
    }
    
    public Dir getFacing()
    {
        return facing;
    }
}
