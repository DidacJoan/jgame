package jgame.entity.mob;

import jgame.entity.Mob;
import jgame.entity.mob.action.LinkAction;
import jgame.entity.mob.action.link.AttackSword;
import jgame.entity.mob.action.link.Move;
import jgame.math.Vec2;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Represents the player in the game.
 * @author hector
 */
public class Player extends Mob
{
    private static final Vec2 TOP_L = new Vec2(4.0, 13.0);
    private static final Vec2 BOT_R = new Vec2(17.0, 27.0);
    
    private Input input;
    
    public Player(Input input) throws SlickException
    {
        super(TOP_L, BOT_R, LinkAction.size);
        
        this.input = input;
        
        setAction(LinkAction.MOVE, new Move("shield"));
        setAction(LinkAction.ATTACK_SWORD, new AttackSword());
    }
    
    @Override
    public boolean hasKeyDown(int code)
    {
        return input.isKeyDown(code);
    }
}
