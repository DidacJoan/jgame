package jgame.entity.mob.ia;

import jgame.entity.Mob;
import jgame.utils.Dir;
import org.newdawn.slick.Input;

/**
 *
 * @author Hector
 */
public class Player extends IA
{
    private static final int[] KEYS     = { Input.KEY_DOWN, Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_LEFT };
    private static final Dir[] KEYS_DIR = { Dir.DOWN, Dir.UP, Dir.RIGHT, Dir.LEFT };
    
    private Input input;
    
    public Player(Mob mob, Input input)
    {
        super(mob);
        
        this.input = input;
    }
    
    @Override
    public void update(int delta)
    {
        checkMove(delta);
        checkAttack();
    }
    
    public void checkMove(int delta)
    {
        Dir facing = mob.getFacing();
        
        if(input.isKeyDown(KEYS[facing.getValue()]))
            mob.move(facing, delta);
        
        int i = 0;
        
        for(int key : KEYS)
        {
            if(KEYS_DIR[i] != facing && input.isKeyDown(key))
                mob.move(KEYS_DIR[i], delta);
            
            i++;
        }
    }
    
    public void checkAttack()
    {
        if(input.isKeyDown(Input.KEY_Z))
            mob.attack();
    }
}
