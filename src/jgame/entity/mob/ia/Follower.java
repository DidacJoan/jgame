package jgame.entity.mob.ia;

import java.util.List;
import jgame.entity.Mob;
import jgame.entity.mob.AI;

/**
 * A very simple seek & follow AI.
 * Lot of work required.
 * @author hector
 */
public class Follower extends AI
{
    private static final int SEEK_RADIUS = 10;
    
    private Mob target;
    
    public Follower(Mob mob)
    {
        super(mob);
    }
    
    @Override
    public void update(int delta)
    {
        if(target == null)
            seek(delta);
        
        else
            mob.follow(target);
    }
    
    private void seek(int delta)
    {
        mob.wander(delta); // Wander a little
        
        List<Mob> mobs = mob.seek(SEEK_RADIUS);
        
        if(!mobs.isEmpty())
            target = mobs.get(0); // Target mob is the first one detected
    }
}
