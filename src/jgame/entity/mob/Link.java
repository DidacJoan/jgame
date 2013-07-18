package jgame.entity.mob;

import jgame.entity.Mob;
import jgame.entity.mob.action.link.AttackSword;
import jgame.entity.mob.action.link.Move;
import jgame.level.Level;
import jgame.math.Vec2;
import org.newdawn.slick.SlickException;

/**
 * Represents the player in the game.
 * @author hector
 */
public class Link extends Mob
{
    private static final Vec2 TOP_L = new Vec2(4.0, 13.0);
    private static final Vec2 BOT_R = new Vec2(17.0, 27.0);
    
    public Link(Level level) throws SlickException
    {
        super("link", level, TOP_L, BOT_R);
        
        setMove(new Move(this, "shield"));
        addAttack(new AttackSword(this));
    }
}
