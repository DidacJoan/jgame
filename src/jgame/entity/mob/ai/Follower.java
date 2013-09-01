package jgame.entity.mob.ai;

import jgame.entity.Mob;

/**
 * A very simple seek & follow AI.
 * Lot of work required.
 * @author hector
 */
public class Follower extends Seeker
{
    private static final int SEEK_RADIUS = 15;
    
    public Follower(Mob mob)
    {
        super(mob);
    }
    
    @Override
    public void update(int delta)
    {
        if(target == null)
            seek(SEEK_RADIUS, delta); // Find a target
        else
            mob.goTo(target, delta); // Go to target
    }
}
