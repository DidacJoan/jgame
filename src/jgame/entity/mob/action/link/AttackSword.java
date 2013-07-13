package jgame.entity.mob.action.link;

import jgame.entity.Mob;
import jgame.entity.mob.DamageAction;
import jgame.entity.mob.action.LinkAction;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.SlickException;

/**
 * Represents the action Link is doing when attacking with the sword.
 * @author hector
 */
public class AttackSword extends DamageAction
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
    
    public AttackSword(Mob entity) throws SlickException
    {
        super(NAME, entity, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
    }
    
    @Override
    public void enter()
    {
        int index = mob.getFacing().getValue();
        setIndex(index);
        getCurrentAnim().setLooping(false);
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
        
        mob.attack(getCurrentDamageArea());
    }
    
    @Override
    public void leave()
    {
        getCurrentAnim().restart();
    }
}
