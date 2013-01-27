package jgame.entity;

import jgame.level.Level;
import jgame.math.Vec2;
import jgame.utils.IntegerEnum;

/**
 *
 * @author hector
 */
public class Mob extends Entity
{
    private Vec2 topLeft;
    private Vec2 bottomRight;
    private Dir facing;
    private boolean moving;
    private Action[] actions;
    private IntegerEnum currentAction;
    
    public Mob(Vec2 topLeft, Vec2 bottomRight, int numActions)
    {
        super();
        
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
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
    
    public void move(Dir dir, Level level, long delta)
    {
        if(! moving)
        {
            facing = dir;
            moving = true;
        }
        
        Vec2 intensity = new Vec2(0.1 * delta, 0.1 * delta);
        Vec2 newPos = dir.getVector().mul(intensity).add(pos); // DIR * INTENSITY + POS
        
        if (! level.isAreaBlocked(topLeft.add(newPos), bottomRight.add(newPos)))
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
    
    public void update(Level level, int delta)
    {
        actions[currentAction.getValue()].transition(this);
        actions[currentAction.getValue()].update(this, level, delta);
    }
    
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
