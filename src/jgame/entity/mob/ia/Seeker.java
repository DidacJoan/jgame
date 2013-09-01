package jgame.entity.mob.ia;

import java.util.Set;
import jgame.Entity;
import jgame.entity.Mob;
import jgame.entity.mob.AI;

/**
 *
 * @author hector
 */
abstract public class Seeker extends AI
{
    protected Entity target;
    
    public Seeker(Mob mob)
    {
        super(mob);
    }
    
    protected void seek(int radius, int delta)
    {
        mob.wander(delta); // Wander a little
        
        Set<Entity> entities = mob.seek(radius);
        
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
