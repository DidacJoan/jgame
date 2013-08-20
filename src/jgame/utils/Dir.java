package jgame.utils;

import java.util.Random;
import jgame.math.Vec2;

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
