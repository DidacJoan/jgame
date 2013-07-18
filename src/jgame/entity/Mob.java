package jgame.entity;

import java.util.ArrayList;
import java.util.List;
import jgame.utils.Dir;
import jgame.Entity;
import jgame.entity.mob.Action;
import jgame.entity.mob.ia.IA;
import jgame.entity.mob.ia.Wander;
import jgame.level.Level;
import jgame.level.area.TileArea;
import jgame.math.Vec2;

/**
 *
 * @author hector
 */
public class Mob extends Entity
{
    protected Level level;
    private Dir facing;
    private boolean moving;
    private List<Action> attackActions;
    private Action moveAction;
    private Action currentAction;
    private IA ia;
    
    public Mob(String name, Level level, Vec2 topLeft, Vec2 bottomRight)
    {
        super(name, topLeft, bottomRight);
        this.level = level;
        facing = Dir.UP;
        moving = false;
        attackActions = new ArrayList();
        ia = new Wander(this);
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
        level.send(this, MessageType.DAMAGE, area);
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
    
    public void setIA(IA ia)
    {
        this.ia = ia;
    }
    
    @Override
    public void update(int delta)
    {
        if(canMove())
            ia.update(delta);
        
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
