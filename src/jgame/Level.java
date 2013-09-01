package jgame;

import jgame.entity.Mob;
import jgame.entity.MovableEntity;
import jgame.entity.mob.Link;
import jgame.entity.mob.ia.Player;
import jgame.entity.object.Plant;
import jgame.level.EntityMap;
import jgame.level.Location;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import jgame.utils.Dir;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;

/**
 * Represents a Level in the game.
 * @author hector
 */
public class Level extends EntityMap
{
    protected Player player;
    
    public Level(String name) throws SlickException
    {
        super(name);
    }
    
    @Override
    public void load() throws SlickException
    {
        super.load();
        loadEntities();
    }
    
    private void loadEntities() throws SlickException
    {
        for(ObjectGroup group : getObjectGroups())
            for(GroupObject object : group.getObjects())
                addEntity(object);
    }
    
    private void addEntity(GroupObject object) throws SlickException
    {
        Entity e;
        
        // @TODO Entity factory
        
        switch(object.type)
        {
            case "plant":
                e = new Plant(object.width, object.height, getImage(object));
                break;
                
            default:
                e = new Link(this);
        }
        
        e.setPos(object.x, object.y - object.height);
        add(e);
    }
    
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public void setAtLocation(Mob p, String locationName)
    {
        Location location = getLocation(locationName);
        
        p.setPos(location.getPos());
        p.setFacing(location.getFacing());
        
        add(p);
    }
    
    @Override
    public void update(int delta)
    {
        removeDead();
        super.update(delta);
    }
    
    @Override
    public void render(Graphics g)
    {
        renderLayersBelow(0, 0);
        
        for(Entity e : getEntities())
            e.render();
        
        renderLayersAbove(0, 0);
    }
    
    public void move(MovableEntity mob, Dir dir, long delta)
    {
        Vec2 pos = mob.getPos();
        Vec2 intensity = new Vec2(0.1 * delta, 0.1 * delta);
        Vec2 newPos = dir.getVector().mul(intensity).add(pos); // DIR * INTENSITY + POS
        
        if(canBeMovedTo(mob, newPos))
            mob.setPos(newPos);
    }
    
    public boolean canBeMovedTo(Entity entity, Vec2Int subtile)
    {
        return canBeMovedTo(entity, getPosition(subtile));
    }
    
    private boolean canBeMovedTo(Entity mob, Vec2 position)
    {
        if(areTilesBlocked(mob.getTopLeft(position), mob.getBottomRight(position)))
            return false;
        
        return handleCollisions(mob, position);
    }
    
    public boolean areTilesBlocked(Entity entity, Vec2Int atSubtile)
    {
        Vec2 pos = getPosition(atSubtile);
        
        return areTilesBlocked(entity.getTopLeft(pos), entity.getBottomRight(pos));
    }
    
    public void highlight(Color color, Vec2Int subtile)
    {
        // No debugging allowed here!
        // @TODO Refactoring?
    }
}
