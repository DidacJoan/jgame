package jgame.entity.mob.ia;

import java.util.Random;
import jgame.entity.Mob;
import jgame.utils.Dir;

/**
 *
 * @author Hector
 */
public class Wander extends IA
{
    private static final int DIR_TIMEOUT = 300;
    private Dir selected;
    private int time;
    
    public Wander(Mob mob)
    {
        super(mob);
        time = 0;
        
        selectDir();
    }
    
    @Override
    public void update(int delta)
    {
        time += delta;
        
        if(time >= DIR_TIMEOUT)
        {
            selectDir();
            time = 0;
            mob.attack();
        }
        else
            mob.move(selected, delta);
    }
    
    private void selectDir()
    {
        Dir[] dirs = Dir.values();
        Random rand = new Random();
        
        int randInt = rand.nextInt();
        
        if(randInt < 0)
            randInt *= -1;
        
        selected = dirs[randInt % dirs.length];
    }
}
