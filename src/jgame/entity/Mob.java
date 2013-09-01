package jgame.entity;

import java.util.Set;
import jgame.Entity;
import jgame.Level;
import jgame.entity.health.HealthPoints;
import jgame.entity.mob.AI;
import jgame.entity.mob.ia.Killer;
import jgame.level.Path;
import jgame.level.pathfinding.Pathfinder;
import jgame.math.Vec2;
import jgame.utils.Dir;
import org.newdawn.slick.Graphics;

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
    private Health health;
    
    public Mob(String name, Level level, Vec2 topLeft, Vec2 bottomRight)
    {
        super(name, level, topLeft, bottomRight);
        
        ai = new Killer(this);
        pathfinder = new Pathfinder(level);
        wanderDirection = Dir.random();
        health = new HealthPoints(100);
        
        health.setRenderCentered(true);
        health.setRenderOver(true);
    }
    
    public void setAI(AI ia)
    {
        this.ai = ia;
    }
    
    @Override
    public void update(int delta)
    {
        if(health.isEmpty())
            kill();
        
        if(canMove())
            ai.update(delta);
        
        super.update(delta);
    }
    
    @Override
    public void receive(MessageType msg, Entity from)
    {
        if(msg == MessageType.DAMAGE)
            health.damage(1);
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
        follow(getPathTo(entity), delta);
    }
    
    public void follow(Path path, int delta)
    {
        if(!path.isEmpty())
        {
            for(Dir direction : path.getFirstStepDirection())
                move(direction, delta);
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        super.render(g);
        health.render(g, getPosCenter());
    }
}
