package jgame.utils;

import jgame.math.Vec2;
import jgame.utils.IntegerEnum;

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
