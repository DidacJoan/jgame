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
    
    public Entity()
    {
        this(new Vec2(0, 0), new Vec2(0, 0));
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
    
    public Vec2 getTopLeft()
    {
        return topLeft.add(pos);
    }
    
    public Vec2 getBottomRight()
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
