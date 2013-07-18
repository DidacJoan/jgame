package jgame.entity.mob.action.link;

import jgame.entity.mob.Action;
import jgame.utils.Dir;
import jgame.entity.Mob;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.SlickException;

/**
 * Action that Link performs when he moves.
 * @author hector
 */
public class Move extends Action
{
    private static final String     NAME            =   "move";
    private static final Vec2[]     ENTITY_POS      =   { new Vec2(), new Vec2(), new Vec2(), new Vec2() };
    private static final Vec2Int    DIM             =   new Vec2Int(21, 27);
    private static final int[]      SPRITE_COUNT    =   {7, 7, 7, 7};
    private static final int[]      SPRITE_SPEED    =   {50, 50, 50, 50};
    private static final int        SPRITE_STAND    =   3;
    
    public Move(Mob entity, String type) throws SlickException
    {
        super(NAME + "_" + type, entity, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
        
        // UP and DOWN animations are ping pong animations!
        // This means that the animation goes back and forth.
        // If each number represents an sprite (3 standing sprite):
        // 3 4 5 6 5 4 3 2 1 0 1 2 3 ...
        getAnim(Dir.UP.getValue()).setPingPong(true);
        getAnim(Dir.DOWN.getValue()).setPingPong(true);
    }
    
    @Override
    public void enter()
    {
        int index = mob.getFacing().getValue();
        
        setIndex(index);
        standByAnim();
    }
    
    @Override
    public void update(int delta)
    {
        setIndex(mob.getFacing().getValue());
        
        if(mob.isMoving())
            updateAnim(delta);
        else
            standByAnim();
    }
    
    private void standByAnim()
    {
        getCurrentAnim().setCurrentFrame(SPRITE_STAND);
    }
    
    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
