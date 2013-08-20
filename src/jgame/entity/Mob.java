package jgame.entity;

import java.util.ArrayList;
import java.util.List;
import jgame.entity.mob.AI;
import jgame.entity.mob.ia.Follower;
import jgame.level.Level;
import jgame.math.Vec2;
import jgame.utils.Dir;

/**
 * A Mob is a MovableEntity with some moving actions and an artificial intelligence.
 * @author hector
 */
public class Mob extends MovableEntity
{
    private AI ai;
    private static final int WANDER_TIMEOUT = 300;
    private Dir wanderDirection;
    private int time;
    
    public Mob(String name, Level level, Vec2 topLeft, Vec2 bottomRight)
    {
        super(name, level, topLeft, bottomRight);
        
        ai = new Follower(this);
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
    
    public List<Mob> seek(int radius)
    {
        List<Mob> mobs = new ArrayList();
        
        // Level responsability
        // return level.seek(pos, radius); // Maybe?
        
        return mobs;
    }
    
    public void follow(MovableEntity mob)
    {
        // Pathfinding goes here using Level
        // A* algorithm with Manhattan heuristic
    }
}
