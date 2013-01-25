package jgame.entity;

import jgame.entity.action.LinkAction;
import jgame.entity.action.link.AttackSword;
import jgame.entity.action.link.Move;
import jgame.level.Level;
import jgame.math.Vec2;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Represents the player in the game.
 * @author hector
 */
public class Player
{
    private Input input;
    private Vec2 pos;
    private Action[] actions;
    private LinkAction currentAction;
    private Dir facing;
    
    public Player(Input input) throws SlickException
    {
        this.input = input;
        pos = new Vec2(0, 0);
        actions = new Action[]{ new Move("shield"), new AttackSword() };
        currentAction = LinkAction.MOVE;
        facing = Dir.UP;
    }
    
    public void changeAction(LinkAction action)
    {
        actions[currentAction.getValue()].leave(this);
        currentAction = action;
        actions[currentAction.getValue()].enter(this);
    }
    
    public boolean hasKeyDown(int code)
    {
        return input.isKeyDown(code);
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
    
    public void setFacing(Dir facing)
    {
        this.facing = facing;
    }
    
    public Dir getFacing()
    {
        return facing;
    }
    
    public void update(Level level, int delta)
    {
        actions[currentAction.getValue()].transition(this);
        actions[currentAction.getValue()].update(this, level, delta);
    }
    
    public void render()
    {
        actions[currentAction.getValue()].render(pos); 
    }
}
