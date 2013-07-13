package jgame.entity;

import jgame.entity.mob.Action;
import jgame.level.Level;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.utils.IntegerEnum;

/**
 *
 * @author hector
 */
public class Mob extends Entity
{
    protected Level level;
    private Dir facing;
    private boolean moving;
    private Action[] actions;
    private IntegerEnum currentAction;
    
    public Mob(String name, Level level, Vec2 topLeft, Vec2 bottomRight, int numActions)
    {
        super(name, topLeft, bottomRight);
        this.level = level;
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
    
    public void move(Dir dir, long delta)
    {
        if(! moving)
        {
            facing = dir;
            moving = true;
        }
        
        level.move(this, dir, delta);
    }
    
    public boolean isMoving()
    {
        return moving;
    }
    
    public void attack(TileArea area)
    {
        level.send(this, MessageType.DAMAGE, area);
    }
    
    public void setAction(IntegerEnum id, Action action)
    {
        actions[id.getValue()] = action;
        
        if(currentAction == null)
            currentAction = id;
    }
    
    public void changeAction(IntegerEnum action)
    {
        actions[currentAction.getValue()].leave();
        currentAction = action;
        actions[currentAction.getValue()].enter();
    }
    
    @Override
    public void update(int delta)
    {
        actions[currentAction.getValue()].transition();
        actions[currentAction.getValue()].update(delta);
    }
    
    @Override
    public void render()
    {
        actions[currentAction.getValue()].render();
        moving = false;
    }
    
    public boolean hasKeyDown(int code)
    {
        return false;
    }
}
