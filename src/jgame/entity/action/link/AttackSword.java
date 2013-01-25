package jgame.entity.action.link;

import jgame.entity.Action;
import jgame.entity.Player;
import jgame.entity.action.LinkAction;
import jgame.level.Level;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.SlickException;

/**
 * Represents the action Link is doing when attacking with the sword.
 * @author hector
 */
public class AttackSword extends Action
{
    private static final String     NAME            =   "attack_sword";
    private static final String     ENTITY          =   "link";
    private static final Vec2[]     ENTITY_POS      =   {
                                                            new Vec2(5, -1),
                                                            new Vec2(11, 12),
                                                            new Vec2(3, 2),
                                                            new Vec2(10, 2)
                                                        };
    private static final Vec2Int    DIM             =   new Vec2Int(36, 36);
    private static final int[]      SPRITE_COUNT    =   {6, 9, 9, 9};
    private static final int[]      SPRITE_SPEED    =   {31, 20, 20, 20};
    
    public AttackSword() throws SlickException
    {
        super(NAME, ENTITY, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
    }
    
    @Override
    public void enter(Player player)
    {
        int index = player.getFacing().getValue();
        setAnim(index);
        getCurrentAnim().stopAt(SPRITE_COUNT[index]-1);
    }
    
    @Override
    public void transition(Player player)
    {
        if(getCurrentAnim().isStopped())
            player.changeAction(LinkAction.MOVE);
    }
    
    @Override
    public void update(Player player, Level level, int delta)
    {
        updateAnim(delta);
        
        /**
         * When animation is in the middle frame, then send a message to entities
         * that are adjacent to the player-facing.
         */
    }
    
    @Override
    public void leave(Player player)
    {
        getCurrentAnim().restart();
    }
}
