package jgame.entity.mob.action.link;

import jgame.entity.mob.Action;
import jgame.entity.MessageType;
import jgame.entity.Mob;
import jgame.entity.mob.action.LinkAction;
import jgame.level.TileArea;
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
    private static final Vec2[]     ENTITY_POS      =   {
                                                            new Vec2(5, -1),
                                                            new Vec2(11, 12),
                                                            new Vec2(3, 2),
                                                            new Vec2(10, 2)
                                                        };
    private static final Vec2Int    DIM             =   new Vec2Int(36, 36);
    private static final int[]      SPRITE_COUNT    =   {6, 9, 9, 9};
    private static final int[]      SPRITE_SPEED    =   {31, 20, 20, 20};
    private static final TileArea   AREA            =
            new TileArea(new String[][]{
                { "OOOO", "ECEE", "OOOO" },
                { "OOOO", "EEEE", "OOOO" },
                { "OOXX", "EEEE", "XXOO" },
                
                { "OOXX", "XXXX", "XXOO" },
                { "OOOX", "XXXX", "XOOO" },
                { "OOOO", "XXXX", "OOOO" }
            });
    
    public AttackSword(Mob entity) throws SlickException
    {
        super(NAME, entity, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
    }
    
    @Override
    public void enter()
    {
        AREA.setCenter(mob);
        AREA.setOrientation(mob.getFacing());
        
        int index = mob.getFacing().getValue();
        setAnim(index);
        getCurrentAnim().stopAt(SPRITE_COUNT[index]-1);
    }
    
    @Override
    public void transition()
    {
        if(getCurrentAnim().isStopped())
            mob.changeAction(LinkAction.MOVE);
    }
    
    @Override
    public void update(int delta)
    {
        if(! updateAnim(delta))
            return;
        
        int middle = (getCurrentAnim().getFrameCount() - 1) / 2;
        
        if(getCurrentAnim().getFrame() == middle)
            mob.send(MessageType.DAMAGE, AREA);
    }
    
    @Override
    public void leave()
    {
        getCurrentAnim().restart();
    }
}
