package jgame.entity;

import jgame.math.Vec2;

/**
 * Represents an entity in the game.
 * An entity is everything the player can interact with.
 * Even the player is an entity.
 * @author hector
 */
public class Entity {

    protected Vec2 pos;
    
    public Entity()
    {
        pos = new Vec2(0, 0);
    }
    
    public Vec2 getPos()
    {
        return pos;
    }
    
    public void setPos(double x, double y)
    {
        pos.x = x;
        pos.y = y;
    }
    
}
