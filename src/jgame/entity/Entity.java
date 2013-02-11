package jgame.entity;

import jgame.level.EntityMap;
import jgame.level.TileMap;
import jgame.math.Vec2;

/**
 * Represents an entity in the game.
 * An entity is everything the player can interact with.
 * Even the player is an entity.
 * @author hector
 */
public class Entity {
    
    protected Vec2 pos;
    protected Vec2 topLeft;
    protected Vec2 bottomRight;
    private boolean shouldDie;
    
    public Entity(int width, int height)
    {
        this(new Vec2(0, 0), new Vec2(width - 1, height - 1));
    }
    
    public Entity(Vec2 topLeft, Vec2 bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        pos = new Vec2(0, 0);
        shouldDie = false;
    }
    
    public Vec2 getPos()
    {
        return pos;
    }
    
    public void setPos(Vec2 pos)
    {
        this.pos = pos;
    }
    
    public void setPos(double x, double y)
    {
        pos.x = x;
        pos.y = y;
    }
    
    public Vec2 getCenter()
    {
        return getPos().add(new Vec2(getWidth() / 2.0, getHeight() / 2.0));
    }
    
    public int getWidth()
    {
        return (int)bottomRight.x + 1;
    }
    
    public int getHeight()
    {
        return (int)bottomRight.y + 1;
    }
    
    public Vec2 getTopLeft()
    {
        return getTopLeft(pos);
    }
    
    public Vec2 getTopLeft(Vec2 pos)
    {
        return topLeft.add(pos);
    }
    
    public Vec2 getBottomRight()
    {
        return getBottomRight(pos);
    }
    
    public Vec2 getBottomRight(Vec2 pos)
    {
        return bottomRight.add(pos);
    }
    
    public boolean shouldDie()
    {
        return shouldDie;
    }
    
    public void kill()
    {
        shouldDie = true;
    }
    
    public boolean collidesWith(Entity e)
    {
        return true;
    }
    
    public void handleCollisionWith(Entity e)
    {
        
    }
    
    public void receive(MessageType msg, Entity from)
    {
        
    }
    
    public void update(TileMap map, EntityMap entities, int delta)
    {
        
    }
    
    public void render()
    {
        
    }
}
