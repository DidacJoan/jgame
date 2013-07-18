package jgame.level;

import jgame.level.area.TileArea;
import jgame.entity.Dir;
import jgame.entity.Entity;
import jgame.entity.MessageType;
import jgame.entity.Mob;
import jgame.entity.mob.Link;
import jgame.entity.object.Plant;
import jgame.math.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;


/**
 * Represents a level.
 * @author hector
 */
public class Level
{
    private TileMap map;
    private EntityMap entities;
    
    public Level(String name) throws SlickException
    {
        map = new TileMap(name);
        entities = new EntityMap(
                map.getWidthInTiles(),
                map.getHeightInTiles(),
                map.getTileWidth(),
                map.getTileHeight()
                );
        
        loadEntities();
    }
    
    private void loadEntities() throws SlickException
    {
        for(ObjectGroup group : map.getObjectGroups())
            for(GroupObject object : group.getObjects())
                addEntity(object);
    }
    
    private void addEntity(GroupObject object) throws SlickException
    {
        Entity e;
        
        e = new Plant(object.width, object.height, map.getImage(object));
        
        e.setPos(object.x, object.y - object.height);
        entities.add(e);
    }
    
    public void setPlayer(Link p, String locationName)
    {
        Location location = map.getLocation(locationName);
        
        p.setPos(location.getPos());
        p.setFacing(location.getFacing());
        
        entities.add(p);
    }
    
    public void update(int delta)
    {
        entities.removeDead();
        entities.update(delta);
    }
    
    public void render(Graphics g)
    {
        map.renderLayersBelow(0, 0);
        
        for(Entity e : entities.getEntities())
            e.render();
        
        map.renderLayersAbove(0, 0);
    }
    
    public void move(Mob mob, Dir dir, long delta)
    {
        Vec2 pos = mob.getPos();
        Vec2 intensity = new Vec2(0.1 * delta, 0.1 * delta);
        Vec2 newPos = dir.getVector().mul(intensity).add(pos); // DIR * INTENSITY + POS
        
        if(map.areTilesBlocked(mob.getTopLeft(newPos), mob.getBottomRight(newPos)))
            return;
        
        if(entities.handleCollisions(mob, newPos))
            mob.setPos(newPos);
    }
    
    public void send(Mob mob, MessageType msg, TileArea area)
    {
        entities.send(msg, mob, area);
    }
}
