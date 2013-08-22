package jgame.utils;

import java.util.Random;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 * Represents the directions available in the game.
 *
 * @author hector
 */
public enum Dir implements IntegerEnum
{
    DOWN(new Vec2(0, 1)),
    UP(new Vec2(0, -1)),
    RIGHT(new Vec2(1, 0)),
    LEFT(new Vec2(-1, 0));
    
    private final int value;
    private final Vec2 vector;
    
    private Dir(Vec2 vector)
    {
        this.value = ordinal();
        this.vector = vector;
    }
    
    public static Dir random()
    {
        Dir[] dirs = Dir.values();
        Random rand = new Random();
        
        int randInt = rand.nextInt();
        
        if(randInt < 0)
            randInt *= -1;
        
        return dirs[randInt % dirs.length];
    }
    
    public static Dir fromVector(Vec2Int vector)
    {
        if(vector.x == 0)
            return vector.y == 1 ? Dir.DOWN : Dir.UP;
        
        return vector.x == 1 ? Dir.RIGHT : Dir.LEFT;
    }
    
    public Vec2 getVector()
    {
        return vector;
    }

    @Override
    public int getValue()
    {
        return value;
    }
};
