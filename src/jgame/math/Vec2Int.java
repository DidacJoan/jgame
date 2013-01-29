package jgame.math;

/**
 * Represents a 2-dimensional vector.
 * @author hector
 */
public class Vec2Int
{
    public int x;
    public int y;
    
    public Vec2Int(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vec2Int add(Vec2 v)
    {
        return new Vec2Int((int)v.x + x, (int)v.y + y);
    }
    
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
