package jgame.entity;

import java.util.Set;
import jgame.Entity;
import jgame.Level;
import jgame.entity.mob.AI;
import jgame.entity.mob.ia.Follower;
import jgame.level.Path;
import jgame.level.pathfinding.Pathfinder;
import jgame.math.Vec2;
import jgame.utils.Dir;

/**
 * A Mob is a MovableEntity with some moving actions and an artificial intelligence.
 * @author hector
 */
public class Mob extends MovableEntity
{
    private AI ai;
    private Pathfinder pathfinder;
    private static final int WANDER_TIMEOUT = 300;
    private Dir wanderDirection;
    private int time;
    
    public Mob(String name, Level level, Vec2 topLeft, Vec2 bottomRight)
    {
        super(name, level, topLeft, bottomRight);
        
        ai = new Follower(this);
        pathfinder = new Pathfinder(level);
        wanderDirection = Dir.random();
    }
    
    public void setAI(AI ia)
    {
        this.ai = ia;
    }
    
    @Override
    public void update(int delta)
    {
        if(canMove())
            ai.update(delta);
        
        super.update(delta);
    }
    
    public void wander(int delta)
    {
        time += delta;
        
        if(time >= WANDER_TIMEOUT)
        {
            wanderDirection = Dir.random();
            time = 0;
        }
        else
            move(wanderDirection, delta);
    }
    
    public Set<Entity> seek(int radius)
    {
        return level.seek(getCenter(), radius); // Maybe?
    }
    
    public Path getPathTo(Entity entity)
    {   
        return pathfinder.getPath(this, entity);
    }
    
    public void goTo(Entity entity, int delta)
    {
        Path path = getPathTo(entity);
        
        if(!path.isEmpty())
            move(path.getFirstStepDirection(), delta);
    }
}
