package jgame.entity.mob.ai;

import jgame.Controller;
import jgame.entity.Mob;
import jgame.entity.mob.AI;
import jgame.utils.Dir;
import org.newdawn.slick.Input;

/**
 *
 * @author Hector
 */
public class Player extends AI
{
    private static final int[] KEYS     = { Input.KEY_DOWN, Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_LEFT };
    private static final Dir[] KEYS_DIR = { Dir.DOWN, Dir.UP, Dir.RIGHT, Dir.LEFT };
    
    private Controller controller;
    
    public Player(Mob mob, Input input)
    {
        super(mob);
        
        this.controller = new Controller(input);
    }
    
    @Override
    public void update(int delta)
    {
        checkMove(delta);
        checkAttack();
    }
    
    private void checkMove(int delta)
    {
        Dir facing = mob.getFacing();
        
        if(controller.isKeyDown(KEYS[facing.getValue()]))
            mob.move(facing, delta);
        
        int i = 0;
        
        for(int key : KEYS)
        {
            if(KEYS_DIR[i] != facing && controller.isKeyDown(key))
                mob.move(KEYS_DIR[i], delta);
            
            i++;
        }
    }
    
    private void checkAttack()
    {
        if(controller.isKeyDown(Input.KEY_Z))
            mob.attack();
    }
    
    public boolean hasKeyUp(int keyCode)
    {
        return controller.isKeyUp(keyCode);
    }
}
