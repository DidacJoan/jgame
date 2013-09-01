package jgame.entity.mob.ia;

import jgame.entity.Mob;
import jgame.level.Path;

/**
 *
 * @author hector
 */
public class Killer extends Seeker
{
    private static final int SEEK_RADIUS = 15;
    
    public Killer(Mob mob)
    {
        super(mob);
    }
    
    @Override
    public void update(int delta)
    {
        if(target == null)
            seek(SEEK_RADIUS, delta);
        
        else if(target.isDead())
            target = null;
        
        else
        {
            Path path = mob.getPathTo(target);
            
            if(path.getLength() < 3)
                mob.attack();
            else
                mob.follow(path, delta);
        }
    }
}
