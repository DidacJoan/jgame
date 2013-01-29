package jgame.entity;

import jgame.level.EntityMap;
import jgame.level.TileMap;
import jgame.math.Vec2;
import jgame.utils.IntegerEnum;

/**
 *
 * @author hector
 */
public class Mob extends Entity
{
    private Dir facing;
    private boolean moving;
    private Action[] actions;
    private IntegerEnum currentAction;
    
    public Mob(Vec2 topLeft, Vec2 bottomRight, int numActions)
    {
        super(topLeft, bottomRight);
        facing = Dir.UP;
        moving = false;
        actions = new Action[numActions];
    }
    
    public void setFacing(Dir facing)
    {
        this.facing = facing;
    }
    
    public Dir getFacing()
    {
        return facing;
    }
    
    public void move(Dir dir, TileMap map, EntityMap entities, long delta)
    {
        if(! moving)
        {
            facing = dir;
            moving = true;
        }
        
        Vec2 intensity = new Vec2(0.1 * delta, 0.1 * delta);
        Vec2 newPos = dir.getVector().mul(intensity).add(pos); // DIR * INTENSITY + POS
        
        if (! map.areTilesBlocked(topLeft.add(newPos), bottomRight.add(newPos), entities))
            pos = newPos;
    }
    
    public boolean isMoving()
    {
        return moving;
    }
    
    public void setAction(IntegerEnum id, Action action)
    {
        actions[id.getValue()] = action;
        
        if(currentAction == null)
            currentAction = id;
    }
    
    public void changeAction(IntegerEnum action)
    {
        actions[currentAction.getValue()].leave(this);
        currentAction = action;
        actions[currentAction.getValue()].enter(this);
    }
    
    @Override
    public void update(TileMap map, EntityMap entities, int delta)
    {
        actions[currentAction.getValue()].transition(this);
        actions[currentAction.getValue()].update(this, map, entities, delta);
    }
    
    @Override
    public void render()
    {
        actions[currentAction.getValue()].render(pos);
        moving = false;
    }
    
    public boolean hasKeyDown(int code)
    {
        return false;
    }
}
