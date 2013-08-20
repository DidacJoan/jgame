package jgame.entity.mob.ia;

import java.util.Set;
import jgame.Entity;
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
    
    private Entity target;
    
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
            mob.goTo(target);
    }
    
    private void seek(int delta)
    {
        mob.wander(delta); // Wander a little
        
        Set<Entity> entities = mob.seek(SEEK_RADIUS);
        
        if(!entities.isEmpty())
        {
            // Find an Entity that can move
            for(Entity e : entities)
            {
                if(e.isMovable())
                {
                    target = e;
                    return;
                }
            }
        }
    }
}
