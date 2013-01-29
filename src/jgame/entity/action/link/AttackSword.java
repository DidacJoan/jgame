package jgame.entity.action.link;

import jgame.entity.Action;
import jgame.entity.MessageType;
import jgame.entity.Mob;
import jgame.entity.Player;
import jgame.entity.action.LinkAction;
import jgame.level.EntityMap;
import jgame.level.Level;
import jgame.level.TileMap;
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
    
    private boolean sent;
    
    public AttackSword() throws SlickException
    {
        super(NAME, ENTITY, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
    }
    
    @Override
    public void enter(Mob player)
    {
        sent = false;
        int index = player.getFacing().getValue();
        setAnim(index);
        getCurrentAnim().stopAt(SPRITE_COUNT[index]-1);
    }
    
    @Override
    public void transition(Mob player)
    {
        if(getCurrentAnim().isStopped())
            player.changeAction(LinkAction.MOVE);
    }
    
    @Override
    public void update(Mob player, TileMap map, EntityMap entities, int delta)
    {
        updateAnim(delta);
        
        int middle = (getCurrentAnim().getFrameCount() - 1) / 2;
        if(!sent && getCurrentAnim().getFrame() == middle)
        {
            entities.send(MessageType.DAMAGE, player, player.getFacing());
            sent = true;
        }
    }
    
    @Override
    public void leave(Mob player)
    {
        getCurrentAnim().restart();
    }
}
