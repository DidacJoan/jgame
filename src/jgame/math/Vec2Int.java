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
    
    public Vec2Int add(int a)
    {
        return new Vec2Int(x + a, y + a);
    }
    
    public Vec2Int sub(Vec2Int v)
    {
        return new Vec2Int(x - v.x, y - v.y);
    }
    
    public Vec2Int sub(int a)
    {
        return new Vec2Int(x - a, y - a);
    }
    
    public Vec2Int mul(Vec2Int v)
    {
        return new Vec2Int(x * v.x, y * v.y);
    }
    
    public Vec2Int mul(int a)
    {
        return new Vec2Int(x * a, y * a);
    }
    
    public Vec2Int div(Vec2Int v)
    {
        return new Vec2Int(x / v.x, y / v.y);
    }
    
    public Vec2Int div(int a)
    {
        return new Vec2Int(x / a, y / a);
    }
    
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
