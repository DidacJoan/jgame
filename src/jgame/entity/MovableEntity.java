package jgame.entity;

import java.util.ArrayList;
import java.util.List;
import jgame.Entity;
import jgame.entity.mob.Action;
import jgame.Level;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.utils.Dir;

/**
 * Represents an Entity with the ability to move.
 * @author hector
 */
abstract public class MovableEntity extends Entity
{
    protected Level level;
    private Dir facing;
    private boolean moving;
    private List<Action> attackActions;
    private Action moveAction;
    private Action currentAction;

    public MovableEntity(String name, Level level, Vec2 topLeft, Vec2 bottomRight)
    {
        super(name, topLeft, bottomRight);
        this.level = level;
        facing = Dir.UP;
        moving = false;
        attackActions = new ArrayList();
        
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
    
    @Override
    public boolean isMovable()
    {
        return true;
    }
    
    public boolean isMoving()
    {
        return moving;
    }
    
    public boolean canMove()
    {
        return !currentAction.isBlocking();
    }
    
    public void addAttack(Action action)
    {
        attackActions.add(action);
    }
    
    public void attack()
    {
        changeAction(attackActions.get(0));
    }
    
    public void attack(TileArea area)
    {
        level.send(MessageType.DAMAGE, this, area);
    }
    
    public void setMove(Action action)
    {
        if(currentAction == null || currentAction == moveAction)
            currentAction = action;
        
        moveAction = action;
    }
    
    public void changeAction(Action action)
    {
        currentAction.leave();
        currentAction = action;
        currentAction.enter();
    }
    
    @Override
    public void update(int delta)
    {
       if(currentAction.isFinished())
           changeAction(moveAction);
        
        currentAction.update(delta);
    }
    
    @Override
    public void render()
    {
        currentAction.render();
        moving = false;
    }
}
