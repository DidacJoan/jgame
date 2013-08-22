package jgame;

import java.util.List;
import java.util.Set;
import jgame.entity.MessageType;
import jgame.entity.Mob;
import jgame.entity.MovableEntity;
import jgame.entity.mob.Link;
import jgame.entity.object.Plant;
import jgame.level.EntityMap;
import jgame.level.Location;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import jgame.utils.Dir;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;

/**
 * A Level is a TileMap with entities.
 * @author hector
 */
public class Level extends TileMap
{
    private EntityMap entityMap;

    public Level(String name) throws SlickException
    {
        super(name);

        entityMap = new EntityMap(width, height, tileWidth, tileHeight);
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
        entityMap.add(e);
    }
    
    public void setPlayer(Mob p, String locationName)
    {
        Location location = getLocation(locationName);
        
        p.setPos(location.getPos());
        p.setFacing(location.getFacing());
        
        entityMap.add(p);
    }
    
    @Override
    public void update(int delta)
    {
        entityMap.removeDead();
        entityMap.update(delta);
    }
    
    @Override
    public void render()
    {
        renderLayersBelow(0, 0);
        
        for(Entity e : entityMap.getEntities())
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
        return canBeMovedTo(entity, entityMap.getPosition(subtile));
    }
    
    private boolean canBeMovedTo(Entity mob, Vec2 position)
    {
        if(areTilesBlocked(mob.getTopLeft(position), mob.getBottomRight(position)))
            return false;
        
        return entityMap.handleCollisions(mob, position);
    }
    
    public void send(MessageType msg, Entity from, TileArea area)
    {
        entityMap.send(msg, from, area);
    }
    
    public Set<Entity> seek(Vec2 pos, int radius)
    {
        // @TODO Refactoring needed
        return entityMap.seek(pos, radius);
    }
    
    public boolean areTilesBlocked(Entity entity, Vec2Int atSubtile)
    {
        Vec2 pos = entityMap.getPosition(atSubtile);
        
        return areTilesBlocked(entity.getTopLeft(pos), entity.getBottomRight(pos));
    }
    
    public Set<Entity> getEntitiesCollidedBy(Entity entity, Vec2Int atSubtile)
    {
        return entityMap.getEntitiesCollidedBy(entity, atSubtile);
    }
    
    public Vec2Int getSubtile(Vec2 pos)
    {
        return entityMap.getSubtile(pos);
    }
    
    public List<Vec2Int> getNeighbors(Vec2Int subtile)
    {
        return entityMap.getNeighbors(subtile);
    }
    
    public int getHorizontalSubtiles()
    {
        return entityMap.getHorizontalSubtiles();
    }
    
    public int getVerticalSubtiles()
    {
        return entityMap.getVerticalSubtiles();
    }
}
